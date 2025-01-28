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
import com.generation.vsnbackend.model.entities.signin.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
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
        //quelli che seguo
        User user=credentialService.getUserByToken();
        List<FriendSummaryDTO> followings=new ArrayList<>();
        for(Friend f:user.getProfile().getFollowers())
            followings.add(dtoConverter.toFriendSummaryDTO(f));
        return followings;
    }

    @GetMapping("/follower")
    public List<FriendSummaryDTO> getAllFollowers(){
        //quelli che mi seguono
        User user=credentialService.getUserByToken();
        List<FriendSummaryDTO> followers=new ArrayList<>();
        for(Friend f:user.getProfile().getFollowings())
            followers.add(dtoConverter.toFriendSummaryDTOxFollower(f));
        return followers;
    }

    @GetMapping("/followers/{id}")
    public List<FriendSummaryDTO> getAllFriends(@PathVariable Long id){

        //per avere i followers di chi sta nell'id (diverso da me)
        Profile profile = ch.profileService.getOneById(id);

        List<FriendSummaryDTO> friends=new ArrayList<>();
        for(Friend f : profile.getFollowers())
        {
            friends.add(dtoConverter.toFriendSummaryDTO(f));
        }
        return friends;
    }

    @GetMapping("/followings/{id}")
    public List<FriendSummaryDTO> getAllFriendsBis(@PathVariable Long id){

        //per avere i following di chi sta nell'id (diverso da me)
        Profile profile = ch.profileService.getOneById(id);

        List<FriendSummaryDTO> friends=new ArrayList<>();
        for(Friend f : profile.getFollowings())
        {
            friends.add(dtoConverter.toFriendSummaryDTOxFollower(f));
        }
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
        User userRequesting=credentialService.getUserByToken();

        if(Objects.equals(friendProfileId, userRequesting.getProfile().getId())) {
            throw new FriendException("Non puoi diventare amico di te stesso, coglione");
        }
        Profile profileReceiving =ch.profileService.getOneById(friendProfileId);

        Friend friend = new Friend();

        friend.setProfile_follower(userRequesting.getProfile());
        friend.setProfile_following(profileReceiving);
        ch.friendService.save(friend);

        profileReceiving.getFollowers().add(friend);
        userRequesting.getProfile().getFollowings().add(friend);

        ch.profileService.save(profileReceiving);
        ch.profileService.save(userRequesting.getProfile());
        ch.userService.save(userRequesting);
        ch.userService.save(profileReceiving.getUser());


        return dtoConverter.toFriendSummaryDTO(friend);
    }

    @DeleteMapping("/followings/{idProfileToUnfollow}")
    public Response unfollow(@PathVariable Long idProfileToUnfollow){
        User userRequesting=credentialService.getUserByToken();
        Long idProfileRequesting=userRequesting.getProfile().getId();
        Friend friendToUnfollow=ch.getOneFriendByFollowingIdAndFollowerId(idProfileToUnfollow,idProfileRequesting);

        if(friendToUnfollow==null)
        {
            return new Response("Couldn't find friend");
        }

        Long friendId=friendToUnfollow.getId();

        Profile profileReceiving =ch.profileService.getOneById(idProfileToUnfollow);
        Friend friend = ch.friendService.getOneById(friendId);

        userRequesting.getProfile().getFollowers().remove(friend);
        profileReceiving.getFollowings().remove(friend);

        ch.profileService.save(profileReceiving);
        ch.profileService.save(userRequesting.getProfile());
        ch.userService.save(userRequesting);
        ch.userService.save(profileReceiving.getUser());

        ch.friendService.deleteById(friendId);
        return new Response("Removed following");
    }
}
