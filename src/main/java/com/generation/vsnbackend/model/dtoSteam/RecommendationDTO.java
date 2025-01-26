package com.generation.vsnbackend.model.dtoSteam;

public class RecommendationDTO
{
	private Long appId;
	private String videogameName;
	private String iconImgUrl;
	private String developers;
	private String publishers;

	public Long getAppId()
	{
		return appId;
	}

	public void setAppId(Long appId)
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

	public String getDevelopers()
	{
		return developers;
	}

	public void setDevelopers(String developers)
	{
		this.developers = developers;
	}

	public String getPublishers()
	{
		return publishers;
	}

	public void setPublishers(String publishers)
	{
		this.publishers = publishers;
	}
}
