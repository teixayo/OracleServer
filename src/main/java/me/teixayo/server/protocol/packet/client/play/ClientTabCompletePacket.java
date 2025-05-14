package me.teixayo.server.protocol.packet.client.play;

import lombok.Getter;
import me.teixayo.server.chunk.Position;
import me.teixayo.server.protocol.packet.client.ClientPacket;

@Getter
public class ClientTabCompletePacket extends ClientPacket {

    private String text;
    private boolean hasPosition;
    private Position position;

    @Override
    public void read() {
        text = readString();
        hasPosition = readBoolean();
        if (hasPosition) {
            position = readPosition();
        }
    }
}
