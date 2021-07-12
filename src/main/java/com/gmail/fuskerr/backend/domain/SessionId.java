package com.gmail.fuskerr.backend.domain;

public class SessionId {
    private int id;

    public SessionId(int id) {
        this.id = id;
    }

    public SessionId() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SessionId{" +
                "id=" + id +
                '}';
    }
}
