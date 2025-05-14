package me.teixayo.server.protocol.packet.server.play;

import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;

public class ServerChatMessagePacket extends ServerPacket {
    private final String message;
    private final byte position;

    public ServerChatMessagePacket(String message, byte position) {
        this.message = message;
        this.position = position;
    }
    //0: chat (chat box), 1: system message (chat box), 2: above hotbar

    @Override
    public void write() {
        String json = "{\"text\": \"" + message + "\"}";
        writeString(json);
        writeByte(position);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.CHAT_MESSAGE;
    }
}
