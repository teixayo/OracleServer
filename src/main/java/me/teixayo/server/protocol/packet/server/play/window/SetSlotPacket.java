package me.teixayo.server.protocol.packet.server.play.window;

import lombok.SneakyThrows;
import me.teixayo.server.item.ItemStack;
import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;

public class SetSlotPacket extends ServerPacket {

    private final int windowID;
    private final int slot;
    private final ItemStack itemStack;

    public SetSlotPacket(int windowID, int slot, ItemStack itemStack) {
        this.windowID = windowID;
        this.slot = slot;
        this.itemStack = itemStack;
    }

    @SneakyThrows
    @Override
    public void write() {

        writeByte(windowID);
        writeShort(slot);

        writeItem(itemStack);

    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.SET_SLOT;
    }
}
