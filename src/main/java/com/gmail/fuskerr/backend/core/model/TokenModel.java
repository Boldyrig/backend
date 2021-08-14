package com.gmail.fuskerr.backend.core.model;

public class TokenModel {
    private final String token;

    public TokenModel(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "TokenRequest{" +
                "token='" + token + '\'' +
                '}';
    }
}
