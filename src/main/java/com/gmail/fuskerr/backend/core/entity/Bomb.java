package com.gmail.fuskerr.backend.core.entity;

import com.gmail.fuskerr.backend.core.game.Tickable;
import com.gmail.fuskerr.backend.core.type.ItemType;

public class Bomb extends ReplicaItem implements Tickable{
    //тиков до взрыва
    private int ticks;

    public Bomb(long id, Position position, int ticks) {
        super(id, ItemType.BOMB, position);
        this.ticks = ticks;
    }

    @Override
    public void tick() {
        if(ticks > 0) ticks--;
    }
    
    public boolean isExplosion() {
        return ticks == 0;
    }
}
