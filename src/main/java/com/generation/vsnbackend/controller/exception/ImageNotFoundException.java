package com.generation.vsnbackend.controller.exception;

public class ImageNotFoundException extends RuntimeException
{
	public ImageNotFoundException(String message)
	{
		super(message);
	}
}
