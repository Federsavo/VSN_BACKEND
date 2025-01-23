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
}
