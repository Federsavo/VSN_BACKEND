package com.generation.vsnbackend.model.dto;

import com.generation.vsnbackend.controller.helper.ControllerHelper;
import com.generation.vsnbackend.model.dtoSteam.VideogameDTOReq;
import com.generation.vsnbackend.model.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DTOConverter
{
	@Autowired
	ControllerHelper ch;

	public User toUserEntity(UserDTOReq userDTOReq)
	{
		User user = new User();
		user.setUsername(userDTOReq.getUsername());
		user.setPassword(userDTOReq.getPassword());
		user.setEmail(userDTOReq.getEmail());
		user.setDateOfBirth(userDTOReq.getDateOfBirth());
		user.setSteamId(userDTOReq.getSteamId());
		return user;
	}

	public UserDTOResp toDTOResp(User user)
	{
		UserDTOResp userDTOResp = new UserDTOResp();
		userDTOResp.setUsername(user.getUsername());
		userDTOResp.setPassword(user.getPassword());
		userDTOResp.setId(user.getId());
		userDTOResp.setSteamId(user.getSteamId());
		return userDTOResp;
	}

	public User toLoginEntity(UserDTOLoginReq userDTOLoginReq)
	{
		User user = new User();
		user.setUsername(userDTOLoginReq.getUsername());
		user.setPassword(userDTOLoginReq.getPassword());
		return user;
	}

	public ProfileDTOResp toProfileDtoResp (Profile profile){
		ProfileDTOResp profileDTOResp = new ProfileDTOResp();

		profileDTOResp.setId(profile.getId());
		profileDTOResp.setSteamId(profile.getUser().getSteamId());
		profileDTOResp.setFollowersCount(profile.getFollowersCount());
		profileDTOResp.setFollowingCount(profile.getFollowingCount());
		profileDTOResp.setFavoriteVideogameAppId(profile.getFavoriteVideogameAppId());
		profileDTOResp.setLastPlayedVideogameAppId(profile.getLastPlayedVideogameAppId());
		profileDTOResp.setProfileName(profile.getUser().getUsername());
		profileDTOResp.setSteamName(profile.getSteamName());
		profileDTOResp.setPlaystationName(profile.getPlaystationName());
		profileDTOResp.setXboxName(profile.getXboxName());
		profileDTOResp.setProfileImgId(profile.getProfileImgId());
		profileDTOResp.setProfileBackdropImgId(profile.getProfileBackdropImgId());
		profileDTOResp.setLastPlayedGameImgUrl(profile.getLastPlayedGameImgUrl());
		profileDTOResp.setLastPlayedGameName(profile.getLastPlayedGameName());
		return profileDTOResp;

	}

	public Profile toProfileEntity (ProfileDTOReq profileDTOReq){
		Profile profile = new Profile();
		User u=ch.userService.getOneById(profileDTOReq.getUserId());
		profile.setUser(u);
		profile.setFollowersCount(profileDTOReq.getFollowersCount());
		profile.setFollowingCount(profileDTOReq.getFollowingCount());
		profile.setFavoriteVideogameAppId(profileDTOReq.getFavoriteVideogameAppId());
		profile.setLastPlayedVideogameAppId(profileDTOReq.getLastPlayedVideogameAppId());

		profile.setProfileName(profileDTOReq.getProfileName());
		profile.setSteamName(profileDTOReq.getSteamName());
		profile.setXboxName(profileDTOReq.getXboxName());
		profile.setPlaystationName(profileDTOReq.getPlaystationName());
		return profile;
	}

	public Post toPostEntity(PostDTOReq postDTOReq){
		Post post = new Post();

//		Profile p=ch.profileService.getOneById(postDTOReq.getProfileId());

		post.setWhatIs(postDTOReq.getWhatIs());
		post.setPublicationDate(LocalDateTime.now());
		post.setContent(postDTOReq.getContent());
		post.setnLike(postDTOReq.getnLike());

		return post;
	}

	public PostDTOResp toPostDTOResp(Post post){
		PostDTOResp postDTOResp = new PostDTOResp();

		postDTOResp.setId(post.getId());
		postDTOResp.setWhatIs(post.getWhatIs());
		postDTOResp.setPublicationDate(String.valueOf(post.getPublicationDate()));
		postDTOResp.setContent(post.getContent());
		postDTOResp.setnLike(post.getnLike());
		postDTOResp.setImage(post.getImage());
		postDTOResp.setProfileId(post.getProfile().getId());

		return postDTOResp;
	}

	public CommentDTOResp toCommentDTOResp(Comment comment){
		CommentDTOResp commentDTOResp = new CommentDTOResp();

		commentDTOResp.setId(comment.getId());
		commentDTOResp.setAuthor(comment.getAuthor());
		commentDTOResp.setTheComment(comment.getTheComment());
		commentDTOResp.setnLike(comment.getnLike());
		commentDTOResp.setPubblicationDate(String.valueOf(comment.getPubblicationDate()));
		commentDTOResp.setPostId(comment.getPost().getId());

		return commentDTOResp;

	}

	public Comment toCommentEntity(CommentDTOReq commentDTOReq){
		Comment comment = new Comment();

		comment.setTheComment(commentDTOReq.getTheComment());
		comment.setnLike(commentDTOReq.getnLike());
		comment.setAuthor(commentDTOReq.getAuthor());
		comment.setPubblicationDate(LocalDateTime.now());

		return comment;

	}



	public ReviewDTOResp toReviewDTOResp(Review review){
		ReviewDTOResp reviewDTOResp = new ReviewDTOResp();

		reviewDTOResp.setId(review.getId());
		reviewDTOResp.setAuthor(review.getAuthor());
		reviewDTOResp.setContent(review.getContent());
		reviewDTOResp.setTitle(review.getTitle());
		reviewDTOResp.setNumberOfStar(review.getNumberOfStar());

		return reviewDTOResp;
	}

	public Review toReviewEntity(ReviewDTOReq reviewDTOReq){
		Review review = new Review();

		review.setAuthor(reviewDTOReq.getAuthor());
		review.setTitle(reviewDTOReq.getTitle());
		review.setNumberOfStar(reviewDTOReq.getNumberOfStar());
		review.setContent(reviewDTOReq.getContent());

		return review;
	}


}
