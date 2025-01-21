package com.generation.vsnbackend.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Profile extends BaseEntity
{
	private int followersCount;
	private int followingCount;

	private Long favoriteVideogameAppId;
	private Long lastPlayedVideogameAppId;

	private String profileName;
	private String steamName;
	private String playstationName;
	private String xboxName;
	private String profileImageUrl;

	@OneToOne(mappedBy = "profile")
	private User user;

	@OneToMany(mappedBy = "profile", fetch= FetchType.EAGER)
	private List<Videogame> videogames=new ArrayList<>();

	@OneToMany(mappedBy = "profile", fetch= FetchType.EAGER)
	private List<Post> posts=new ArrayList<>();

	public int getFollowersCount()
	{
		return followersCount;
	}

	public void setFollowersCount(int followersCount)
	{
		this.followersCount = followersCount;
	}

	public int getFollowingCount()
	{
		return followingCount;
	}

	public void setFollowingCount(int followingCount)
	{
		this.followingCount = followingCount;
	}

	public Long getFavoriteVideogameAppId()
	{
		return favoriteVideogameAppId;
	}

	public void setFavoriteVideogameAppId(Long favoriteVideogameAppId)
	{
		this.favoriteVideogameAppId = favoriteVideogameAppId;
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

	public String getProfileImageUrl()
	{
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl)
	{
		this.profileImageUrl = profileImageUrl;
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
}
