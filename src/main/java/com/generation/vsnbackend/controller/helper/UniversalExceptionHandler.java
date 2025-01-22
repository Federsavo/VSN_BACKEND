package com.generation.vsnbackend.controller.helper;

import com.generation.vsnbackend.controller.exception.IllegalRegisterException;
import com.generation.vsnbackend.controller.exception.InvalidPasswordException;
import com.generation.vsnbackend.controller.exception.InvalidUsernameException;
import com.generation.vsnbackend.model.entities.errors.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UniversalExceptionHandler
{
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<ErrorResponse> handleWrongPassword(InvalidPasswordException ex)
	{
		ErrorResponse error = new ErrorResponse(
				HttpStatus.FORBIDDEN.value(),
				ex.getMessage(),
				System.currentTimeMillis()
		);

		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(InvalidUsernameException.class)
	public ResponseEntity<ErrorResponse> handleWrongUsername(InvalidUsernameException ex)
	{
		ErrorResponse error = new ErrorResponse(
				HttpStatus.FORBIDDEN.value(),
				ex.getMessage(),
				System.currentTimeMillis()
		);

		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(IllegalRegisterException.class)
	public ResponseEntity<ErrorResponse> handleWrongRegister(IllegalRegisterException ex)
	{
		ErrorResponse error = new ErrorResponse(
				HttpStatus.FORBIDDEN.value(),
				ex.getMessage(),
				System.currentTimeMillis()
		);

		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}

}
