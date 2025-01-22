package com.generation.vsnbackend.controller;

import com.generation.vsnbackend.controller.helper.ControllerHelper;
import com.generation.vsnbackend.model.dto.DTOConverter;
import com.generation.vsnbackend.model.dto.PostDTOReq;
import com.generation.vsnbackend.model.dto.PostDTOResp;
import com.generation.vsnbackend.model.entities.Post;
import com.generation.vsnbackend.model.entities.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    ControllerHelper ch;

    @Autowired
    DTOConverter dtoConverter;
    @Autowired
    private CredentialService credentialService;

    @GetMapping()
    List<PostDTOResp> getAllPosts(){
        List<PostDTOResp> posts = new ArrayList<>();
        Profile profile=credentialService.getUserByToken().getProfile();
        for (Post post:profile.getPosts()){
            posts.add(dtoConverter.toPostDTOResp(post));
        }
        return posts;
    }

//    @GetMapping("/{profileId}/{id}")
//    PostDTOResp getOnePost(@PathVariable Long profileId, @PathVariable Long id){
//        PostDTOResp post=new PostDTOResp();
//        for(Post p : ch.profileService.getOneById(profileId).getPosts()){
//            if(p.getId() == id){
//                post=dtoConverter.toPostDTOResp(p);
//            }
//        }
//        return post;
//    }

    @PostMapping
    Post insertPost(@RequestBody PostDTOReq postDTOReq){
        return ch.postService.save(dtoConverter.toPostEntity(postDTOReq));
    }
}
