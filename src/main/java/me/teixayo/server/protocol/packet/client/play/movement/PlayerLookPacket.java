package me.teixayo.server.protocol.packet.client.play.movement;

import lombok.Getter;
import me.teixayo.server.protocol.packet.client.ClientPacket;

@Getter
public class PlayerLookPacket extends ClientPacket {

    private float yaw;
    private float pitch;
    private boolean onGround;

    @Override
    public void read() {
        yaw = readFloat();
        pitch = readFloat();
        onGround = readBoolean();
    }
}
