package com.generation.vsnbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SteamAPIService {

    private final static String key="F94F9544AEB0C802112742B29BB670DD";

    @Autowired
    private RestTemplate restTemplate;

    public String getPlayerSummary(String steamId){

        String url="http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key="+key+"&steamids="+steamId;
        return restTemplate.getForObject(url, String.class);
    }

    public String getPlayerAchievements(String steamId, String appId)
    {
        String url="http://api.steampowered.com/ISteamUserStats/GetPlayerAchievements/v0001/?appid="+appId+"&key="+key+"&steamid="+steamId;
        return restTemplate.getForObject(url, String.class);
    }

    public String getAchievementsIcons(String steamId, String appId)
    {
        String url="http://api.steampowered.com/ISteamUserStats/GetSchemaForGame/v0002/?key="+steamId+"&appid="+appId+"&l=english&format=json";
        return restTemplate.getForObject(url, String.class);
    }

    public String getPublishedFiles(String steamId, String appId, int fileType)
    {
        String url="https://api.steampowered.com/IPublishedFileService/GetUserFiles/v1/?key="+key+"&appid="+appId+"&steamid="+steamId+"&filetype="+fileType+"&format=json";
        return restTemplate.getForObject(url, String.class);
    }

    public String getPlayerGames(String steamId)
    {
        String url="http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key="+key+"&steamid="+steamId+"&include_appinfo=true&format=json";
        return restTemplate.getForObject(url, String.class);
    }


}
