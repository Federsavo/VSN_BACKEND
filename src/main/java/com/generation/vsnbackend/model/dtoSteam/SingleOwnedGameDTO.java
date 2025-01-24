package com.generation.vsnbackend.model.dtoSteam;

public class SingleOwnedGameDTO
{
	private String appId;
	private String videogameName;
	private String iconImgUrl;
	private String softwareHouse;
	private int numberOfStars; //1 a 10

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

	public String getSoftwareHouse()
	{
		return softwareHouse;
	}

	public void setSoftwareHouse(String softwareHouse)
	{
		this.softwareHouse = softwareHouse;
	}

	public int getNumberOfStars()
	{
		return numberOfStars;
	}

	public void setNumberOfStars(int numberOfStars)
	{
		this.numberOfStars = numberOfStars;
	}
}
