package com.generation.vsnbackend.controller;

import com.generation.vsnbackend.controller.helper.ControllerHelper;
import com.generation.vsnbackend.model.dto.UserDTOLoginReq;
import com.generation.vsnbackend.model.dto.UserDTOReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController
{

	@Autowired
	ControllerHelper ch;

	@Autowired
	CredentialService credentialService;

	@PostMapping("/authentication/register")
	String registerUser(@RequestBody UserDTOReq userDTOReq)
	{
		return credentialService.registration(userDTOReq);
	}

	@PostMapping("/authentication/login")
	String loginUser(@RequestBody UserDTOLoginReq userDTOlLoginReq)
	{
		return credentialService.login(userDTOlLoginReq);
	}

}
