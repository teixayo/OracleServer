package me.teixayo.server.entity;

public class WatchableObject {

    private final int objectClassID;
    private final int objectIndex;
    private final Object object;

    public WatchableObject(int objectClassID, int objectIndex, Object object) {
        this.objectClassID = objectClassID;
        this.objectIndex = objectIndex;
        this.object = object;
    }

    public int getObjectClassID() {
        return objectClassID;
    }

    public int getObjectIndex() {
        return objectIndex;
    }

    public Object getObject() {
        return object;
    }
}
