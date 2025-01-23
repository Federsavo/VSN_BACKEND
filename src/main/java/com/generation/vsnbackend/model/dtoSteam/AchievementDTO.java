package com.generation.vsnbackend.model.dtoSteam;

public class AchievementDTO
{
	private String gameName;
	private String achievementName;
	private String description;
	private String iconUrl;

	public String getGameName()
	{
		return gameName;
	}

	public void setGameName(String gameName)
	{
		this.gameName = gameName;
	}

	public String getAchievementName()
	{
		return achievementName;
	}

	public void setAchievementName(String achievementName)
	{
		this.achievementName = achievementName;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getIconUrl()
	{
		return iconUrl;
	}

	public void setIconUrl(String iconUrl)
	{
		this.iconUrl = iconUrl;
	}
}
