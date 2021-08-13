package com.gmail.fuskerr.backend.core.entity;

import com.gmail.fuskerr.backend.core.type.Direction;

public class ExplosionSide {
    private boolean stopped = false;
    private final Direction direction;

    public ExplosionSide(Direction direction) {
        this.direction = direction;
    }
    
    public void stop() {
        stopped = true;
    }
    
    public boolean isStopped() {
        return stopped;
    }
    
    public Direction getDirection() {
        return direction;
    }
}
