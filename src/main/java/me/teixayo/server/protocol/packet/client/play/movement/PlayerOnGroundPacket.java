package me.teixayo.server.protocol.packet.client.play.movement;

import lombok.Getter;
import me.teixayo.server.protocol.packet.client.ClientPacket;

@Getter
public class PlayerOnGroundPacket extends ClientPacket {

    private boolean onGround;


    @Override
    public void read() {
        this.onGround = readBoolean();
    }
}
