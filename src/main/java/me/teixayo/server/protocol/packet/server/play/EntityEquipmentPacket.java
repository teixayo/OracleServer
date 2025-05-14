package me.teixayo.server.protocol.packet.server.play;

import me.teixayo.server.item.ItemStack;
import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;

public class EntityEquipmentPacket extends ServerPacket {

    private final int entityID;
    private final int slot;
    /*Equipment slot. 0: held, 1–4: armor slot (1: boots, 2: leggings, 3: chestplate, 4: helmet)*/
    private final ItemStack item;

    public EntityEquipmentPacket(int entityID, int slot, ItemStack item) {
        this.entityID = entityID;
        this.slot = slot;
        this.item = item;
    }

    @Override
    public void write() {
        writeVarInt(entityID);
        writeShort(slot);
        writeItem(item);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.ENTITY_EQUIPMENT;
    }
}
