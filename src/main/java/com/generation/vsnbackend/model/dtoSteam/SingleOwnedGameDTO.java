package com.generation.vsnbackend.model.dtoSteam;

public class SingleOwnedGameDTO
{
	private String appId;
	private String videogameName;
	private String iconImgUrl;
	private int numberOfStars;//da 1 a 10
	private boolean preferred;

	public String getAppId()
	{
		return appId;
	}

	public void setAppId(String appId)
	{
		this.appId = appId;
	}

	public String getVideogameName()
	{
		return videogameName;
	}

	public void setVideogameName(String videogameName)
	{
		this.videogameName = videogameName;
	}

	public String getIconImgUrl()
	{
		return iconImgUrl;
	}

	public void setIconImgUrl(String iconImgUrl)
	{
		this.iconImgUrl = iconImgUrl;
	}

	public int getNumberOfStars()
	{
		return numberOfStars;
	}

	public void setNumberOfStars(int numberOfStars)
	{
		this.numberOfStars = numberOfStars;
	}

	public boolean isPreferred()
	{
		return preferred;
	}

	public void setPreferred(boolean preferred)
	{
		this.preferred = preferred;
	}
}
