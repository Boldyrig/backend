package com.gmail.fuskerr.backend.requestbody;

public class TokenRequest {
    private final String token;

    public TokenRequest(String token) {
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
