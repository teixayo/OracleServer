package me.teixayo.server.protocol.packet.client.play;

import lombok.Getter;
import me.teixayo.server.protocol.packet.client.ClientPacket;

@Getter
public class EnchantItemPacket extends ClientPacket {

    private byte windowID;
    private byte enchantment;

    @Override
    public void read() {
        windowID = readByte();
        enchantment = readByte();
    }
}
