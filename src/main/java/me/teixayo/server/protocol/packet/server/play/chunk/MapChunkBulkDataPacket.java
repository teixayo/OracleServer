package me.teixayo.server.protocol.packet.server.play.chunk;

import lombok.Getter;
import me.teixayo.server.chunk.Chunk;
import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;

import java.nio.ByteBuffer;
import java.util.List;

public class MapChunkBulkDataPacket extends ServerPacket {

    @Getter
    private final PacketChunkUtils.ChunkMap[] chunkMaps;
    private final Chunk[] chunks;

    public MapChunkBulkDataPacket(List<Chunk> chunks) {
        chunkMaps = new PacketChunkUtils.ChunkMap[chunks.size()];
        this.chunks = chunks.toArray(new Chunk[0]);
        int x = 0;
        for (Chunk chunk : chunks) {
            chunkMaps[x] =
                    PacketChunkUtils.a(chunk, true, true, 65535);
            x++;
        }

    }

    @Override
    public void write() {

        writeBoolean(true);
        writeVarInt(chunkMaps.length);

        for (int i = 0; i < chunkMaps.length; i++) {
            writeInt(chunks[i].getChunkX());
            writeInt(chunks[i].getChunkY());
            writeShort((short) (chunkMaps[i].sectionMask & '\uffff'));

        }

        for (PacketChunkUtils.ChunkMap chunkMap : chunkMaps) {
            ByteBuffer data = chunkMap.data;

            data.rewind();

            writeBytes(data);
            chunkMap.pointer.free();
        }
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.MAP_CHUNK_BULK_DATA;
    }

}
