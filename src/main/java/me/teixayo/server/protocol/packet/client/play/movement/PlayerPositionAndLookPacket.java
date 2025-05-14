package me.teixayo.server.protocol.packet.client.play.movement;

import lombok.Getter;
import me.teixayo.server.protocol.packet.client.ClientPacket;

@Getter
public class PlayerPositionAndLookPacket extends ClientPacket {


    private double x, feetY, z;

    private float yaw;
    private float pitch;
    private boolean onGround;

    @Override
    public void read() {
        x = readDouble();
        feetY = readDouble();
        z = readDouble();
        yaw = readFloat();
        pitch = readFloat();
        onGround = readBoolean();
    }
}
