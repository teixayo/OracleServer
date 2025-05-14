package me.teixayo.server.chunk;

import lombok.Getter;
import me.teixayo.server.memory.MemoryManagement;
import me.teixayo.server.memory.Pointer;

import java.lang.foreign.ValueLayout;
import java.nio.ByteBuffer;

public class ChunkSection {

    public static final int SECTION_SIZE = 16 * 16 * 16;

    private final Pointer byteBuffer;
    @Getter
    private final NibbleArray emittedLight;
    @Getter
    private final NibbleArray skyLight;

    //private char[] segment;
    public ChunkSection() {
        byteBuffer = MemoryManagement.createNewPointer(SECTION_SIZE * 2);

        emittedLight = new NibbleArray();
        skyLight = new NibbleArray();

        //segment = new char[SECTION_SIZE];
    }

    public void setBlock(int x, int y, int z, int id, byte data) {
        char block = (char) ((id << 4) | data);

        int index = y << 8 | z << 4 | x;
//
        byteBuffer.getSegment().set(ValueLayout.JAVA_CHAR, index * 2L, block);

    }


    public ByteBuffer getByteBuffer() {
        return byteBuffer.getSegment().asByteBuffer();
    }

    public void fill(int id, byte data) {
        char block = (char) ((id << 4) | data);
        for (int index = 0; index < SECTION_SIZE; index++) {
            byteBuffer.getSegment().set(ValueLayout.JAVA_CHAR, index * 2L, block);
        }
    }

    public void fillFast(int id, byte data) {
//        int i = 0;
//        VectorSpecies<Byte> speciesPreferred = ByteVector.SPECIES_PREFERRED;
//
////        char block = (char) ((id << 4) | data);
//
//        byte[] array = byteBuffer.array();
//        for(; i < speciesPreferred.loopBound(SECTION_SIZE * 2); i+= speciesPreferred.length()) {
//            VectorMask<Byte> byteVectorMask = speciesPreferred.indexInRange(i, SECTION_SIZE * 2);
//            ByteVector vector = ByteVector.fromArray(speciesPreferred, array, i, byteVectorMask);
////            vector.mul(0).add(block);
//        }
////        for(; i < array.length; i++) {
////            array[i] = block;
////        }


    }

    public void free() {
        byteBuffer.free();
    }

    public boolean hasLightingMap() {
        return false;
    }
}
