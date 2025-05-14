package me.teixayo.server.protocol.packet.server.play;

import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;

public class SpawnExperienceOrbPacket extends ServerPacket {

    private final int entityID;
    private final int x;
    private final int y;
    private final int z;
    private final short count;

    public SpawnExperienceOrbPacket(int entityID, double x, double y, double z, short count) {
        this.entityID = entityID;
        this.x = (int) (x * 32.0);
        this.y = (int) (y * 32.0);
        this.z = (int) (z * 32.0);
        this.count = count;
    }

    @Override
    public void write() {
        writeVarInt(entityID);
        writeInt(x);
        writeInt(y);
        writeInt(z);
        writeShort(count);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.SPAWN_EXPERIENCE_ORB;
    }
}
