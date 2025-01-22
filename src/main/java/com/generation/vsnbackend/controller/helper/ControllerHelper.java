package com.generation.vsnbackend.controller.helper;


import com.generation.vsnbackend.model.entities.*;
import com.generation.vsnbackend.model.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ControllerHelper
{
	public final GenericService<User, Long> userService;
	public final GenericService<Comment, Long> commentService;
	public final GenericService<Post, Long> postService;
	public final GenericService<Review, Long> reviewService;
	public final GenericService<Videogame, Long> videogameService;
	public final GenericService<Profile, Long> profileService;


	@Autowired
	public ControllerHelper(UserRepository userRepo, CommentRepository commentRepo, PostRepository postRepo, ReviewRepository reviewRepo, VideogameRepository videogameRepo, ProfileRepository profileRepo)
	{
		this.userService = new GenericService<>(userRepo);
		this.commentService=new GenericService<>(commentRepo);
		this.postService=new GenericService<>(postRepo);
		this.reviewService=new GenericService<>(reviewRepo);
		this.videogameService=new GenericService<>(videogameRepo);
		this.profileService=new GenericService<>(profileRepo);
	}

}
