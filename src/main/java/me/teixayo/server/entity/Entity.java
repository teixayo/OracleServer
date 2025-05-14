package me.teixayo.server.entity;

import lombok.Getter;

public abstract class Entity {
    public static int ENTITY_ID = 0;

    @Getter
    private final int entityId;

    public Entity() {
        this.entityId = ENTITY_ID++;
    }

}
