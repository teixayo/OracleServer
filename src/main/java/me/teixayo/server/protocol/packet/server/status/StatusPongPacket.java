package me.teixayo.server.protocol.packet.server.status;

import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;

public class StatusPongPacket extends ServerPacket {
    private final long payLoad;

    public StatusPongPacket(long payLoad) {
        this.payLoad = payLoad;
    }

    @Override
    public void write() {
        writeLong(payLoad);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.STATUS_PONG;
    }
}
