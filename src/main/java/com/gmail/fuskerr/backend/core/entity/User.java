package com.gmail.fuskerr.backend.core.entity;

public class User {
    private Long id;
    private String name;
    private String token;

    public User(Long id, String name, String token) {
        this.id = id;
        this.name = name;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }
}
