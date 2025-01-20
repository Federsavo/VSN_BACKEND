package com.generation.vsnbackend.controller.helper;


import com.generation.vsnbackend.model.entities.User;
import com.generation.vsnbackend.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ControllerHelper
{
	public final GenericService<User, Long> userService;


	@Autowired
	public ControllerHelper(UserRepository userRepo)
	{
		this.userService = new GenericService<>(userRepo);
	}
}
