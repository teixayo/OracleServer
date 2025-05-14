package me.teixayo.server.protocol.packet.client.play.movement;

import lombok.Getter;
import me.teixayo.server.protocol.packet.client.ClientPacket;

@Getter
public class PlayerPositionPacket extends ClientPacket {

    private double x, feetY, z;
    private boolean onGround;

    @Override
    public void read() {
        x = readDouble();
        feetY = readDouble();
        z = readDouble();
        onGround = readBoolean();
    }
}
