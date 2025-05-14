package me.teixayo.server.protocol.packet.client.handshake;

import lombok.Getter;
import me.teixayo.server.protocol.packet.client.ClientPacket;


@Getter
public class ClientHandshakePacket extends ClientPacket {

    private int protocol;
    private String address;
    private int port;
    private byte status;

    @Override
    public void read() {

        this.protocol = readVarInt();
        this.address = readString();
        this.port = readUnsignedShort();
        this.status = readByte();
    }
}
