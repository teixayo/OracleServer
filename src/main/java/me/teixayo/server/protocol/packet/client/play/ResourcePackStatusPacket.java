package me.teixayo.server.protocol.packet.client.play;

import lombok.Getter;
import me.teixayo.server.protocol.packet.client.ClientPacket;

@Getter
public class ResourcePackStatusPacket extends ClientPacket {

    private String hash;
    private int result;

    /* 0: successfully loaded, 1: declined, 2: failed download, 3: accepted */
    @Override
    public void read() {
        hash = readString();
        result = readVarInt();
    }
}
