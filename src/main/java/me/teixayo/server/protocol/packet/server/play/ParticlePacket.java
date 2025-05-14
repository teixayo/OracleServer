package me.teixayo.server.protocol.packet.server.play;

import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;

public class ParticlePacket extends ServerPacket {

    private final ParticleType particleID;
    private final boolean longDistance;
    private final float x;
    private final float y;
    private final float z;
    private final float offsetX;
    private final float offsetY;
    private final float offsetZ;
    private final float particleData;
    private final int particleCount;
    private final int[] data;
    //Todo add ParticleData class

    public ParticlePacket(ParticleType particleID, boolean longDistance, float x, float y, float z, float offsetX, float offsetY, float offsetZ, float particleData, int particleCount, int[] data) {
        this.particleID = particleID;
        this.longDistance = longDistance;
        this.x = x;
        this.y = y;
        this.z = z;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.particleData = particleData;
        this.particleCount = particleCount;
        this.data = data;
    }

    @Override
    public void write() {

        writeInt(particleID.getId());
        writeBoolean(longDistance);

        writeFloat(x);
        writeFloat(y);
        writeFloat(z);
        writeFloat(offsetX);
        writeFloat(offsetY);
        writeFloat(offsetZ);
        writeFloat(particleData);
        writeInt(particleCount);
        if (data == null) return;
        for (int varInt : data) {
            writeVarInt(varInt);
        }
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.PARTICLE;
    }
}
