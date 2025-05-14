package me.teixayo.server.chunk;

import lombok.Getter;
import me.teixayo.server.memory.MemoryManagement;
import me.teixayo.server.memory.Pointer;

public class Chunk {

    @Getter
    private final int chunkX;
    @Getter
    private final int chunkY;
    @Getter
    private ChunkSection[] sections;
    @Getter
    private Pointer biomeIndex;


    public Chunk(int chunkX, int chunkY) {
        this.chunkX = chunkX;
        this.chunkY = chunkY;
    }

    public void initialize() {
        this.sections = new ChunkSection[16];
        this.biomeIndex = MemoryManagement.createNewPointer(256);
    }

    public void free() {
        for (int i = 0; i < 16; i++) {
            sections[i].free();
        }
        biomeIndex.free();
    }

    public void fill(
            int id, byte data) {
        for (int i = 0; i < 16; i++) {
            ChunkSection section = sections[i];
            if (section == null) {
                section = sections[i] = new ChunkSection();
            }
            section.fill(id, data);
        }
    }


    public void setBlock(int x, int y, int z, int id, byte data) {
        ChunkSection section = sections[y >> 4];
        if (section == null) {
            section = sections[y >> 4] = new ChunkSection();
        }

        section.setBlock(x & 0xf, y & 0xf, z & 0xf, id, data);
    }
}
