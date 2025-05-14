package me.teixayo.server.chunk;

import io.netty.util.collection.LongObjectHashMap;
import lombok.Getter;
import me.teixayo.server.Server;
import me.teixayo.server.entity.player.Player;
import me.teixayo.server.protocol.packet.server.play.chunk.MapChunkBulkDataPacket;
import me.teixayo.server.visual.SimplexNoise;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class World {

    private final LongObjectHashMap<Chunk> chunks;

    @Getter
    private final ConcurrentLinkedQueue<Chunk> queueChunks;
    @Getter
    private final ConcurrentLinkedQueue<Chunk> queuePacketsLoadedChunks;

    public World() {
        this.chunks = new LongObjectHashMap<>();
        this.queueChunks = new ConcurrentLinkedQueue<>();
        this.queuePacketsLoadedChunks = new ConcurrentLinkedQueue<>();
    }


    public Chunk getChunk(int x, int y) {
        return chunks.get(getChunkHash(x, y));
    }

    public Chunk getChunk(Position position) {
        return getChunk(position.getX() >> 4, position.getY() >> 4);
    }

    public Chunk loadEmptyChunk(int x, int y) {
        Chunk chunk = new Chunk(x, y);
        chunk.initialize();
        chunks.put(getChunkHash(x, y), chunk);
        return chunk;
    }

    public Chunk loadEmptyChunk(Position position) {
        return loadEmptyChunk(position.getX() >> 4, position.getY() >> 4);
    }

    public void setChunk(int x, int y, Chunk chunk) {
        chunks.put(getChunkHash(x, y), chunk);
    }

    public void loadChunk(int x, int y) {
        queueChunks.add(new Chunk(x, y));

    }

    public void finalizeForLoadingChunks() {

        ArrayList<Chunk> chunkWithCap = new ArrayList<>();

        while (!queuePacketsLoadedChunks.isEmpty()) {

            chunkWithCap.add(queuePacketsLoadedChunks.poll());
            if (chunkWithCap.size() >= 4) {
                MapChunkBulkDataPacket packetChunk = new MapChunkBulkDataPacket(chunkWithCap);
                for (Player player : Server.get().getPlayers()) {
                    player.sendPacket(packetChunk);
                }
                chunkWithCap.clear();
            }

        }


        if (!chunkWithCap.isEmpty()) {
            MapChunkBulkDataPacket packetChunk = new MapChunkBulkDataPacket(chunkWithCap);
            for (Player player : Server.get().getPlayers()) {
                player.sendPacket(packetChunk);
            }
        }
    }

    public void loadQueueChunks() {

        if (queueChunks.isEmpty()) return;
        while (!queueChunks.isEmpty()) {
            Chunk chunk = queueChunks.poll();
            Server.get().getSchedulerManager().getExecutor().execute(() -> {
                chunk.initialize();

                generate(chunk);
                setChunk(chunk.getChunkX(), chunk.getChunkY(), chunk);
                queuePacketsLoadedChunks.add(chunk);
            });
        }
    }

    private void generate(Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 256; y++) {
                for (int z = 0; z < 16; z++) {
                    int finalX = (16 * chunk.getChunkX()) + x;
                    int finalZ = (16 * chunk.getChunkY()) + z;
                    int finalY = y;
                    double noise = SimplexNoise.noise(finalX / 50.0, finalY / 50.0, finalZ / 50.0);

                    if (noise < -0.3) {
                        if (noise < -0.5) {
                            chunk.setBlock(x, y, z, 1, (byte) 0);
                        } else {
                            chunk.setBlock(x, y, z, 3, (byte) 0);
                        }
                    }


//                    double noise = SimplexNoise.noise(finalX / 100.0 + ThreadLocalRandom.current().nextGaussian() / 500.0, finalZ / 100.0 + ThreadLocalRandom.current().nextGaussian() / 500.0);
//                    int y1 = (int) ((Math.abs(noise) * (10 * Math.abs(noise * 5))) + 10);
//                    chunk.setBlock(x, y1, z, 2, (byte) 0);
//
//                    if(y1 == 10) {
//                        chunk.setBlock(x,y1,z,8, (byte) 0);
//                    }
//
//                    for(int yi = 0 ; yi < y1; yi++) {
//                        chunk.setBlock(x, yi, z, 1, (byte) 0);
//                    }
                }

            }
        }
        for (ChunkSection section : chunk.getSections()) {
            if (section == null) continue;
            section.getEmittedLight().getPointer().getSegment().fill((byte) 0xFF);
        }
    }


    private long getChunkHash(int x, int y) {
        return ((long) x << 32) + y - Integer.MIN_VALUE;
    }


}
