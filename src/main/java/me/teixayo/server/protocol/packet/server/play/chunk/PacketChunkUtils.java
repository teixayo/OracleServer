package me.teixayo.server.protocol.packet.server.play.chunk;

import me.teixayo.server.chunk.Chunk;
import me.teixayo.server.chunk.ChunkSection;
import me.teixayo.server.memory.MemoryManagement;
import me.teixayo.server.memory.Pointer;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class PacketChunkUtils {


    public static ChunkMap a(Chunk chunk, boolean applyEmittedLight, boolean applyBiomes, int i) {
        ChunkSection[] chunkSections = chunk.getSections();
        ChunkMap packetplayoutmapchunk_chunkmap = new ChunkMap();
        ArrayList<ChunkSection> arraylist = new ArrayList<>();

        int j = 0;
        for (ChunkSection chunkSection : chunkSections) {
            if (chunkSection != null && (!applyBiomes || !chunkSection.hasLightingMap()) && (i & 1 << j) != 0) {
                packetplayoutmapchunk_chunkmap.sectionMask |= 1 << j;
                arraylist.add(chunkSection);
            }
            j++;
        }

        int chunks = Integer.bitCount(packetplayoutmapchunk_chunkmap.sectionMask);

        Pointer pointer = MemoryManagement.createNewPointer(
                chunks * (16 * 16 * 16 * 2) +
                        chunks * (2048) +
                        (applyEmittedLight ? chunks * 2048 : 0) +
                        256
        );
        ByteBuffer byteBuffer = pointer.getSegment().asByteBuffer();

        for (ChunkSection chunkSection : arraylist) {
//            byteBuffer.writeBytes(chunkSection.getByteBuffer());
            byteBuffer.put(chunkSection.getByteBuffer());
//
//            for (int l = 0; l < 4096; l++) {
//
//                byte i2 = chunkSection.getByteBuffer().getByte(l * 2);
//
//                byteBuffer.writeByte(i2);
//                byte i1 = chunkSection.getByteBuffer().getByte((l * 2) + 1);
//                byteBuffer.writeByte(i1);
//
//            }
        }
        for (ChunkSection chunkSection : arraylist) {
//            byteBuffer.writeBytes(chunkSection.getEmittedLight().getBuf());
            byteBuffer.put(chunkSection.getEmittedLight().getBuf());
        }
        if (applyEmittedLight) {
            for (ChunkSection chunkSection : arraylist) {

//                byteBuffer.writeBytes(chunkSection.getSkyLight().getBuf());
                byteBuffer.put(chunkSection.getSkyLight().getBuf());
            }

        }

        if (applyBiomes) {
//            byteBuffer.writeBytes(chunk.getBiomeIndex().getSegment().asByteBuffer());
            byteBuffer.put(chunk.getBiomeIndex().getSegment().asByteBuffer());
        }


        packetplayoutmapchunk_chunkmap.data = byteBuffer;
        packetplayoutmapchunk_chunkmap.pointer = pointer;

        return packetplayoutmapchunk_chunkmap;
    }

    public static class ChunkMap {
        public ByteBuffer data;
        public Pointer pointer;
        public int sectionMask;

        public ChunkMap() {
        }
    }
}
