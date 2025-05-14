package me.teixayo.server.protocol.packet.client.play;

import lombok.Getter;
import me.teixayo.server.protocol.packet.client.ClientPacket;

@Getter
public class PlayerChatMessagePacket extends ClientPacket {

    private String message;

    @Override
    public void read() {
        message = readString();
    }

}
