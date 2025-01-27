package com.generation.vsnbackend.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends BaseEntity
{
	private String username;
	private String password;
	private String steamId;
	private String email;
	private LocalDate dateOfBirth;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "profile_id")
	private Profile profile;

//	@OneToOne
//	@JoinColumn(name = "friend_id")
//	private Friend friend;
//
//	public Friend getFriend() {
//		return friend;
//	}
//
//	public void setFriend(Friend friend) {
//		this.friend = friend;
//	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
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
