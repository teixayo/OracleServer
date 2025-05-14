package me.teixayo.server.entity;

import me.teixayo.server.chunk.Position;
import me.teixayo.server.item.ItemStack;

import java.util.HashMap;

public class DataWatcher {


    private static final HashMap<Class<?>, Integer> classToId = new HashMap<>();

    static {
        classToId.put(Byte.class, 0);
        classToId.put(Short.class, 1);
        classToId.put(Integer.class, 2);
        classToId.put(Float.class, 3);
        classToId.put(String.class, 4);
        classToId.put(ItemStack.class, 5);
        classToId.put(Position.class, 6);
    }

    private final HashMap<Integer, WatchableObject> data;

    public DataWatcher() {
        data = new HashMap<>();
    }

    public <T> void addWatchableObject(int index, T object) {
        int id = classToId.get(object.getClass());
        WatchableObject watchableObject = new WatchableObject(id, index, object);
        data.put(index, watchableObject);
    }

    public HashMap<Integer, WatchableObject> getData() {
        return data;
    }
}
