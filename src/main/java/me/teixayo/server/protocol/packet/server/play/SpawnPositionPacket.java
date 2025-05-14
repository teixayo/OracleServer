package me.teixayo.server.protocol.packet.server.play;

import me.teixayo.server.chunk.Position;
import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;


public class SpawnPositionPacket extends ServerPacket {
    private final Position position;

    public SpawnPositionPacket(Position position) {
        this.position = position;
    }

    @Override
    public void write() {
        writePosition(position);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.SPAWN_POSITION;
    }
}
