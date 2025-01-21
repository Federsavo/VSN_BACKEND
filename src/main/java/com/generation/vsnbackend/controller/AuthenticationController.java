package com.generation.vsnbackend.controller;

import com.generation.vsnbackend.controller.helper.ControllerHelper;
import com.generation.vsnbackend.model.dto.UserDTOLoginReq;
import com.generation.vsnbackend.model.dto.UserDTOReq;
import com.generation.vsnbackend.model.signin.SignIn;
import com.generation.vsnbackend.model.signin.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthenticationController
{

	@Autowired
    ControllerHelper ch;

	@Autowired
	CredentialService credentialService;

    @PostMapping("/authentication/register")
    SignIn registerUser(@RequestBody UserDTOReq userDTOReq)
    {
        return credentialService.registration(userDTOReq);
    }

    @PostMapping("/authentication/login")
    Token loginUser(@RequestBody UserDTOLoginReq userDTOlLoginReq)
    {
        return credentialService.login(userDTOlLoginReq);
    }

}
