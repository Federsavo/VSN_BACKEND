package com.generation.vsnbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SteamAPIService {

    private static String key="F94F9544AEB0C802112742B29BB670DD";

    @Autowired
    private RestTemplate restTemplate;

    public String getPlayerSummary(String steamId){

        String url="http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key="+key+"&steamids="+steamId;
        return restTemplate.getForObject(url, String.class);
    }

    public String getLastPlayedGame(String steamId){

        String url="http://api.steampowered.com/IPlayerService/GetRecentlyPlayedGames/v0001/?key="+key+"&steamid="+steamId+"&format=json";
        return restTemplate.getForObject(url, String.class);
    }

    public String getUrlImageLastVideogame(Long appId, String imageIcon){

        String url="http://media.steampowered.com/steamcommunity/public/images/apps/"+appId+"/"+imageIcon+".jpg";
        return url;
    }

}
