package com.generation.vsnbackend.model.entities.signin;

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


