package me.teixayo.server.protocol.packet.client.play;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import me.teixayo.server.protocol.packet.client.ClientPacket;

@Getter
public class PluginMessagePacket extends ClientPacket {

    private String channel;
    private ByteBuf data;

    @Override
    public void read() {
        channel = readString();
        data = readAllBytes();
        data.release();
        //Todo fix this because bad client use this method for crash the server
    }
}
