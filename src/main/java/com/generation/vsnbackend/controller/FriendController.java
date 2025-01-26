package com.generation.vsnbackend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.generation.vsnbackend.controller.exception.FriendException;
import com.generation.vsnbackend.controller.helper.ControllerHelper;
import com.generation.vsnbackend.model.dto.DTOConverter;
import com.generation.vsnbackend.model.dto.FriendSummaryDTO;
import com.generation.vsnbackend.model.dto.ProfileDTOResp;
import com.generation.vsnbackend.model.dtoSteam.DTOSteamConverter;
import com.generation.vsnbackend.model.entities.Friend;
import com.generation.vsnbackend.model.entities.Profile;
import com.generation.vsnbackend.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/friends")
public class FriendController {

    @Autowired
    CredentialService credentialService;
    @Autowired
    DTOConverter dtoConverter;
    @Autowired
    DTOSteamConverter dtoSteamConverter;
    @Autowired
    SteamAPIService steamAPIService;

    @Autowired
    ControllerHelper ch;

    @GetMapping("/following")
    public List<FriendSummaryDTO> getAllFriends(){
        User user=credentialService.getUserByToken();
        List<FriendSummaryDTO> friends=new ArrayList<>();
        for(Friend f:user.getProfile().getFriends())
            friends.add(dtoConverter.toFriendSummaryDTO(f));
        return friends;
    }

    @GetMapping("/followers")
    public List<FriendSummaryDTO> getAllFriends(@PathVariable Long id){
        Profile profile = ch.profileService.getOneById(id);

        List<FriendSummaryDTO> friends=new ArrayList<>();
        for(Friend f :profile.getFriends())
            friends.add(dtoConverter.toFriendSummaryDTO(f));
        return friends;
    }

    @GetMapping("/following/{friendId}")
    public ProfileDTOResp getDetailFriend(@PathVariable Long friendId){

        //arriva id del profilo dell'amico di chi sta usando il sito
        Profile profile=ch.profileService.getOneById(friendId);

        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(steamAPIService.getLastPlayedGame(profile.getUser().getSteamId()));
            JsonNode lastVideogame = rootNode.path("response").path("games").get(0);
            profile.setLastPlayedVideogameAppId(lastVideogame.path("appid").asLong());
            profile.setLastPlayedGameImgUrl(steamAPIService.getUrlImageVideogame(profile.getLastPlayedVideogameAppId(), lastVideogame.path("img_icon_url").asText()));
            profile.setLastPlayedGameName(lastVideogame.path("name").asText());
            ch.profileService.save(profile);

            JsonNode steamName = objectMapper.readTree(steamAPIService.getPlayerSummary(profile.getUser().getSteamId()));
            profile.setSteamName(steamName.path("response").path("players").get(0).path("personaname").asText());
            profile.setProfileName(profile.getUser().getUsername());

            ch.profileService.save(profile);
            return dtoConverter.toProfileDtoResp(profile);
        }
        catch(Exception e)
        {
            return dtoConverter.toProfileDtoResp(profile);
        }
    }


    @PostMapping("/add/{friendProfileId}")
    public FriendSummaryDTO addOneFriend(@PathVariable Long friendProfileId){
        User user=credentialService.getUserByToken();

        if(Objects.equals(friendProfileId, user.getProfile().getId())) {
            throw new FriendException("Non puoi diventare amico di te stesso, coglione");
        }
        Profile friendOfUser = ch.profileService.getOneById(friendProfileId);

        Friend friend = new Friend();
        friend.setUser(friendOfUser.getUser());
        friendOfUser.getUser().setFriend(friend);
        //friend.getUser().setId(friendOfUser.getId());
        friend.setProfile(user.getProfile());
        user.getProfile().getFriends().add(friend);

        user.getProfile().setFollowingCount(user.getProfile().getFollowingCount() + 1);
        friendOfUser.setFollowersCount(friendOfUser.getFollowersCount() + 1);

        ch.friendService.save(friend);
        ch.profileService.save(friendOfUser);
        ch.userService.save(user);
        ch.userService.save(friendOfUser.getUser());

        return dtoConverter.toFriendSummaryDTO(friend);
    }


}
