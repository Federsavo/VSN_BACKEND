package com.generation.vsnbackend.controller;

import com.generation.vsnbackend.controller.exception.NoAchievementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class SteamAPIService {

    private static final String key="F94F9544AEB0C802112742B29BB670DD";
    private static final int COUNT=5;
    private static final int MAXLENGTH=300;


    @Autowired
    private RestTemplate restTemplate;

    /**
     * Retrieves the player summary for a specific Steam user based on their Steam ID.
     * This method makes a call to the Steam API and returns the response as a JSON string.
     *
     * @param steamId the Steam ID of the player whose summary is to be retrieved
     * @return a JSON string containing the player summary data
     * @throws RestClientException if there is an error during the API call
     */
    public String getPlayerSummary(String steamId){

        String url="https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key="+key+"&steamids="+steamId;
        return restTemplate.getForObject(url, String.class);
    }


    /**
     * Retrieves the achievements for a specific player in a given videogame based on their Steam ID and the game's app ID.
     * This method makes a call to the Steam API and returns the response as a JSON string. If the player has no achievements,
     * a NoAchievementException is thrown.
     *
     * @param steamId the Steam ID of the player whose achievements are to be retrieved
     * @param appId the app ID of the game for which achievements are being requested
     * @return a JSON string containing the player's achievement data
     * @throws NoAchievementException if the player has no achievements for the specified game
     */
    public String getPlayerAchievements(String steamId, Long appId) throws NoAchievementException
	{
        try
        {
            String url = "https://api.steampowered.com/ISteamUserStats/GetPlayerAchievements/v0001/?appid=" + appId + "&key=" + key + "&steamid=" + steamId;
            return restTemplate.getForObject(url, String.class);
        }
        catch (Exception e)
        {
            throw new NoAchievementException();
        }
    }

    /**
     * Retrieves achievement information for a specific videogame based on its app ID.
     * This method makes a call to the Steam API and returns the response as a JSON string,
     * which includes details about the achievements available in the specified game.
     *
     * @param appId the app ID of the game for which achievement information is requested
     * @return a JSON string containing the achievement schema for the specified game
     */
    public String getAchievementsInfo(Long appId)
    {
        String url="https://api.steampowered.com/ISteamUserStats/GetSchemaForGame/v0002/?key="+key+"&appid="+appId+"&l=english&format=json";
        return restTemplate.getForObject(url, String.class);
    }

    public String getPublishedFiles(String steamId, Long appId, int fileType)
    /**
     * Retrieves the published files for a specific Steam user and game based on their Steam ID, app ID, and file type.
     * This method makes a call to the Steam API and returns the response as a JSON string,
     * which includes details about the user's published files for the specified game.
     *
     * @param steamId the Steam ID of the user whose published files are to be retrieved
     * @param appId the app ID of the game for which published files are requested
     * @param fileType the type of files to retrieve (e.g., 0 for all file types)
     * @return a JSON string containing the published files for the specified user and game
     */
    public String getPublishedFiles(String steamId, Long appId, int fileType)
    {
        String url="https://api.steampowered.com/IPublishedFileService/GetUserFiles/v1/?key="+key+"&appid="+appId+"&steamid="+steamId+"&filetype="+fileType+"&format=json";
        return restTemplate.getForObject(url, String.class);
    }


    /**
     * Retrieves the videogames owned by a specific Steam user based on their Steam ID.
     * This method makes a call to the Steam API and returns the response as a JSON string,
     * which includes details about the games owned by the specified user.
     *
     * @param steamId the Steam ID of the user whose owned games are to be retrieved
     * @return a JSON string containing the list of games owned by the specified user, including app information
     */
    public String getPlayerGames(String steamId)
    {
        String url="https://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key="+key+"&steamid="+steamId+"&include_appinfo=true&format=json";
        return restTemplate.getForObject(url, String.class);
    }

    /**
     * Retrieves the last played videogame for a specific Steam user based on their Steam ID.
     * This method makes a call to the Steam API and returns the response as a JSON string,
     * which includes details about the games that the user has recently played.
     *
     * @param steamId the Steam ID of the user whose last played game is to be retrieved
     * @return a JSON string containing the last played game information for the specified user
     */
    public String getLastPlayedGame(String steamId){

        String url="https://api.steampowered.com/IPlayerService/GetRecentlyPlayedGames/v0001/?key="+key+"&steamid="+steamId+"&format=json";
        return restTemplate.getForObject(url, String.class);
    }


    /**
     * Constructs the URL for the image of a specific videogame based on its app ID and image icon name.
     * This method returns a formatted URL that points to the game's image on the Steam community server.
     *
     * @param appId the app ID of the video game for which the image URL is being constructed
     * @param imageIcon the name of the image icon file (without extension) used to form the URL
     * @return a String representing the URL of the video game's image
     */
    public String getUrlImageVideogame(Long appId, String imageIcon){

        String url="https://media.steampowered.com/steamcommunity/public/images/apps/"+appId+"/"+imageIcon+".jpg";
        return url;
    }

    /**
     * Retrieves the latest news for a specific videogame based on its app ID.
     * This method makes a call to the Steam API and returns the response as a JSON string,
     * which includes up to five news articles related to the specified videogame.
     *
     * @param appId the app ID of the video game for which news is to be retrieved
     * @return a JSON string containing the latest news articles for the specified videogame
     */
    public String getVideogameNews(Long appId){
        //solo 5 news
        String url="http://api.steampowered.com/ISteamNews/GetNewsForApp/v0002/?appid="+appId+"&count="+COUNT+"&maxlength="+MAXLENGTH+"&format=json";
        return restTemplate.getForObject(url, String.class);
    }

    /**
     * Retrieves the details of a specific videogame based on its app ID.
     * This method makes a call to the Steam API and returns the response as a JSON string,
     * which includes comprehensive information about the specified game.
     *
     * @param appId the app ID of the video game for which details are to be retrieved
     * @return a JSON string containing the details of the specified videogame
     */
    public String getOneVideogameDetail(Long appId){
        String url="https://store.steampowered.com/api/appdetails?appids="+appId;
        return restTemplate.getForObject(url, String.class);
    }

    public String postClusterBasedOnPlaytime(String steamId)
    {
        String url="https://api.steampowered.com/IStoreAppSimilarityService/IdentifyClustersFromPlaytime/v1/?key="+key+"&steamid="+steamId+"&format=json";
        return restTemplate.postForObject(url,null, String.class);
    }
}
