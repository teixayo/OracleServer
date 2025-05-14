package me.teixayo.server.protocol.packet.server.play;

import me.teixayo.server.chunk.Position;
import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;

public class BlockChangePacket extends ServerPacket {

    private final Position position;
    private final int blockID;

    public BlockChangePacket(Position position, int blockID) {
        this.position = position;
        this.blockID = blockID;
    }

    @Override
    public void write() {
        writePosition(position);
        writeVarInt(blockID);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.BLOCK_CHANGE;
    }
}
