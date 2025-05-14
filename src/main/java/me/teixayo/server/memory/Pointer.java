package me.teixayo.server.memory;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Pointer {

    private MemorySegment segment;
    private Arena arena;

    protected static Pointer allocate(int sizeBytes) {
        Pointer pointer = new Pointer();
        pointer.arena = Arena.ofShared();
        pointer.segment = pointer.arena.allocate(sizeBytes);
        return pointer;
    }


    public void free() {
        arena.close();
    }
}
