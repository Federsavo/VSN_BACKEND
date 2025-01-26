package com.generation.vsnbackend.model.dto;

import com.generation.vsnbackend.controller.helper.ControllerHelper;
import com.generation.vsnbackend.model.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DTOConverter
{
	@Autowired
	ControllerHelper ch;

	/**
	 * Converts a UserDTOReq object (DTO for user registration requests) to a User entity.
	 * This method takes a UserDTOReq object, which contains the user information
	 * required for registration or updating a user profile, and maps its fields
	 * to a new User entity.
	 *
	 * This method is typically used during user registration or when updating
	 * user information to persist the data in the database.
	 *
	 * @param userDTOReq the UserDTOReq object containing user information
	 * @return a User entity populated with the data from the UserDTOReq
	 */
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

	/**
	 * Converts a User entity to a UserDTOResp object.
	 * This method takes a User entity, which represents a user in the system,
	 * and maps its fields to a UserDTOResp object, which is intended for
	 * use in API responses.
	 *
	 * This transformation is commonly used when retrieving user information
	 * from the database to present it in a structured format suitable for
	 * client-side applications.
	 *
	 * @param user the User entity to be converted
	 * @return a UserDTOResp object populated with data from the User entity
	 */
	public UserDTOResp toDTOResp(User user)
	{
		UserDTOResp userDTOResp = new UserDTOResp();
		userDTOResp.setUsername(user.getUsername());
		userDTOResp.setPassword(user.getPassword());
		userDTOResp.setId(user.getId());
		userDTOResp.setSteamId(user.getSteamId());
		return userDTOResp;
	}

	/**
	 * Converts a UserDTOLoginReq object (DTO for user login requests) to a User entity.
	 * This method takes a UserDTOLoginReq object, which contains the username
	 * and password for user login, and maps its fields to a new User entity.
	 *
	 * This transformation is typically used during the login process to
	 * authenticate users based on the provided credentials.
	 *
	 * @param userDTOLoginReq the UserDTOLoginReq object containing login credentials
	 * @return a User entity populated with the username and password from the UserDTOLoginReq
	 */
	public User toLoginEntity(UserDTOLoginReq userDTOLoginReq)
	{
		User user = new User();
		user.setUsername(userDTOLoginReq.getUsername());
		user.setPassword(userDTOLoginReq.getPassword());
		return user;
	}

	/**
	 * Converts a Profile entity to a ProfileDTOResp object.
	 * This method maps the properties of a Profile entity to a ProfileDTOResp
	 * data transfer object, which is used to transfer profile information
	 * in a format suitable for API responses.
	 *
	 * @param profile the Profile entity to convert
	 * @return a ProfileDTOResp object containing the profile's data
	 */
	public ProfileDTOResp toProfileDtoResp (Profile profile){
		ProfileDTOResp profileDTOResp = new ProfileDTOResp();

		profileDTOResp.setId(profile.getId());
		profileDTOResp.setSteamId(profile.getUser().getSteamId());
		profileDTOResp.setFollowersCount(profile.getFollowersCount());
		profileDTOResp.setFollowingCount(profile.getFollowingCount());
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

	/**
	 * Converts a ProfileDTOReq object to a Profile entity.
	 * This method maps the properties of a ProfileDTOReq data transfer object
	 * to a Profile entity, which can then be saved in the database.
	 *
	 * @param profileDTOReq the ProfileDTOReq object containing the data to convert
	 * @return a Profile entity populated with the data from the ProfileDTOReq
	 */
	public Profile toProfileEntity (ProfileDTOReq profileDTOReq){
		Profile profile = new Profile();
		User u=ch.userService.getOneById(profileDTOReq.getUserId());
		profile.setUser(u);
		profile.setFollowersCount(profileDTOReq.getFollowersCount());
		profile.setFollowingCount(profileDTOReq.getFollowingCount());
		profile.setLastPlayedVideogameAppId(profileDTOReq.getLastPlayedVideogameAppId());

		profile.setProfileName(profileDTOReq.getProfileName());
		profile.setSteamName(profileDTOReq.getSteamName());
		profile.setXboxName(profileDTOReq.getXboxName());
		profile.setPlaystationName(profileDTOReq.getPlaystationName());
		return profile;
	}


	/**
	 * Converts a PostDTOReq object to a Post entity.
	 * This method maps the properties of a PostDTOReq data transfer object
	 * to a Post entity, which can then be saved in the database.
	 *
	 * @param postDTOReq the PostDTOReq object containing the data to convert
	 * @return a Post entity populated with the data from the PostDTOReq
	 */
	public Post toPostEntity(PostDTOReq postDTOReq){
		Post post = new Post();

//		Profile p=ch.profileService.getOneById(postDTOReq.getProfileId());

		post.setWhatIs(postDTOReq.getWhatIs());
		post.setPublicationDate(LocalDateTime.now());
		post.setContent(postDTOReq.getContent());
		post.setnLike(postDTOReq.getnLike());

		return post;
	}

	/**
	 * Converts a Post entity to a PostDTOResp object.
	 * This method maps the properties of a Post entity to a PostDTOResp
	 * data transfer object, which can be used to send data to the client.
	 *
	 * @param post the Post entity to convert
	 * @return a PostDTOResp object populated with data from the Post entity
	 */
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

	//DA QUI IN POI DOC DA FARE

	public FriendSummaryDTO toFriendSummaryDTO (Friend friend){
		FriendSummaryDTO friendSummaryDTO = new FriendSummaryDTO();

		friendSummaryDTO.setId(friend.getId());
		friendSummaryDTO.setSteamId(friend.getUser().getSteamId());
		friendSummaryDTO.setFollowersCount(friend.getUser().getProfile().getFollowersCount());
		friendSummaryDTO.setFollowingCount(friend.getUser().getProfile().getFollowingCount());
		friendSummaryDTO.setFavoriteVideogameAppId(friend.getUser().getProfile().getLastPlayedVideogameAppId());
		friendSummaryDTO.setProfileName(friend.getUser().getUsername());
		friendSummaryDTO.setProfileImgId(friend.getUser().getProfile().getProfileImgId());
		friendSummaryDTO.setProfileBackdropImgId(friend.getUser().getProfile().getProfileBackdropImgId());
		friendSummaryDTO.setLastPlayedGameImgUrl(friend.getUser().getProfile().getLastPlayedGameImgUrl());
		friendSummaryDTO.setLastPlayedGameName(friend.getUser().getProfile().getLastPlayedGameName());

		return friendSummaryDTO;

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
