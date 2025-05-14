package me.teixayo.server.protocol.packet.server.play;

import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;

public class HeldItemChangePacket extends ServerPacket {

    private final int slot;

    public HeldItemChangePacket(int slot) {
        this.slot = slot;
    }

    @Override
    public void write() {
        writeByte(slot);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.HELD_ITEM_CHANGE;
    }
}
