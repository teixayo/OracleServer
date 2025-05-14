package me.teixayo.server.protocol.packet.server.play;

import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;

public class PlayerPositionAndLockPacket extends ServerPacket {
    private final double x;
    private final double y;
    private final double z;
    private final float pitch;
    private final float yaw;
    private final byte flags;

    public PlayerPositionAndLockPacket(double x, double y, double z, float pitch, float yaw, byte flags) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
        this.flags = flags;
    }

    @Override
    public void write() {
        writeDouble(x);
        writeDouble(y);
        writeDouble(z);
        writeFloat(pitch);
        writeFloat(yaw);
        writeByte(flags);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.PLAYER_POSITION_AND_LOCK;
    }
}
