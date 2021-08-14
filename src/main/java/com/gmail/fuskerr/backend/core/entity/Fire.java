package com.gmail.fuskerr.backend.core.entity;

import com.gmail.fuskerr.backend.core.game.Tickable;
import com.gmail.fuskerr.backend.core.type.ItemType;

public class Fire extends ReplicaItem implements Tickable {
    private int ticks;

    public Fire(long id, Position position, int ticks) {
        super(id, ItemType.FIRE, position);
        this.ticks = ticks;
    }

    @Override
    public void tick() {
        ticks--;
    }

    public boolean isBurn() {
        return ticks > 0;
    }
}
