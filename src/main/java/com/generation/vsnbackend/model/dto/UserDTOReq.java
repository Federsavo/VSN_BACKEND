package com.generation.vsnbackend.model.dto;

import java.time.LocalDate;

public class UserDTOReq
{
	private String username;
	private String password;
	private String steamId;
	private String email;
	private LocalDate dateOfBirth;
	private String SteamId;

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

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public LocalDate getDateOfBirth()
	{
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth)
	{
		this.dateOfBirth = dateOfBirth;
	}
}
