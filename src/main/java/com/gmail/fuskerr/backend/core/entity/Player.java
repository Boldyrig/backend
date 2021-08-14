package com.gmail.fuskerr.backend.core.entity;

public class Player {
    private final Long id;
    private final String name;
    private final String token;
    
    private boolean ready = false;

    public Player(Long id, String name, String token) {
        this.id = id;
        this.name = name;
        this.token = token;
    }
    
    public void setReady(boolean ready) {
        this.ready = ready;
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

    public boolean isReady() {
        return ready;
    }
}
