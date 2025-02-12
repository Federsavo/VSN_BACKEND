package com.generation.vsnbackend.controller.exception;

public class InvalidUsernameException extends RuntimeException
{
	public InvalidUsernameException(String message)
	{
		super(message);
	}
	public InvalidUsernameException(){}
}
