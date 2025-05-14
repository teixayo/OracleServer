package me.teixayo.server.protocol.packet.client.play;

import lombok.Getter;
import me.teixayo.server.protocol.packet.client.ClientPacket;

@Getter
public class HeldItemChangePacket extends ClientPacket {
    private short slot;

    @Override
    public void read() {
        slot = readShort();
    }
}
