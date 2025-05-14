package me.teixayo.server.protocol.packet.client.status;

import lombok.Getter;
import me.teixayo.server.protocol.packet.client.ClientPacket;

@Getter
public class StatusPingPacket extends ClientPacket {

    private long payLoad;

    @Override
    public void read() {
        payLoad = readLong();
    }
}
