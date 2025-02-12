package com.generation.vsnbackend.controller.exception;

public class IllegalRegisterException extends RuntimeException
{
	public IllegalRegisterException(String message)
	{
		super(message);
	}
	public IllegalRegisterException(){}
}
