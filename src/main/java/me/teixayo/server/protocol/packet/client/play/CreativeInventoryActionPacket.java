package me.teixayo.server.protocol.packet.client.play;

import lombok.Getter;
import me.teixayo.server.item.ItemStack;
import me.teixayo.server.protocol.packet.client.ClientPacket;

@Getter
public class CreativeInventoryActionPacket extends ClientPacket {

    private short slot;
    private ItemStack itemStack;

    @Override
    public void read() {
        slot = readShort();


        itemStack = readItem();
    }
}
