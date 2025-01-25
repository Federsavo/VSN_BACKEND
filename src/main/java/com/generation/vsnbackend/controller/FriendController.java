package com.generation.vsnbackend.controller;

import com.generation.vsnbackend.controller.helper.ControllerHelper;
import com.generation.vsnbackend.model.dto.DTOConverter;
import com.generation.vsnbackend.model.dtoSteam.DTOSteamConverter;
import com.generation.vsnbackend.model.entities.Friend;
import com.generation.vsnbackend.model.entities.Profile;
import com.generation.vsnbackend.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping
    public List<Friend> getAllFriends(){
        User user=credentialService.getUserByToken();
        List<Friend> friends=new ArrayList<>();
        for(Friend f:user.getProfile().getFriends())
            friends.add(f);
        return friends;
    }



    @PostMapping("/add/{friendProfileId}")
    public String addOneFriend(@PathVariable Long friendProfileId){
        User user=credentialService.getUserByToken();
        Profile friendOfUser=ch.profileService.getOneById(friendProfileId);

        Friend friend=new Friend();
        friend.setUser(friendOfUser.getUser());
        friend.getUser().setId(friendOfUser.getId());
        ch.friendService.save(friend);

        user.getProfile().getFriends().add(friend);
        ch.userService.save(user);



        return "salvato";
    }


}
