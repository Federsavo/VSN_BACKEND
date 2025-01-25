package com.generation.vsnbackend.controller;

import com.generation.vsnbackend.controller.helper.ControllerHelper;
import com.generation.vsnbackend.model.dto.UserDTOLoginReq;
import com.generation.vsnbackend.model.dto.UserDTOReq;
import com.generation.vsnbackend.model.entities.signin.Response;
import com.generation.vsnbackend.model.entities.signin.Token;
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

    /**
     * Registers a new user in the system.
     * This method accepts a UserDTOReq object containing the user's registration details,
     * such as username, password, and other relevant information.
     * It then calls the credentialService to handle the registration process.
     *
     * This operation does not require a token, as it is intended for new user registration.
     * The user's profile will be created upon successful registration.
     *
     * @param userDTOReq the UserDTOReq object containing the registration details
     * @return a Response object indicating the success or failure of the registration operation
     */
    @PostMapping("/authentication/register")
	Response registerUser(@RequestBody UserDTOReq userDTOReq)
    {
        return credentialService.registration(userDTOReq);
    }

    /**
     * Authenticates a user and generates a token for session management.
     * This method accepts a UserDTOLoginReq object containing the user's login credentials,
     * such as username and password. It then calls the credentialService to handle the login process.
     *
     * Upon successful authentication, a token is returned, which can be used for subsequent
     * requests to access protected resources.
     *
     * This operation requires the user to provide valid login credentials but does not
     * require a previously obtained token since it is the initial login action.
     *
     * @param userDTOlLoginReq the UserDTOLoginReq object containing the login credentials
     * @return a Token object representing the user's session token
     */
    @PostMapping("/authentication/login")
    Token loginUser(@RequestBody UserDTOLoginReq userDTOlLoginReq)
    {
        return credentialService.login(userDTOlLoginReq);
    }

}
