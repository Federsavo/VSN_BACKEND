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
	public final GenericService<Friend, Long> friendService;


	@Autowired
	public ControllerHelper(UserRepository userRepo, CommentRepository commentRepo, PostRepository postRepo, ReviewRepository reviewRepo, VideogameRepository videogameRepo, ProfileRepository profileRepo, FriendRepository friendRepo)
	{
		this.userService = new GenericService<>(userRepo);
		this.commentService=new GenericService<>(commentRepo);
		this.postService=new GenericService<>(postRepo);
		this.reviewService=new GenericService<>(reviewRepo);
		this.videogameService=new GenericService<>(videogameRepo);
		this.profileService=new GenericService<>(profileRepo);
		this.friendService=new GenericService<>(friendRepo);
	}


	/**
	 *This method finds and returns a videogame by its app ID in db
	 * @param appId the unique ID of the videogame to find (inherited from Steam)
	 * @param profile the profile who owns the videogame
	 * @return the videogame with the specified app ID, or null if not found
	 */
	public Videogame findOneVideogameByAppId(Long appId,Profile profile)
	{
		List<Videogame> videogames=videogameService.getList();
		for(Videogame videogame : videogames)
		{
			if(videogame.getAppId().equals(appId)&&videogame.getProfile().equals(profile))
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

	/**
	 * Retrieves a Friend entity based on the specified following and follower IDs.
	 *
	 * This method searches for a friendship relationship where the specified user is following
	 * another user. It iterates through the list of all Friend entities and checks if the
	 * follower and following IDs match the given parameters. If a match is found, the corresponding
	 * Friend object is returned; otherwise, null is returned.
	 *
	 * @param followingId The ID of the user being followed.
	 * @param followerId The ID of the user who is following.
	 * @return The Friend object representing the friendship relationship, or null if no such
	 *         relationship exists.
	 */
	public Friend getOneFriendByFollowingIdAndFollowerId(Long followingId,Long followerId)
	{
		List<Friend> friends=friendService.getList();
		for(Friend friend : friends)
		{
			if(friend.getProfile_following().getId().equals(followingId))
				return friend;
		}
		return null;
	}

	/**
	 * Calculates the average number of stars for all videogames with a given appId.
	 *
	 * This method filters the list of videogames to select only those that have an `appId`
	 * matching the provided parameter. Then, it extracts the number of stars for each videogame
	 * and computes the average of these values. If no videogames are found with the specified
	 * `appId`, the method returns 0.
	 *
	 * @param appId the application ID for which the average number of stars should be calculated.
	 * @return the average number of stars for the videogames with the given appId, or 0 if none are found.
	 */
	public int getAverageNumberOfStars(Long appId)
	{
		List<Videogame> videogames=videogameService.getList();
		return (int) videogames.stream().filter(v-> v.getAppId().equals(appId))
				.mapToInt(Videogame::getNumberOfStars)
				.average()
				.orElse(0);
	}

}
