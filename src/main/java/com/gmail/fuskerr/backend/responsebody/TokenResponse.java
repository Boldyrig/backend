package com.gmail.fuskerr.backend.responsebody;

public class TokenResponse {
    private final String token;

    public TokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "TokenResponse{" +
                "token='" + token + '\'' +
                '}';
    }
}
