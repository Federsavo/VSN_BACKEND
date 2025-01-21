package com.generation.vsnbackend.model.dto;

import com.generation.vsnbackend.model.entities.Profile;
import com.generation.vsnbackend.model.entities.User;
import org.springframework.stereotype.Service;

@Service
public class DTOConverter
{
	public User toUserEntity(UserDTOReq userDTOReq)
	{
		User user = new User();
		user.setUsername(userDTOReq.getUsername());
		user.setPassword(userDTOReq.getPassword());
		user.setEmail(userDTOReq.getEmail());
		user.setDateOfBirth(userDTOReq.getDateOfBirth());
		user.setSteamId(userDTOReq.getSteamId());
		return user;
	}

	public UserDTOResp toDTOResp(User user)
	{
		UserDTOResp userDTOResp = new UserDTOResp();
		userDTOResp.setUsername(user.getUsername());
		userDTOResp.setPassword(user.getPassword());
		userDTOResp.setId(user.getId());
		userDTOResp.setSteamId(user.getSteamId());
		return userDTOResp;
	}

	public User toLoginEntity(UserDTOLoginReq userDTOLoginReq)
	{
		User user = new User();
		user.setUsername(userDTOLoginReq.getUsername());
		user.setPassword(userDTOLoginReq.getPassword());
		return user;
	}
}
