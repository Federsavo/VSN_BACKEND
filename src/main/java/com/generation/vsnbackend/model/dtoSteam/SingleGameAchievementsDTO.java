package com.generation.vsnbackend.model.dtoSteam;

import java.util.List;

public class SingleGameAchievementsDTO
{
	private String appId;
	private String gameName;
	private List<String> achievementsNames;

	public String getAppId()
	{
		return appId;
	}

	public void setAppId(String appId)
	{
		this.appId = appId;
	}

	public List<String> getAchievementsNames()
	{
		return achievementsNames;
	}

	public void setAchievementsNames(List<String> achievementsNames)
	{
		this.achievementsNames = achievementsNames;
	}

	public String getGameName()
	{
		return gameName;
	}

	public void setGameName(String gameName)
	{
		this.gameName = gameName;
	}
}
