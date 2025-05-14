package me.teixayo.server.memory;

import java.util.ArrayList;
import java.util.List;

public class MemoryManagement {


    private static final List<Pointer> pointerList;

    static {
        pointerList = new ArrayList<>();
    }

    public static Pointer createNewPointer(int sizeBytes) {
        Pointer pointer = Pointer.allocate(sizeBytes);
        pointerList.add(pointer);
        return pointer;
    }

    public static void freeAll() {
        for (Pointer pointer : pointerList) {
            pointer.free();

        }
    }
}
