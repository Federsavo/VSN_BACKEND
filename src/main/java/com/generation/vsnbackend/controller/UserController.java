package com.generation.vsnbackend.controller;

import com.generation.vsnbackend.controller.helper.ControllerHelper;
import com.generation.vsnbackend.model.dto.DTOConverter;
import com.generation.vsnbackend.model.dto.UserDTOResp;
import com.generation.vsnbackend.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController
{

	@Autowired
	ControllerHelper ch;

	@Autowired
	DTOConverter dtoConverter;

	/**
	 * Retrieves a list of all registered users in the system.
	 * This method fetches all user entities from the user service and
	 * converts each User object to a UserDTOResp object for the response.
	 *
	 * This operation does not require a token, as it is intended to retrieve
	 * information about users in the system and does not modify any user data.
	 *
	 * @return a list of UserDTOResp objects representing all registered users
	 */
	@GetMapping("/users")
	List<UserDTOResp> getListUsers()
	{
		List<UserDTOResp> res=new ArrayList<>();
		for (User u : ch.userService.getList())
		{
			res.add(dtoConverter.toDTOResp(u));
		}
		return res;
	}

}
