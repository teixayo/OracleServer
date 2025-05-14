package me.teixayo.server.protocol.packet.server.play;

import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;

public class PlayerHeaderFooterPacket extends ServerPacket {

    private final String header;
    private final String footer;

    public PlayerHeaderFooterPacket(String header, String footer) {
        this.header = header;
        this.footer = footer;
    }

    @Override
    public void write() {
        String headerJson = "{\"text\": \"" + header + "\"}";
        writeString(headerJson);
        String footerJson = "{\"text\": \"" + footer + "\"}";
        writeString(footerJson);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.HEADER_FOOTER;
    }
}
