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

import java.util.List;

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

    @GetMapping
    public List<SingleOwnedGameDTO> getListOwnedGamesDto() throws JsonProcessingException
    {
        Profile profile=credentialService.getUserByToken().getProfile();
        List<SingleOwnedGameDTO> games=dtoSteamConverter.toListOfOwnedGames(steamAPIService.getPlayerGames(profile.getUser().getSteamId()));
        List<Videogame> gamesDb=profile.getVideogames();
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
        return games;
    }


    @GetMapping("/{appId}")
    public VideogameDetailDTO getVideogameDetail(@PathVariable("appId") Long appId) throws JsonProcessingException {
        String videogameDetailJson = steamAPIService.getOneVideogameDetail(appId);
        Videogame videogame = ch.findOneVideogameByAppId(appId);
        videogame=dtoSteamConverter.toVideogameFromSteam(steamAPIService.getOneVideogameDetail(appId),videogame);
        ch.videogameService.save(videogame);
        return dtoSteamConverter.toVideogameDetailFromSteam(videogame,steamAPIService.getOneVideogameDetail(videogame.getAppId()));
    }


    @PutMapping
    public Response updateVideogame(@RequestBody Videogame videogame)
    {
        ch.videogameService.save(videogame);
        return new Response("Successfully updated videogame");
    }
}
