package me.teixayo.server.protocol.packet.client.play;

import lombok.Getter;
import me.teixayo.server.protocol.packet.client.ClientPacket;

import java.util.UUID;

@Getter
public class SpectatePacket extends ClientPacket {

    private UUID targetPlayer;

    @Override
    public void read() {
        targetPlayer = readUUID();
    }
}
