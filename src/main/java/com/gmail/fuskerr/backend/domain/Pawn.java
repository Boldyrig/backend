package com.gmail.fuskerr.backend.domain;

public class Pawn extends ReplicaItem {
    private Direction direction;
    private boolean alive;
    private final String token;

    public Pawn(long id, ItemType type, Position position, Direction direction, boolean alive, String token) {
        super(id, type, position);
        this.direction = direction;
        this.alive = alive;
        this.token = token;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isAlive() {
        return alive;
    }

    public String getToken() {
        return token;
    }
    
    public void setPosition(Position position) {
        this.position = position;
    }
    
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
