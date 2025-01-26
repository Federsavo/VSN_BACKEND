package com.generation.vsnbackend.controller;

import com.generation.vsnbackend.controller.exception.PostContentException;
import com.generation.vsnbackend.controller.helper.ControllerHelper;
import com.generation.vsnbackend.model.dto.DTOConverter;
import com.generation.vsnbackend.model.dto.PostDTOReq;
import com.generation.vsnbackend.model.dto.PostDTOResp;
import com.generation.vsnbackend.model.entities.Post;
import com.generation.vsnbackend.model.entities.Profile;
import com.generation.vsnbackend.model.entities.signin.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    /**
     * Retrieves the list of posts for the authenticated user.
     *
     * @return a list of PostDTOResp objects representing the user's posts
     */
    @GetMapping()
    List<PostDTOResp> getUserPosts(){
        List<PostDTOResp> posts = new ArrayList<>();
        Profile profile=credentialService.getUserByToken().getProfile();

        for (int i=profile.getPosts().size()-1;i>=0;i--){
            posts.add(dtoConverter.toPostDTOResp(profile.getPosts().get(i)));
        }
        return posts;
    }

    /**
     * Retrieves a list of activity posts that do not belong to the authenticated user's profile.
     *
     * @return a list of PostDTOResp objects representing the user's activity posts
     */
    @GetMapping("/activity")
    List<PostDTOResp> getActivityPosts()
    {
        List<Post> posts = ch.postService.getList();
        List<PostDTOResp> postDTOResps = new ArrayList<>();
        Profile profile=credentialService.getUserByToken().getProfile();
        for(int i=posts.size()-1;i>=0;i--)
        {
            Post post=posts.get(i);
            if(!(post.getProfile().equals(profile)))
                postDTOResps.add(dtoConverter.toPostDTOResp(post));
        }
        return postDTOResps;
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

    /**
     * Inserts a new post for the authenticated user.The user is identified
     * by the token provided in the request, which is used to retrieve the user's profile.
     *
     * @param postDTOReq the data transfer object containing post details
     * @return a PostDTOResp object representing the inserted post
     * @throws PostContentException if the post content is empty or exceeds the maximum allowed size
     */
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

    /**
     * Deletes a post associated with the authenticated user. The user's profile is retrieved
     * using the token from the request, which is used to identify the post to be deleted.
     *
     * @param postId the ID of the post to be deleted
     * @return a Response indicating the result of the deletion operation
     */
    @DeleteMapping("/{postId}")
    Response deletePost(@PathVariable Long postId)
    {
        Profile profile = credentialService.getUserByToken().getProfile();
        for(Post post:profile.getPosts())
            if(post.getId().equals(postId))
                ch.postService.deleteById(postId);
        return new Response("Deleted post");
    }
}
