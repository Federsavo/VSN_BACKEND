package com.generation.vsnbackend.model.dto;

/**
 * Data Transfer Object (DTO) for user login requests.
 * This class is used to encapsulate the login credentials
 * (username and password) provided by the user during the login process.
 */
public class UserDTOLoginReq
{
	private String username;
	private String password;

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
}
