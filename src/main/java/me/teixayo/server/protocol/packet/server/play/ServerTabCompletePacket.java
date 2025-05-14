package me.teixayo.server.protocol.packet.server.play;

import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;

public class ServerTabCompletePacket extends ServerPacket {

    private final int count;
    private final String[] matches;


    public ServerTabCompletePacket(String[] matches) {
        this.matches = matches;
        this.count = matches.length;
    }

    @Override
    public void write() {
        writeVarInt(count);
        for (String match : matches) {
            writeString(match);
        }
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.TAB_COMPLETE;
    }
}
