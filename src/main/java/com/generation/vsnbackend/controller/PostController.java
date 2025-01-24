package com.generation.vsnbackend.controller;

import com.generation.vsnbackend.controller.exception.PostContentException;
import com.generation.vsnbackend.controller.helper.ControllerHelper;
import com.generation.vsnbackend.model.dto.DTOConverter;
import com.generation.vsnbackend.model.dto.PostDTOReq;
import com.generation.vsnbackend.model.dto.PostDTOResp;
import com.generation.vsnbackend.model.entities.Post;
import com.generation.vsnbackend.model.entities.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final static int MAX_POSTS_CONTENT_SIZE = 255;

    @Autowired
    ControllerHelper ch;
    @Autowired
    DTOConverter dtoConverter;
    @Autowired
    private CredentialService credentialService;

    @GetMapping()
    List<PostDTOResp> getUserPosts(){
        List<PostDTOResp> posts = new ArrayList<>();
        Profile profile=credentialService.getUserByToken().getProfile();
        for (int i=profile.getPosts().size()-1;i>=0;i--){
            posts.add(dtoConverter.toPostDTOResp(profile.getPosts().get(i)));
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
    PostDTOResp insertPost(@RequestBody PostDTOReq postDTOReq){

        if(postDTOReq.getContent().isBlank()||postDTOReq.getContent().length()>MAX_POSTS_CONTENT_SIZE)
            throw new PostContentException("Post content is too big or empty");
        Profile profile = credentialService.getUserByToken().getProfile();
        Post post = dtoConverter.toPostEntity(postDTOReq);
        ch.profileService.save(profile);
        profile.getPosts().add(post);
        post.setProfile(profile);
        ch.postService.save(post);
        return dtoConverter.toPostDTOResp(post);
    }
}
