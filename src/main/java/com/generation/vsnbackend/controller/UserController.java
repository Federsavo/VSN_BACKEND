package com.generation.vsnbackend.controller;

import com.generation.vsnbackend.controller.helper.ControllerHelper;
import com.generation.vsnbackend.model.dto.UserDTOLoginReq;
import com.generation.vsnbackend.model.dto.UserDTOReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
public class UserController
{

	@Autowired
	CredentialService credentialService;

	@PostMapping("/register")
	String registerUser(@RequestBody UserDTOReq userDTOReq)
	{
		return credentialService.registration(userDTOReq);
	}

	@PostMapping("/login")
	String loginUser(@RequestBody UserDTOLoginReq userDTOlLoginReq)
	{
		return credentialService.login(userDTOlLoginReq);
	}

}
