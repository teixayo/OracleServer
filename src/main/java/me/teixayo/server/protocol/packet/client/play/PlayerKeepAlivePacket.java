package me.teixayo.server.protocol.packet.client.play;

import lombok.Getter;
import me.teixayo.server.protocol.packet.client.ClientPacket;


@Getter
public class PlayerKeepAlivePacket extends ClientPacket {

    private int keepAliveID;

    @Override
    public void read() {
        keepAliveID = readVarInt();
    }
}
