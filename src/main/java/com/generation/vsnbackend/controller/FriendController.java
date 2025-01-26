package com.generation.vsnbackend.controller;

import com.generation.vsnbackend.controller.exception.FriendException;
import com.generation.vsnbackend.controller.helper.ControllerHelper;
import com.generation.vsnbackend.model.dto.DTOConverter;
import com.generation.vsnbackend.model.dto.FriendSummaryDTO;
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
    ControllerHelper ch;

    @GetMapping("/following")
    public List<FriendSummaryDTO> getAllFriends(){
        User user=credentialService.getUserByToken();
        List<FriendSummaryDTO> friends=new ArrayList<>();
        for(Friend f:user.getProfile().getFriends())
            friends.add(dtoConverter.toFriendSummaryDTO(f));
        return friends;
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

        ch.friendService.save(friend);
        ch.profileService.save(friendOfUser);
        ch.userService.save(user);
        ch.userService.save(friendOfUser.getUser());


        return dtoConverter.toFriendSummaryDTO(friend);
    }


}
