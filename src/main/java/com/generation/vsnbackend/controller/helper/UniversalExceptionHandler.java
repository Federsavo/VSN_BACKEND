package com.generation.vsnbackend.controller.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.generation.vsnbackend.controller.exception.IllegalRegisterException;
import com.generation.vsnbackend.controller.exception.InvalidPasswordException;
import com.generation.vsnbackend.controller.exception.InvalidUsernameException;
import com.generation.vsnbackend.controller.exception.PostContentException;
import com.generation.vsnbackend.model.entities.errors.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UniversalExceptionHandler
{
	/**
	 * Handles exceptions for invalid passwords.
	 *
	 * @param ex the exception thrown when an invalid password is encountered
	 * @return a ResponseEntity containing an error response with a 403 Forbidden status
	 */
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

	/**
	 * Handles exceptions for invalid usernames.
	 *
	 * @param ex the exception thrown when an invalid username is encountered
	 * @return a ResponseEntity containing an error response with a 403 Forbidden status
	 */
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

	/**
	 * Handles exceptions for illegal registration attempts.
	 *
	 * @param ex the exception thrown when an illegal registration attempt occurs
	 * @return a ResponseEntity containing an error response with a 403 Forbidden status
	 */
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

	/**
	 * Handles exceptions for JSON processing errors.
	 *
	 * @param ex the exception thrown when an error occurs during JSON processing
	 * @return a ResponseEntity containing an error response with a 400 Bad Request status
	 */
	@ExceptionHandler(JsonProcessingException.class)
	public ResponseEntity<ErrorResponse> handleWrongRegister(JsonProcessingException ex)
	{
		ErrorResponse error = new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				ex.getMessage(),
				System.currentTimeMillis()
		);

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}


	/**
	 * Handles exceptions related to invalid or problematic post content.
	 *
	 * @param ex the exception thrown when there is an issue with post content
	 * @return a ResponseEntity containing an error response with a 400 Bad Request status
	 */
	@ExceptionHandler(PostContentException.class)
	public ResponseEntity<ErrorResponse> handlePostContent(JsonProcessingException ex)
	{
		ErrorResponse error = new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				ex.getMessage(),
				System.currentTimeMillis()
		);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}
