package com.gmail.fuskerr.backend.requestbody;

public class UserNameRequest {
    private final String name;

    public UserNameRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "UserNameRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
