package me.teixayo.server.protocol.packet.server.play.window;

import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;

public class OpenWindowPacket extends ServerPacket {

    private final int windowID;
    private final WindowType windowType;
    private final String windowTitle;
    private final byte numberOfSlots;
//    private Optional<Integer> entityID; //EntityHorse's EID. Only sent when Window Type is “EntityHorse”

    public OpenWindowPacket(int windowID, WindowType windowType, String windowTitle, byte numberOfSlots) {
        this.windowID = windowID;
        this.windowType = windowType;
        this.windowTitle = windowTitle;
        this.numberOfSlots = numberOfSlots;
    }

    @Override
    public void write() {
        writeByte(windowID);
        writeString(windowType.getType());
        String title = "{\"text\": \"" + windowTitle + "\"}";
        writeString(title);
        writeByte(numberOfSlots);

    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.OPEN_WINDOW;
    }
}
