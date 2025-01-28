package com.generation.vsnbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.generation.vsnbackend.controller.exception.NoAchievementException;
import com.generation.vsnbackend.controller.helper.ControllerHelper;
import com.generation.vsnbackend.controller.helper.FileDataService;
import com.generation.vsnbackend.model.dtoSteam.*;
import com.generation.vsnbackend.model.dtoSteam.DTOSteamConverter;
import com.generation.vsnbackend.model.dtoSteam.NewsDTO;
import com.generation.vsnbackend.model.dtoSteam.PlayerDTO;
import com.generation.vsnbackend.model.entities.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/steam")
public class SteamController {

    private final static int NUMBER_OF_RECOMMENDATIONS=3;

    @Autowired
    SteamAPIService steamAPIService;

    @Autowired
    DTOSteamConverter dtoSteamConverter;

    @Autowired
    CredentialService credentialService;

    @Autowired
    private FileDataService fileDataService;

    @Autowired
    ControllerHelper ch;

    /**
     * Retrieves the Player Data Transfer Object (DTO) for the current user based on their Steam ID.
     * This method uses the user's profile, obtained from the authentication token, to fetch
     * the player's summary from the Steam API and convert it into a PlayerDTO.
     *
     * @return a PlayerDTO object containing the player's information retrieved from the Steam API
     * @throws JsonProcessingException if there is an error processing the JSON response from the Steam API
     */
    @GetMapping("/player")
    public PlayerDTO getPlayerDto() throws JsonProcessingException {
        Profile profile=credentialService.getUserByToken().getProfile();
        return dtoSteamConverter.toPlayerDTO(steamAPIService.getPlayerSummary(profile.getUser().getSteamId()));
    }

    /**
     * Retrieves the latest news articles for a specific videogame based on its app ID.
     * This method calls the Steam API to fetch the news in JSON format and converts
     * it into a list of NewsDTO objects for further processing.
     *
     * @param appId the app ID of the video game for which news articles are to be retrieved
     * @return a List of NewsDTO objects containing the latest news articles for the specified video game
     * @throws JsonProcessingException if there is an error processing the JSON response from the Steam API
     */
    @GetMapping("/news/{appId}")
    public List<NewsDTO> getNewsVideogame(@PathVariable Long appId) throws JsonProcessingException {
        String json= steamAPIService.getVideogameNews(appId);
        return dtoSteamConverter.toNewsDTOs(json);
    }

    /**
     * Retrieves a list of achievements obtained by the user for a specific videogame based on its app ID.
     * This method first retrieves the user's profile using the authentication token, then fetches
     * the player's achievements from the Steam API, and finally converts the data into a list of AchievementDTO objects.
     *
     * @param appid the app ID of the videogame for which achievements are to be retrieved
     * @return a List of AchievementDTO objects representing the achievements obtained by the user for the specified game,
     *         or null if there are no achievements found for the user
     * @throws JsonProcessingException if there is an error processing the JSON response from the Steam API
     */
    @GetMapping("/achievements/{appid}")
    public List<AchievementDTO> getListOfObtainedAchievements(@PathVariable Long appid) throws JsonProcessingException
	{
        try
        {
            Profile profile = credentialService.getUserByToken().getProfile();
            String steamId = profile.getUser().getSteamId();
            Set<String> setAchievements = dtoSteamConverter.toSetOfObtainedAchievements(steamAPIService.getPlayerAchievements(steamId, appid));
            System.out.println(setAchievements);
            return dtoSteamConverter.toListOfObtainedAchievements(steamAPIService.getAchievementsInfo(appid), setAchievements);
        }
        catch (NoAchievementException e)
        {
            return null;
        }
    }

    /**
     * Retrieves a list of recommended games based on the user's playtime data from Steam.
     *
     * This method fetches the user's profile using the current authentication token,
     * then requests recommended game app IDs from the Steam API based on the user's
     * playtime clusters. It randomly selects game IDs and retrieves their details until
     * the specified number of recommendations is reached.
     *
     * @return a list of {@link RecommendationDTO} containing the recommended games.
     * @throws JsonProcessingException if there is an error processing the JSON response
     *                                  from the Steam API.
     */
    @GetMapping("/recommendations")
    public List<RecommendationDTO> getRecommendations() throws JsonProcessingException
	{
        Profile profile = credentialService.getUserByToken().getProfile();

        List<Long> appIds=dtoSteamConverter.toListOfAppIdsRecommendedFromSteam(steamAPIService.postClusterBasedOnPlaytime(profile.getUser().getSteamId()));
        List<RecommendationDTO> recommendationDTOS=new ArrayList<>();

        while(recommendationDTOS.size()<NUMBER_OF_RECOMMENDATIONS)
        {
            int randomIndex=(int) (Math.random()*appIds.size());
            Long appId=appIds.get(randomIndex);
            VideogameDetailDTO videogameDetailDTO= dtoSteamConverter.toVideogameDetailFromSteamForRecommedation(appId,steamAPIService.getOneVideogameDetail(appId));
            if(videogameDetailDTO.getNameVideogame()!=null)
            {
                recommendationDTOS.add(dtoSteamConverter.toRecommendationDTOFromDetail(videogameDetailDTO));
            }
        }
        return recommendationDTOS;
    }

}
