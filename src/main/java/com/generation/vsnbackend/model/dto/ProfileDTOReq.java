package com.generation.vsnbackend.model.dto;

public class ProfileDTOReq
{
	private Long userId;

	private int followersCount;
	private int followingCount;

	private Long lastPlayedVideogameAppId;

	private String profileName;
	private String steamName;
	private String playstationName;
	private String xboxName;

	private Long profileImgId;
	private Long profileBackdropImgId;


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

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

	public String getProfileName()
	{
		return profileName;
	}

	public void setProfileName(String profileName)
	{
		this.profileName = profileName;
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
}
