package me.teixayo.server.protocol.packet.server.play.window;

import me.teixayo.server.item.ItemStack;
import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;

public class SetMultiSlotPacket extends ServerPacket {

    private final int windowID;
    private final short count;
    private final ItemStack[] items;

    public SetMultiSlotPacket(int windowID, ItemStack[] items) {
        this.windowID = windowID;
        this.count = (short) items.length;
        this.items = items;
    }

    @Override
    public void write() {
        writeByte(windowID);
        writeShort(count);
        for (ItemStack item : items) {
            writeItem(item);
        }
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.SET_MULTI_SLOT;
    }
}
