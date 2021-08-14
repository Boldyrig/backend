package com.gmail.fuskerr.backend.core.entity;

import com.gmail.fuskerr.backend.core.type.ItemType;

public class ReplicaItem {
    private final long id;
    private final ItemType type;
    protected Position position;

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
