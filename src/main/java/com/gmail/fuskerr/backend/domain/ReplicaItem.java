package com.gmail.fuskerr.backend.domain;

public class ReplicaItem {
    private final long id;
    private final ItemType type;
    private final Position position;

    public ReplicaItem(long id, ItemType type, Position position) {
        this.id = id;
        this.type = type;
        this.position = position;
    }

    public long getId() {
        return id;
    }

    public ItemType getType() {
        return type;
    }

    public Position getPosition() {
        return position;
    }
}
