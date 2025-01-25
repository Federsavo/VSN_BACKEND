package com.generation.vsnbackend.model.entities.signin;

/**
 * Represents a generic response object that holds a message.
 * The Response class is used to encapsulate a message that can be
 * returned from API endpoints or service methods to provide information
 * about the result of an operation, such as success or failure.
 */
public class Response
{

    private String message;

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
