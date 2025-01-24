package com.generation.vsnbackend.model.dtoSteam;

public class SingleOwnedGameDTO
{
	private String appId;
	private String videogameName;
	private String iconImgUrl;

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
}
