package com.generation.vsnbackend.controller.helper;


import com.generation.vsnbackend.model.entities.*;
import com.generation.vsnbackend.model.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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


	/**
	 *This method finds and returns a videogame by its app ID in db
	 * @param appId the unique ID of the videogame to find (inherited from Steam)
	 * @return the videogame with the specified app ID, or null if not found
	 */
	public Videogame findOneVideogameByAppId(Long appId)
	{
		List<Videogame> videogames=videogameService.getList();
		for(Videogame videogame : videogames)
		{
			if(videogame.getAppId().equals(appId))
				return videogame;
		}
		return null;
	}

	/**
	 * Clears all videogames associated with a specific user profile from the database.
	 *
	 * @param profile the user profile whose associated videogames will be removed
	 */
	public void clearVideogameDbByProfile(Profile profile)
	{
		List<Videogame> videogames=videogameService.getList();
		for(Videogame videogame : videogames)
		{
			if(videogame.getProfile().equals(profile))
				videogameService.deleteById(videogame.getId());
		}
	}
}
