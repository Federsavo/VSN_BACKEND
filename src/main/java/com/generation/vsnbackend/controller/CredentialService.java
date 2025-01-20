package com.generation.vsnbackend.controller;

import com.generation.vsnbackend.controller.exception.InvalidPasswordException;
import com.generation.vsnbackend.controller.exception.InvalidUsernameException;
import com.generation.vsnbackend.controller.helper.ControllerHelper;
import com.generation.vsnbackend.model.dto.UserDTOLoginReq;
import com.generation.vsnbackend.model.dto.UserDTOReq;
import com.generation.vsnbackend.model.entities.User;
import com.generation.vsnbackend.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Service
public class CredentialService
{
	@Autowired
	ControllerHelper ch;

	@Autowired
	UserRepository userRepo;

	public String registration(UserDTOReq userDTOReq)
	{
		User user = new User();
		if (userRepo.findByUsername(userDTOReq.getUsername()).isPresent())
			throw new InvalidUsernameException("Username already exists");

		user.setUsername(userDTOReq.getUsername());
		user.setSteamId(userDTOReq.getSteamId());
		user.setDateOfBirth(userDTOReq.getDateOfBirth());
		user.setEmail(userDTOReq.getEmail());
		String hashedPassword = DigestUtils.md5DigestAsHex(userDTOReq.getPassword().getBytes());
		user.setPassword(hashedPassword);
		ch.userService.save(user);
		return "Registered successfully";
	}

	public String login(UserDTOLoginReq userDTOLoginReq) {

		Optional<User> User = userRepo.findByUsername(userDTOLoginReq.getUsername());

		if(User.isEmpty())
			throw new InvalidUsernameException("Invalid username");

		User user = User.get();
		String hashedPassword = DigestUtils.md5DigestAsHex(userDTOLoginReq.getPassword().getBytes());

		if(!user.getPassword().equals(hashedPassword))
			throw new InvalidPasswordException("Invalid password");

		String prefix = "";
		String suffix = "";

		for(int i=0;i < 10;i++)
		{
			prefix += Math.round(Math.random()*10);
			suffix += Math.round(Math.random()*10);
		}

		return prefix + "-" + user.getId() + "-" + suffix;
	}

	public User getUserByToken()
	{
		ServletRequestAttributes req = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		String token = req.getRequest().getHeader("token");
		if(token == null)
			throw new InvalidUsernameException("Token non valido");

		long id = Long.parseLong(token.split("-")[1]);
		Optional<User> u = userRepo.findById(id);
		if(u.isEmpty())
			throw new InvalidUsernameException("Token non valido");

		return u.get();
	}
}
