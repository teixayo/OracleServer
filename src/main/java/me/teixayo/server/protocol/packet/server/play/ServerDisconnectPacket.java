package me.teixayo.server.protocol.packet.server.play;

import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;

public class ServerDisconnectPacket extends ServerPacket {

    private final String reason;

    public ServerDisconnectPacket(String reason) {
        this.reason = reason;
    }

    @Override
    public void write() {
        String json = "{\"text\": \"" + reason + "\"}";
        writeString(json);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.DISCONNECT;
    }
}
