package com.gmail.fuskerr.backend.domain;

public class Bonus extends ReplicaItem {
    private final BonusType bonusType;

    public Bonus(long id, ItemType type, Position position, BonusType bonusType) {
        super(id, type, position);
        this.bonusType = bonusType;
    }
}
