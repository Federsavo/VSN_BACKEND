package com.generation.vsnbackend.model.dtoSteam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.generation.vsnbackend.controller.SteamAPIService;
import com.generation.vsnbackend.model.entities.Videogame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DTOSteamConverter {

    @Autowired
    SteamAPIService steamAPIService;

    /**
     * Converts a JSON string representing player data into a PlayerDTO object.
     *
     * @param json The JSON string containing player data from the Steam API response.
     * @return A PlayerDTO object populated with player information.
     * @throws JsonProcessingException if there is an error processing the JSON string.
     */
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

    /**
     * Retrieves the number of videogames from a JSON string representing player data from the Steam API response.
     *
     * @param json The JSON string containing player data, including the game count.
     * @return The number of games owned by the player, as an integer.
     * @throws JsonProcessingException if there is an error processing the JSON string.
     */
    public int getNumberOfGames(String json) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        return rootNode.path("response").path("game_count").asInt();
    }

    /**
     * Converts a JSON string representing a player's owned games into a list of SingleOwnedGameDTO objects.
     *
     * @param json   The JSON string containing player-owned games data from the Steam API response.
     * @param gamesDb A list of Videogame objects representing the videogames in the database, used to retrieve additional details.
     * @return A list of SingleOwnedGameDTO objects representing the player's owned videogames.
     * @throws JsonProcessingException if there is an error processing the JSON string.
     */
    public List<SingleOwnedGameDTO> toListOfOwnedGames(String json, List<Videogame> gamesDb) throws JsonProcessingException
    {
        int numberOfGames = getNumberOfGames(json);
        List<SingleOwnedGameDTO> ownedGames = new LinkedList<>();

        for(int i = 0; i < numberOfGames; i++)
        {
            SingleOwnedGameDTO singleOwnedGameDTO = new SingleOwnedGameDTO();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode playerNode = rootNode.path("response").path("games").get(i);
            singleOwnedGameDTO.setAppId(Long.valueOf(playerNode.path("appid").asText()));
            singleOwnedGameDTO.setVideogameName(playerNode.path("name").asText());
            String imgUrl = playerNode.path("img_icon_url").asText();
            Long appId = playerNode.path("appid").asLong();
            singleOwnedGameDTO.setIconImgUrl(steamAPIService.getUrlImageVideogame(appId,imgUrl));
            if(gamesDb!=null&&!gamesDb.isEmpty()&&i<gamesDb.size())
            {
                singleOwnedGameDTO.setNumberOfStars(gamesDb.get(i).getNumberOfStars());
                singleOwnedGameDTO.setPreferred(gamesDb.get(i).isPreferred());
            }
            ownedGames.add(singleOwnedGameDTO);
        }
        return ownedGames;
    }

    /**
     * Converts a Videogame entity into a SingleOwnedGameDTO object.
     *
     * @param videogame The Videogame entity to be converted.
     * @return A SingleOwnedGameDTO object containing the details of the videogame.
     */
    public SingleOwnedGameDTO toOwnedGame(Videogame videogame)
    {
        SingleOwnedGameDTO singleOwnedGameDTO = new SingleOwnedGameDTO();
        singleOwnedGameDTO.setAppId(videogame.getAppId());
        singleOwnedGameDTO.setPreferred(videogame.isPreferred());
        singleOwnedGameDTO.setIconImgUrl(videogame.getIconImgUrl());
        singleOwnedGameDTO.setNumberOfStars(videogame.getNumberOfStars());
        singleOwnedGameDTO.setVideogameName(videogame.getNameVideogame());
        return singleOwnedGameDTO;
    }

    /**
     * Converts a JSON string representing player achievements into a set of achievement names
     * that the player has obtained.
     *
     * @param json The JSON string containing player statistics, including achievements.
     * @return A Set containing the names of achievements that have been obtained by the player.
     * @throws JsonProcessingException If there is an error processing the JSON input.
     */
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

    /**
     * Converts a JSON string representing available game achievements into a list of
     * AchievementDTO objects for the achievements that have been obtained by the player.
     *
     * @param json The JSON string containing game statistics, including available achievements.
     * @param achievementsNames A Set of achievement names that have been obtained by the player.
     * @return A List of AchievementDTO objects representing the details of the obtained achievements.
     * @throws JsonProcessingException If there is an error processing the JSON input.
     */
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

    /**
     * Converts a JSON string containing news data into a list of NewsDTO objects.
     * The method extracts information for the first five news items from the JSON structure.
     *
     * @param json The JSON string containing news information, structured according to the Steam API.
     * @return A List of NewsDTO objects representing the extracted news items.
     * @throws JsonProcessingException If there is an error processing the JSON input.
     */
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
            //da sistemare
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


    /**
     * Populates a Videogame object with data retrieved from a JSON string
     * representing a videogame's details from the Steam API.
     *
     * @param json The JSON string containing videogame information structured according to the Steam API.
     * @param videogame The Videogame object to be populated with the retrieved data.
     * @return The populated Videogame object.
     * @throws JsonProcessingException If there is an error processing the JSON input.
     */
    public Videogame toVideogameFromSteam (String json, Videogame videogame) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode videogameSteam = rootNode.path(videogame.getAppId().toString()); // Assicurati che questo sia corretto


        videogame.setDevelopers(videogameSteam.path("data").path("developers").get(0).asText());
        videogame.setPublishers(videogameSteam.path("data").path("publishers").get(0).asText());

        // Determine the correct date format based on the release date string
        DateTimeFormatter formatter;
        if(Character.isDigit(videogameSteam.path("data").path("release_date").path("date").asText().charAt(1)))
             formatter = DateTimeFormatter.ofPattern("dd MMM, yyyy",  Locale.ENGLISH);
        else {
            formatter = DateTimeFormatter.ofPattern("d MMM, yyyy", Locale.ENGLISH);
        }
        videogame.setReleaseDate(LocalDate.parse(videogameSteam.path("data").path("release_date").path("date").asText(), formatter));


        // Extract genres from the JSON and concatenate them into a single string
        String generi="";
        int numberOfGenres = videogameSteam.path("data").path("genres").size();
        for(int i=0;i<numberOfGenres;i++) {
            if (videogameSteam.path("data").path("genres").get(i) != null)
                generi += videogameSteam.path("data").path("genres").get(i).path("description").asText() + ", ";
            else {
                // Se non ci sono più elementi nell'array "genres", usciamo dal ciclo
                break;
            }
        }
        // Remove the trailing comma and space from the genres string
        if (!generi.isEmpty()) {
            generi = generi.substring(0, generi.length() - 2);
        }

        videogame.setGenre(generi);

        return videogame;

    }

    /**
     * Converts a Videogame object and its associated JSON data from the Steam API
     * into a VideogameDetailDTO object, encapsulating detailed information about the videogame.
     *
     * @param videogame The Videogame object containing basic information about the videogame.
     * @param json The JSON string containing detailed videogame information structured according to the Steam API.
     * @return A VideogameDetailDTO object populated with details from the Videogame and the provided JSON.
     * @throws JsonProcessingException If there is an error processing the JSON input.
     */
    public VideogameDetailDTO toVideogameDetailFromSteam (Videogame videogame, String json) throws JsonProcessingException {
        VideogameDetailDTO videogameDetailDTO = new VideogameDetailDTO();

        videogameDetailDTO.setId(videogame.getId());
        videogameDetailDTO.setNameVideogame(videogame.getNameVideogame());
        videogameDetailDTO.setDevelopers(videogame.getDevelopers());
        videogameDetailDTO.setPublishers(videogame.getPublishers());
        videogameDetailDTO.setPreferred(videogame.isPreferred());
        videogameDetailDTO.setReleaseDate(String.valueOf(videogame.getReleaseDate()));
        videogameDetailDTO.setGenre(videogame.getGenre());
        videogameDetailDTO.setStarReviews(videogame.getStarReviews());
        videogameDetailDTO.setAppId(videogame.getAppId());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);

        JsonNode videogameSteam = rootNode.path(videogame.getAppId().toString());

        videogameDetailDTO.setRequiredAge(Integer.parseInt( videogameSteam.path("data").path("required_age").asText()));
        videogameDetailDTO.setDetailedDescription(videogameSteam.path("data").path("detailed_description").asText());
        videogameDetailDTO.setShortDescription(videogameSteam.path("data").path("short_description").asText());
        videogameDetailDTO.setSupportedLanguages(videogameSteam.path("data").path("supported_languages").asText());
        videogameDetailDTO.setHeaderImageUrl(videogameSteam.path("data").path("header_image").asText());
        videogameDetailDTO.setWebsite(videogameSteam.path("data").path("website").asText());
        videogameDetailDTO.setPrice(videogameSteam.path("data").path("price_overview").path("final_formatted").asText());

        // Collect supported platforms
        String platform="";
        if(videogameSteam.path("data").path("platforms").path("windows").asBoolean(false))
            platform +="windows-";
        if(videogameSteam.path("data").path("platforms").path("mac").asBoolean(false))
            platform +="mac-";
        if(videogameSteam.path("data").path("platforms").path("linux").asBoolean(false))
            platform +="linux";

        // Remove trailing hyphen if it exists
        if (platform.endsWith("-")) {
            platform = platform.substring(0, platform.length() - 1);
        }
        videogameDetailDTO.setPlatforms(platform);

        //videogameDetailDTO.setTotalAchievements(Integer.parseInt(videogameSteam.path("data").path("achievements").path("total").asText()));


        return videogameDetailDTO;

    }

}
