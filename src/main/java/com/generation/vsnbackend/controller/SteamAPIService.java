package com.generation.vsnbackend.controller;

import com.generation.vsnbackend.controller.exception.NoAchievementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SteamAPIService {

    private static final String key="F94F9544AEB0C802112742B29BB670DD";
    private static final int COUNT=5;
    private static final int MAXLENGTH=300;


    @Autowired
    private RestTemplate restTemplate;

    public String getPlayerSummary(String steamId){

        String url="https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key="+key+"&steamids="+steamId;
        return restTemplate.getForObject(url, String.class);
    }

    public String getPlayerAchievements(String steamId, String appId) throws NoAchievementException
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

    public String getAchievementsInfo(String appId)
    {
        String url="https://api.steampowered.com/ISteamUserStats/GetSchemaForGame/v0002/?key="+key+"&appid="+appId+"&l=english&format=json";
        return restTemplate.getForObject(url, String.class);
    }

    public String getPublishedFiles(String steamId, String appId, int fileType)
    {
        String url="https://api.steampowered.com/IPublishedFileService/GetUserFiles/v1/?key="+key+"&appid="+appId+"&steamid="+steamId+"&filetype="+fileType+"&format=json";
        return restTemplate.getForObject(url, String.class);
    }

    public String getPlayerGames(String steamId)
    {
        String url="https://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key="+key+"&steamid="+steamId+"&include_appinfo=true&format=json";
        return restTemplate.getForObject(url, String.class);
    }

    public String getLastPlayedGame(String steamId){

        String url="https://api.steampowered.com/IPlayerService/GetRecentlyPlayedGames/v0001/?key="+key+"&steamid="+steamId+"&format=json";
        return restTemplate.getForObject(url, String.class);
    }

    public String getUrlImageVideogame(Long appId, String imageIcon){

        String url="https://media.steampowered.com/steamcommunity/public/images/apps/"+appId+"/"+imageIcon+".jpg";
        return url;
    }

    public String getVideogameNews(Long appId){
        //solo 5 news
        String url="http://api.steampowered.com/ISteamNews/GetNewsForApp/v0002/?appid="+appId+"&count="+COUNT+"&maxlength="+MAXLENGTH+"&format=json";
        return restTemplate.getForObject(url, String.class);
    }

    public String getOneVideogameDetail(Long appId){
        String url="https://store.steampowered.com/api/appdetails?appids="+appId;
        return restTemplate.getForObject(url, String.class);
    }

    public String postClusterBasedOnPlaytime(Long steamId)
    {
        String url="https://api.steampowered.com/IStoreAppSimilarityService/IdentifyClustersFromPlaytime/v1/?key="+key+"&steamid="+steamId+"&format=json";
        return restTemplate.postForObject(url,null, String.class);
    }
}
