package me.teixayo.server.protocol.packet.server.status;

import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;

public class StatusResponsePacket extends ServerPacket {
    private final String name;
    private final int protocol;
    private final int max;
    private final int online;
    private final String description;

    public StatusResponsePacket(String name, int protocol, int max, int online, String description) {
        this.name = name;
        this.protocol = protocol;
        this.max = max;
        this.online = online;
        this.description = description;
    }

    @Override
    public void write() {
        String json = "{\"version\":{\"name\":\"" + name + "\",\"protocol\": " + protocol + "},\"players\":{\"max\":" + max + ",\"online\":" + online + "},\"description\":{\"text\":\"" + description + "\"}}";
        writeString(json);

    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.STATUS_RESPONSE;
    }
}
