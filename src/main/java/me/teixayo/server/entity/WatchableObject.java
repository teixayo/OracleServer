package me.teixayo.server.entity;

import lombok.Getter;

public class WatchableObject {

    @Getter
    private final int objectClassID;
    @Getter
    private final int objectIndex;
    @Getter
    private final Object object;

    public WatchableObject(int objectClassID, int objectIndex, Object object) {
        this.objectClassID = objectClassID;
        this.objectIndex = objectIndex;
        this.object = object;
    }
}
