package com.generation.vsnbackend.model.entities.signin;

/**
 * Represents a token that consists of a randomly generated prefix,
 * a specified identifier, and a randomly generated suffix.
 * The Token class is used to create unique tokens that can be
 * utilized for purposes such as authentication, session management,
 * or any scenario requiring a unique identifier.
 */
public class Token {

    private String token;

    public Token(Long id) {
        String prefix = "";
        String suffix = "";

        for(int i=0;i < 10;i++)
        {
            prefix += Math.round(Math.random()*10);
            suffix += Math.round(Math.random()*10);
        }

        this.token = prefix + "-" + id + "-" + suffix;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}


