package me.teixayo.server.chunk;

import lombok.Getter;
import me.teixayo.server.memory.MemoryManagement;
import me.teixayo.server.memory.Pointer;

import java.nio.ByteBuffer;

@Getter
public class NibbleArray {
    private final Pointer pointer;

    public NibbleArray() {
        this.pointer = MemoryManagement.createNewPointer(2048);
    }


    public ByteBuffer getBuf() {
        return pointer.getSegment().asByteBuffer();
    }
}
