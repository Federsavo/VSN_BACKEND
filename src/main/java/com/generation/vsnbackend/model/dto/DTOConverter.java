package com.generation.vsnbackend.model.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.generation.vsnbackend.controller.helper.ControllerHelper;
import com.generation.vsnbackend.model.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
		post.setPubblicationDate(LocalDateTime.now());
		post.setContent(postDTOReq.getContent());
		post.setnLike(postDTOReq.getnLike());

		return post;
	}

	public PostDTOResp toPostDTOResp(Post post){
		PostDTOResp postDTOResp = new PostDTOResp();

		postDTOResp.setId(post.getId());
		postDTOResp.setWhatIs(post.getWhatIs());
		postDTOResp.setPubblicationDate(String.valueOf(post.getPubblicationDate()));
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

	public VideogameDTOResp toVideogameDTOResp(Videogame videogame){
		VideogameDTOResp videogameDTOResp = new VideogameDTOResp();

		videogameDTOResp.setId(videogame.getId());
		videogameDTOResp.setNameVideogame(videogame.getNameVideogame());
		videogameDTOResp.setDescription(videogame.getDescription());
		videogameDTOResp.setSoftwareHouse(videogame.getSoftwareHouse());
		videogameDTOResp.isPreferred(videogame.isPreferred());
		videogameDTOResp.setReleaseDate(String.valueOf(videogame.getReleaseDate()));
		videogameDTOResp.setStarReviews(videogame.getStarReviews());

		return videogameDTOResp;

	}

	public Videogame toVideogameEntity(VideogameDTOReq videogameDTOReq){
		Videogame videogame = new Videogame();

		videogame.setNameVideogame(videogameDTOReq.getNameVideogame());
		videogame.setDescription(videogameDTOReq.getDescription());
		videogame.setSoftwareHouse(videogameDTOReq.getSoftwareHouse());
		videogame.setPreferred(videogameDTOReq.isPreferred());
		videogame.setReleaseDate(LocalDate.parse(videogameDTOReq.getReleaseDate()));

		return videogame;
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

	public PlayerDTO toPlayerDTO(String json) throws JsonProcessingException {
		PlayerDTO playerDTO = new PlayerDTO();

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(json);

		// Naviga nel JSON fino alla lista dei giocatori
		JsonNode playerNode = rootNode.path("response").path("players").get(0); // Ottieni il primo giocatore

		playerDTO.setPersonaName(playerNode.path("personaname").asText());
		playerDTO.setProfileUrl(playerNode.path("profileurl").asText());
		playerDTO.setAvatar(playerNode.path("avatar").asText());
		playerDTO.setAvatarMedium(playerNode.path("avatarmedium").asText());
		playerDTO.setAvatarFull(playerNode.path("avatarfull").asText());
		playerDTO.setRealName(playerNode.path("realname").asText());

		int secondi=playerNode.path("lastlogoff").asInt();
		LocalDate lastLogOff = Instant.ofEpochSecond(secondi)
				.atZone(ZoneId.systemDefault()) // Use the system's default timezone
				.toLocalDate();
		playerDTO.setLastLogOff(lastLogOff);

		secondi=playerNode.path("timecreated").asInt();
		LocalDate timeCreated = Instant.ofEpochSecond(secondi)
				.atZone(ZoneId.systemDefault()) // Use the system's default timezone
				.toLocalDate();

		playerDTO.setTimeCreated(timeCreated);

		playerDTO.setLocCountryCode(playerNode.path("loccountrycode").asText());
		playerDTO.setPrimaryClanId(playerNode.path("primaryclanid").asText());


		return playerDTO;
	}
}
