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
