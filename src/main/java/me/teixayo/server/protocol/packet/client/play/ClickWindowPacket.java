package me.teixayo.server.protocol.packet.client.play;

import lombok.Getter;
import me.teixayo.server.item.ItemStack;
import me.teixayo.server.protocol.packet.client.ClientPacket;

@Getter
public class ClickWindowPacket extends ClientPacket {

    private byte windowID;
    private short slot;
    private byte button;
    private short actionNumber;
    private byte mode;
    private ItemStack clickedItem;

    @Override
    public void read() {
        windowID = readByte();
        slot = readShort();
        button = readByte();
        actionNumber = readShort();
        mode = readByte();

        clickedItem = readItem();
    }

}
