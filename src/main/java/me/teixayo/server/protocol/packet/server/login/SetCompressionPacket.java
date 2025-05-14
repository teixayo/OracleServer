package me.teixayo.server.protocol.packet.server.login;

import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;

public class SetCompressionPacket extends ServerPacket {

    private final int compressionThreshold;

    public SetCompressionPacket(int compressionThreshold) {
        this.compressionThreshold = compressionThreshold;
    }

    @Override
    public void write() {
        writeVarInt(compressionThreshold);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.LOGIN_SET_COMPRESSION;
    }
}
