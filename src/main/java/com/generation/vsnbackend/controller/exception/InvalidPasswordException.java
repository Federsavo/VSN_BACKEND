package com.generation.vsnbackend.controller.exception;

public class InvalidPasswordException extends RuntimeException
{
	public InvalidPasswordException(String message)
	{
		super(message);
	}
	public InvalidPasswordException(){}
}
