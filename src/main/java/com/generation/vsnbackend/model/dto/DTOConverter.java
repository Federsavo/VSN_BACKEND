package com.generation.vsnbackend.model.dto;

import com.generation.vsnbackend.controller.helper.ControllerHelper;
import com.generation.vsnbackend.model.entities.Post;
import com.generation.vsnbackend.model.entities.Profile;
import com.generation.vsnbackend.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		profileDTOResp.setProfileName(profile.getProfileName());
		profileDTOResp.setSteamName(profile.getSteamName());
		profileDTOResp.setPlaystationName(profile.getPlaystationName());
		profileDTOResp.setXboxName(profile.getXboxName());
		profileDTOResp.setProfileImageUrl(profile.getProfileImageUrl());

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
		profile.setProfileImageUrl(profileDTOReq.getProfileImageUrl());
		return profile;
	}

	public Post toPostEntity(PostDTOReq postDTOReq){
		Post post = new Post();
		Profile p=ch.profileService.getOneById(postDTOReq.getProfileId());

		post.setWhatIs(postDTOReq.getWhatIs());
		post.setPubblicationDate(LocalDateTime.now());
		post.setContent(postDTOReq.getContent());
		post.setnLike(postDTOReq.getnLike());
		post.setnLike(postDTOReq.getnLike());

		post.setProfile(p);
		return post;
	}

	public PostDTOResp toPostDTOResp(Post post){
		PostDTOResp postDTOResp = new PostDTOResp();

		postDTOResp.setId(post.getId());
		postDTOResp.setWhatIs(post.getWhatIs());
		postDTOResp.setPubblicationDate(postDTOResp.getPubblicationDate());
		postDTOResp.setContent(post.getContent());
		postDTOResp.setnLike(post.getnLike());
		postDTOResp.setImage(post.getImage());
		postDTOResp.setProfileId(post.getProfile().getId());

		return postDTOResp;
	}
}
