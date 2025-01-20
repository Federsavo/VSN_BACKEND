package com.generation.vsnbackend.controller.helper;

import com.generation.vsnbackend.controller.exception.IllegalRegisterException;
import com.generation.vsnbackend.controller.exception.InvalidPasswordException;
import com.generation.vsnbackend.controller.exception.InvalidUsernameException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UniversalExceptionHandler
{
	@ExceptionHandler(InvalidPasswordException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String handleWrongPassword(InvalidPasswordException ex)
	{
		return ex.getMessage();
	}

	@ExceptionHandler(InvalidUsernameException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleWrongUsername(InvalidUsernameException ex)
	{
		return ex.getMessage();
	}

	@ExceptionHandler(IllegalRegisterException.class)
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	public String handleWrongRegister(IllegalRegisterException ex) { return ex.getMessage(); }

}
