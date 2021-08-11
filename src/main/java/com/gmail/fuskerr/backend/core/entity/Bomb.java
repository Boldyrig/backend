package com.gmail.fuskerr.backend.core.entity;

import com.gmail.fuskerr.backend.core.game.Tickable;
import com.gmail.fuskerr.backend.core.type.ItemType;

public class Bomb extends ReplicaItem implements Tickable{
    //тиков до взрыва
    private int ticks;
    private final int power;

    public Bomb(long id, Position position, int ticks, int power) {
        super(id, ItemType.BOMB, position);
        this.ticks = ticks;
        this.power = power;
    }

    @Override
    public void tick() {
        if(ticks > 0) ticks--;
    }
    
    public boolean isExplosion() {
        return ticks == 0;
    }

    public int getPower() {
        return power;
    }
}
