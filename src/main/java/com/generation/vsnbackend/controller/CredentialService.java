package com.generation.vsnbackend.controller;

import com.generation.vsnbackend.controller.exception.InvalidPasswordException;
import com.generation.vsnbackend.controller.exception.InvalidUsernameException;
import com.generation.vsnbackend.controller.helper.ControllerHelper;
import com.generation.vsnbackend.model.dto.UserDTOLoginReq;
import com.generation.vsnbackend.model.dto.UserDTOReq;
import com.generation.vsnbackend.model.entities.Profile;
import com.generation.vsnbackend.model.entities.User;
import com.generation.vsnbackend.model.repositories.UserRepository;
import com.generation.vsnbackend.model.entities.signin.Response;
import com.generation.vsnbackend.model.entities.signin.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
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

	public Response registration(UserDTOReq userDTOReq)
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
		Profile profile = new Profile();
		user.setProfile(profile);
		profile.setUser(user);

		ch.userService.save(user);
		ch.profileService.save(profile);

        return new Response("Successfully");
	}

	public Token login(UserDTOLoginReq userDTOLoginReq) {

		if(userDTOLoginReq.getUsername().isBlank() || userDTOLoginReq.getPassword().isBlank())
			throw new InvalidUsernameException("Invalid username or password");

		Optional<User> User = userRepo.findByUsername(userDTOLoginReq.getUsername());

		if(User.isEmpty())
			throw new InvalidUsernameException("Invalid username or password");

		User user = User.get();
		String hashedPassword = DigestUtils.md5DigestAsHex(userDTOLoginReq.getPassword().getBytes());

		if(!user.getPassword().equals(hashedPassword))
			throw new InvalidPasswordException("Invalid username or password");

		return new Token(user.getId());
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
