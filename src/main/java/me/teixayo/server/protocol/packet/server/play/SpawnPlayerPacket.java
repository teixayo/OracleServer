package me.teixayo.server.protocol.packet.server.play;

import me.teixayo.server.entity.DataWatcher;
import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;

import java.util.UUID;

public class SpawnPlayerPacket extends ServerPacket {

    private final int entityID;
    private final UUID uuid;
    private final int x;
    private final int y;
    private final int z;
    private final byte yaw;
    private final byte pitch;
    private final short currentItem;
    private final DataWatcher dataWatcher;

    public SpawnPlayerPacket(int entityID, UUID uuid, double x, double y, double z, float yaw, float pitch, short currentItem, DataWatcher dataWatcher) {
        this.entityID = entityID;
        this.uuid = uuid;
        this.x = (int) (x * 32.0);
        this.y = (int) (y * 32.0);
        this.z = (int) (z * 32.0);
        this.yaw = (byte) (int) (yaw * 256.0F / 360.0F);
        this.pitch = (byte) (int) (pitch * 256.0F / 360.0F);
        this.currentItem = currentItem;
        this.dataWatcher = dataWatcher;
    }

    @Override
    public void write() {
        writeVarInt(entityID);
        writeUUIDLong(uuid);
        writeInt(x);
        writeInt(y);
        writeInt(z);
        writeByte(yaw);
        writeByte(pitch);
        writeShort(currentItem);
        writeDataWatcher(dataWatcher);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.SPAWN_PLAYER;
    }
}
