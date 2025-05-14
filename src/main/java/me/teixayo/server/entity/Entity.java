package me.teixayo.server.entity;

public abstract class Entity {
    public static int ENTITY_ID = 0;

    private final int entityId;

    public Entity() {
        this.entityId = ENTITY_ID++;
    }

    public int getEntityId() {
        return entityId;
    }
}
