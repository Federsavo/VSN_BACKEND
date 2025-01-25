package com.generation.vsnbackend.model.dto;

import com.generation.vsnbackend.controller.exception.IllegalRegisterException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Data Transfer Object (DTO) for user registration requests.
 * This class encapsulates the information required for a user to register,
 * including username, password, Steam ID, email, and date of birth.
 */
public class UserDTOReq
{
	private String username;
	private String password;
	private String steamId;
	private String email;
	private LocalDate dateOfBirth;

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		if(username == null || username.isBlank())
			throw new IllegalRegisterException("Username cannot be empty");
		if(username.length() > 20)
			throw new IllegalRegisterException("Username must be at least 20 characters");
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		boolean passwordOk = false;
		if(password == null || password.isBlank())
			throw new IllegalRegisterException("Password cannot be empty");
		for(int i = 0; i < password.length(); i++)
			if(Character.isDigit(password.charAt(i)))
			{
				passwordOk=true;
				break;
			}
		if(!passwordOk)
			throw new IllegalRegisterException("Password must contain at least one number");
		this.password = password;
	}

	public String getSteamId()
	{
		if(steamId == null || steamId.isBlank())
			throw new IllegalRegisterException("SteamId cannot be empty");
		return steamId;
	}

	public void setSteamId(String steamId)
	{
		this.steamId = steamId;
	}

	public String getEmail()
	{
		if(email == null || email.isBlank())
			throw new IllegalRegisterException("Email cannot be empty");
		if(!email.contains("@"))
			throw new IllegalRegisterException("Email must contain @");
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public LocalDate getDateOfBirth()
	{
		if(dateOfBirth == null)
			throw new IllegalRegisterException("Date of Birth cannot be empty");
		if(dateOfBirth.isAfter(LocalDate.now()))
			throw new IllegalRegisterException("Date of Birth cannot be in the future");
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth)
	{
		this.dateOfBirth = dateOfBirth;
	}
}
