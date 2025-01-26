package com.generation.vsnbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.generation.vsnbackend.controller.helper.ControllerHelper;
import com.generation.vsnbackend.model.dtoSteam.DTOSteamConverter;
import com.generation.vsnbackend.model.dtoSteam.SingleOwnedGameDTO;
import com.generation.vsnbackend.model.dtoSteam.VideogameDetailDTO;
import com.generation.vsnbackend.model.entities.Profile;
import com.generation.vsnbackend.model.entities.Videogame;
import com.generation.vsnbackend.model.entities.signin.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/videogames")
public class VideogameController {

    @Autowired
    SteamAPIService steamAPIService;
    @Autowired
    DTOSteamConverter dtoSteamConverter;
    @Autowired
    CredentialService credentialService;
    @Autowired
    ControllerHelper ch;

    /**
     * Retrieves a list of owned videogames for the current user, converting the data from the Steam API
     * into a list of SingleOwnedGameDTO objects. his method checks the user's profile using the
     * authentication token to compare owned videogames with those in the local database and updates the
     * database accordingly.
     *
     * It first fetches the player's owned games from the Steam API using the user's Steam ID.
     * If there are discrepancies between the local game database and the fetched list,
     * the local database is cleared and updated with the latest data.
     *
     * @return a List of SingleOwnedGameDTO objects representing the games owned by the user, sorted
     *         by preference status in descending order
     * @throws JsonProcessingException if there is an error processing the JSON response from the Steam API
     */
    @GetMapping
    public List<SingleOwnedGameDTO> getListOwnedGamesDto() throws JsonProcessingException
    {
        Profile profile=credentialService.getUserByToken().getProfile();
        List<Videogame> gamesDb=profile.getVideogames();
        List<SingleOwnedGameDTO> games=dtoSteamConverter.toListOfOwnedGames(steamAPIService.getPlayerGames(profile.getUser().getSteamId()),
                                                                            gamesDb);
        if(gamesDb!=null&&(gamesDb.isEmpty() || gamesDb.size() != games.size()))
        {
            ch.clearVideogameDbByProfile(profile);
            for (SingleOwnedGameDTO game : games)
            {
                Videogame v = new Videogame();
                v.setPreferred(false);
                v.setNumberOfStars(0);
                v.setIconImgUrl(game.getIconImgUrl());
                v.setAppId(game.getAppId());
                v.setNameVideogame(game.getVideogameName());
                v.setProfile(profile);
                profile.getVideogames().add(v);
                ch.videogameService.save(v);
            }
        }
		if (games != null)
		{
            games=games.stream().sorted(Comparator.comparing(SingleOwnedGameDTO::isPreferred).reversed()).toList();
        }
        return games;
    }

    /**
     * Retrieves a list of preferred owned games for the current user.
     * This method obtains the user's profile using the authentication token
     * to filter the games marked as preferred in the user's videogame collection.
     *
     * It first fetches the user's preferred videogames from their profile
     * and converts them into a list of SingleOwnedGameDTO objects.
     *
     * @return a List of SingleOwnedGameDTO objects representing the preferred games owned by the user
     * @throws JsonProcessingException if there is an error processing the JSON response
     */
    @GetMapping("/preferred")
    public List<SingleOwnedGameDTO> getListPreferredOwnedGamesDto() throws JsonProcessingException
    {
        Profile profile=credentialService.getUserByToken().getProfile();
        List<Videogame> gamesPreferred=profile.getVideogames().stream().filter(v -> v.isPreferred()).toList();
        List<SingleOwnedGameDTO> res=new ArrayList<>();
        for(Videogame v : gamesPreferred)
        {
            res.add(dtoSteamConverter.toOwnedGame(v));
        }
        return res;
    }


    /**
     * Retrieves detailed information about a specific videogame based on its application ID (appId).
     * This method uses the Steam API to fetch the videogame's details and updates the local
     * videogame data in the database.
     *
     * It first obtains the videogame details from the Steam API, then retrieves the existing
     * videogame entry from the local database using the appId. If an existing entry is found,
     * it updates the details based on the Steam API response and saves the updated entry back
     * to the database. Finally, it converts the updated videogame object into a VideogameDetailDTO
     * and returns it.
     *
     * @param appId the application ID of the videogame to retrieve details for
     * @return a VideogameDetailDTO object containing detailed information about the videogame
     * @throws JsonProcessingException if there is an error processing the JSON response from the Steam API
     */
    @GetMapping("/{appId}")
    public VideogameDetailDTO getVideogameDetail(@PathVariable("appId") Long appId) throws JsonProcessingException {
        String videogameDetailJson = steamAPIService.getOneVideogameDetail(appId);
        Videogame videogame = ch.findOneVideogameByAppId(appId);
        if(videogame!=null)
        {
            videogame = dtoSteamConverter.toVideogameFromSteam(videogameDetailJson, videogame);
            ch.videogameService.save(videogame);
        }
        return dtoSteamConverter.toVideogameDetailFromSteam(appId,videogame,videogameDetailJson);
    }

    /**
     * Updates the details of a specific videogame owned by the user.
     * This method retrieves the videogame based on the provided application ID (appId)
     * from the request body. If the videogame exists, it updates its number of stars
     * and whether it is marked as preferred. The updated videogame details are then
     * saved back to the database.
     *
     * The user's profile is obtained using the authentication token to ensure that
     * the request is valid and associated with the correct user.
     *
     * @param req the SingleOwnedGameDTO object containing the updated videogame details
     * @return a Response object indicating the success or failure of the update operation
     */
    @PutMapping
    public Response updateVideogame(@RequestBody SingleOwnedGameDTO req)
    {
        Videogame videogame=ch.findOneVideogameByAppId(req.getAppId());
        if(videogame!=null)
        {
            videogame.setNumberOfStars(req.getNumberOfStars());
            videogame.setPreferred(req.isPreferred());
            ch.videogameService.save(videogame);
            return new Response("Successfully updated videogame");
        }
        return new Response("Could not find videogame");
    }
}
