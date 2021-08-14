package com.gmail.fuskerr.backend.core.entity;

import com.gmail.fuskerr.backend.core.type.BonusType;
import com.gmail.fuskerr.backend.core.type.ItemType;

public class Bonus extends ReplicaItem {
    private final BonusType bonusType;

    public Bonus(long id, ItemType type, Position position, BonusType bonusType) {
        super(id, type, position);
        this.bonusType = bonusType;
    }
}
