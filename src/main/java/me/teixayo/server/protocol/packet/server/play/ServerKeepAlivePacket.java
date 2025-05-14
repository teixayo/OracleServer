package me.teixayo.server.protocol.packet.server.play;

import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;


public class ServerKeepAlivePacket extends ServerPacket {
    private final int keepAliveID;

    public ServerKeepAlivePacket(int keepAliveID) {
        this.keepAliveID = keepAliveID;
    }

    @Override
    public void write() {
        writeVarInt(keepAliveID);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.KEEP_ALIVE;
    }
}
