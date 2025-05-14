package me.teixayo.server.protocol.packet.server.play.chunk;

import me.teixayo.server.chunk.Chunk;
import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;

import java.nio.ByteBuffer;

public class ChunkDataPacket extends ServerPacket {

    private final boolean groundUpContinuous;

    private final PacketChunkUtils.ChunkMap chunkMap;
    private final Chunk chunk;

    public ChunkDataPacket(Chunk chunk, boolean groundUpContinuous) {
        chunkMap = PacketChunkUtils.a(chunk, true, true, 65535);
        this.chunk = chunk;
        this.groundUpContinuous = groundUpContinuous;
    }

    @Override
    public void write() {
        writeInt(chunk.getChunkX());
        writeInt(chunk.getChunkY());
        writeBoolean(groundUpContinuous);
        writeShort((short) (chunkMap.sectionMask & 0xFFFF));

        ByteBuffer data = chunkMap.data;

        data.rewind();

        writeVarInt(data.remaining());
        writeBytes(data);
        chunkMap.pointer.free();

    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.CHUNK_DATA;
    }
}
