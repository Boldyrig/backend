package com.gmail.fuskerr.backend.domain;

public class Pawn extends ReplicaItem {
    private final Direction direction;
    private final boolean alive;

    public Pawn(long id, ItemType type, Position position, Direction direction, boolean alive) {
        super(id, type, position);
        this.direction = direction;
        this.alive = alive;
    }
}
