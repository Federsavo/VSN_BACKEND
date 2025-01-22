package com.generation.vsnbackend.model.entities.signin;

public class SignIn {

    private String message;

    public SignIn(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
