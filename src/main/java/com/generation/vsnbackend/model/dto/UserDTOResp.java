package com.generation.vsnbackend.model.dto;

public class UserDTOResp
{
	private Long id;
	private String username;
	private String password;
	private String steamId;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getSteamId()
	{
		return steamId;
	}

	public void setSteamId(String steamId)
	{
		this.steamId = steamId;
	}
}
