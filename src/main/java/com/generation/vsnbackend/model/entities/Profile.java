package com.generation.vsnbackend.model.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Profile extends BaseEntity
{

	private Long lastPlayedVideogameAppId;

	private String profileName;
	private String steamName;
	private String playstationName;
	private String xboxName;
	private Long profileImgId;
	private Long profileBackdropImgId;
	private String lastPlayedGameImgUrl;
	private String LastPlayedGameName;

	@OneToOne(mappedBy = "profile" )
	private User user;

	@OneToMany(mappedBy = "profile", fetch= FetchType.EAGER)
	private List<Videogame> videogames = new ArrayList<>();

	@OneToMany(mappedBy = "profile", fetch= FetchType.EAGER)
	private List<Post> posts = new ArrayList<>();

	//sono i following
	@OneToMany(mappedBy="profile_following", cascade= CascadeType.ALL,  fetch= FetchType.EAGER)
	private List<Friend> followings = new ArrayList<>();

	//followers
	@OneToMany(mappedBy = "profile_follower", cascade= CascadeType.ALL, fetch= FetchType.EAGER)
	private List<Friend> followers = new ArrayList<>();

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public String getProfileName()
	{
		return profileName;
	}

	public void setProfileName(String profileName)
	{
		this.profileName = profileName;
	}

	public Long getLastPlayedVideogameAppId()
	{
		return lastPlayedVideogameAppId;
	}

	public void setLastPlayedVideogameAppId(Long lastPlayedVideogameAppId)
	{
		this.lastPlayedVideogameAppId = lastPlayedVideogameAppId;
	}

	public String getSteamName()
	{
		return steamName;
	}

	public void setSteamName(String steamName)
	{
		this.steamName = steamName;
	}

	public String getPlaystationName()
	{
		return playstationName;
	}

	public void setPlaystationName(String playstationName)
	{
		this.playstationName = playstationName;
	}

	public String getXboxName()
	{
		return xboxName;
	}

	public void setXboxName(String xboxName)
	{
		this.xboxName = xboxName;
	}

	public List<Videogame> getVideogames()
	{
		return videogames;
	}

	public void setVideogames(List<Videogame> videogames)
	{
		this.videogames = videogames;
	}

	public List<Post> getPosts()
	{
		return posts;
	}

	public void setPosts(List<Post> posts)
	{
		this.posts = posts;
	}

	public Long getProfileImgId()
	{
		return profileImgId;
	}

	public void setProfileImgId(Long profileImgId)
	{
		this.profileImgId = profileImgId;
	}

	public Long getProfileBackdropImgId()
	{
		return profileBackdropImgId;
	}

	public void setProfileBackdropImgId(Long profileBackdropImgId)
	{
		this.profileBackdropImgId = profileBackdropImgId;
	}

	public String getLastPlayedGameImgUrl() {
		return lastPlayedGameImgUrl;
	}

	public void setLastPlayedGameImgUrl(String lastPlayedGameImgUrl) {
		this.lastPlayedGameImgUrl = lastPlayedGameImgUrl;
	}

	public String getLastPlayedGameName()
	{
		return LastPlayedGameName;
	}

	public void setLastPlayedGameName(String lastPlayedGameName)
	{
		LastPlayedGameName = lastPlayedGameName;
	}

	public List<Friend> getFollowings() {
		return followings;
	}

	public void setFollowings(List<Friend> followings) {
		this.followings = followings;
	}

	public void addFollowing (Friend friend){
		this.followings.add(friend);
	}

	public List<Friend> getFollowers() {
		return followers;
	}

	public void setFollowers(List<Friend> followers) {
		this.followers = followers;
	}

	public void addFollower(Friend follower) {
		this.followers.add(follower);
	}
}
