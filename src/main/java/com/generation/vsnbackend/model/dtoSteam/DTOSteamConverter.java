package com.generation.vsnbackend.model.dtoSteam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.generation.vsnbackend.controller.SteamAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DTOSteamConverter {

    @Autowired
    SteamAPIService steamAPIService;

    public PlayerDTO toPlayerDTO(String json) throws JsonProcessingException
    {
        PlayerDTO playerDTO = new PlayerDTO();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);

        // Naviga nel JSON fino alla lista dei giocatori
        JsonNode playerNode = rootNode.path("response").path("players").get(0); // Ottieni il primo giocatore

        playerDTO.setPersonaName(playerNode.path("personaname").asText());
        playerDTO.setProfileUrl(playerNode.path("profileurl").asText());
        playerDTO.setAvatar(playerNode.path("avatar").asText());
        playerDTO.setAvatarMedium(playerNode.path("avatarmedium").asText());
        playerDTO.setAvatarFull(playerNode.path("avatarfull").asText());
        playerDTO.setRealName(playerNode.path("realname").asText());

        int secondi=playerNode.path("lastlogoff").asInt();
        LocalDate lastLogOff = Instant.ofEpochSecond(secondi)
                .atZone(ZoneId.systemDefault()) // Use the system's default timezone
                .toLocalDate();
        playerDTO.setLastLogOff(lastLogOff);

        secondi=playerNode.path("timecreated").asInt();
        LocalDate timeCreated = Instant.ofEpochSecond(secondi)
                .atZone(ZoneId.systemDefault()) // Use the system's default timezone
                .toLocalDate();

        playerDTO.setTimeCreated(timeCreated);

        playerDTO.setLocCountryCode(playerNode.path("loccountrycode").asText());
        playerDTO.setPrimaryClanId(playerNode.path("primaryclanid").asText());


        return playerDTO;
    }

    public int getNumberOfGames(String json) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        return rootNode.path("response").path("game_count").asInt();
    }

    public List<SingleOwnedGameDTO> toListOfOwnedGames(String json) throws JsonProcessingException
    {
        int numberOfGames = getNumberOfGames(json);
        List<SingleOwnedGameDTO> ownedGames = new ArrayList<>();

        for(int i = 0; i < numberOfGames; i++)
        {
            SingleOwnedGameDTO singleOwnedGameDTO = new SingleOwnedGameDTO();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode playerNode = rootNode.path("response").path("games").get(i);
            singleOwnedGameDTO.setAppId(playerNode.path("appid").asText());
            singleOwnedGameDTO.setVideogameName(playerNode.path("name").asText());
            String imgUrl = playerNode.path("img_icon_url").asText();
            singleOwnedGameDTO.setIconImgUrl(steamAPIService.getUrlImageVideogame(playerNode.path("appid").asLong(),imgUrl));

            ownedGames.add(singleOwnedGameDTO);
        }
        return ownedGames;
    }

    public Set<String> toSetOfObtainedAchievements(String json) throws JsonProcessingException
	{
        Set<String> achievements = new HashSet<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode playerNode = rootNode.path("playerstats").path("achievements");
        for(int i = 0; i < playerNode.size(); i++)
        {
            if(playerNode.path(i).path("achieved").asText().equals("1"))
            {
                String achievement=playerNode.path(i).path("apiname").asText();
                achievements.add(achievement);
            }
        }
        return achievements;
    }

    public List<AchievementDTO> toListOfObtainedAchievements(String json,Set<String> achievementsNames) throws JsonProcessingException
    {
        List<AchievementDTO> achievements = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode achievementsNode = rootNode.path("game").path("availableGameStats").path("achievements");
        for(int i=0; i<achievementsNode.size(); i++)
        {
            JsonNode node = achievementsNode.get(i);
            if(achievementsNames.contains(node.path("name").asText()))
            {
                AchievementDTO achievementDTO = new AchievementDTO();
                achievementDTO.setAchievementName(node.path("displayName").asText());
                achievementDTO.setDescription(node.path("description").asText());
                achievementDTO.setIconUrl(node.path("icon").asText());
                achievements.add(achievementDTO);
            }
        }
        return achievements;
    }


    public List<NewsDTO> toNewsDTOs(String json) throws JsonProcessingException {
        List<NewsDTO> newsDTOs = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);

        for(int i=0; i<5; i++) {

            NewsDTO newsDTO = new NewsDTO();
            JsonNode news = rootNode.path("appnews").path("newsitems").get(i);

            newsDTO.setUrl(news.path("url").asText());
            newsDTO.setTitle(news.path("title").asText());
            newsDTO.setAuthor(news.path("author").asText());
            newsDTO.setContents(news.path("contents").asText());
            newsDTO.setFeedLabel(news.path("feedlabel").asText());
            newsDTO.setFeedName(news.path("feedname").asText());
            newsDTO.setTags(news.path("tags").asText());
            int secondi=news.path("date").asInt();
            LocalDate date = Instant.ofEpochSecond(secondi)
                    .atZone(ZoneId.systemDefault()) // Use the system's default timezone
                    .toLocalDate();
            newsDTO.setDate(date);

            newsDTOs.add(newsDTO);
        }

        return newsDTOs;

    }

}
