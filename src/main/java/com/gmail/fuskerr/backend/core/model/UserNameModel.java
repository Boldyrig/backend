package com.gmail.fuskerr.backend.core.model;

public class UserNameModel {
    private final String name;

    public UserNameModel(String name) {
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
