package me.teixayo.server.protocol.packet.client.play;

import lombok.Getter;
import me.teixayo.server.chunk.Position;
import me.teixayo.server.item.ItemStack;
import me.teixayo.server.protocol.packet.client.ClientPacket;


@Getter
public class PlayerBlockPlacementPacket extends ClientPacket {

    private Position location;
    private PlayerDiggingPacket.Face face;
    private ItemStack heldItem;
    private byte cursorPositionX;
    private byte cursorPositionY;
    private byte cursorPositionZ;

    @Override
    public void read() {
        location = readPosition();
        face = PlayerDiggingPacket.Face.getFace(readByte());
        heldItem = readItem();
        cursorPositionX = readByte();
        cursorPositionY = readByte();
        cursorPositionZ = readByte();
    }
}
