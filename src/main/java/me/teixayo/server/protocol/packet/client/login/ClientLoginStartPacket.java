package me.teixayo.server.protocol.packet.client.login;

import lombok.Getter;
import me.teixayo.server.protocol.packet.client.ClientPacket;


@Getter
public class ClientLoginStartPacket extends ClientPacket {

    private String username;

    @Override
    public void read() {
        this.username = readString();
    }
}
