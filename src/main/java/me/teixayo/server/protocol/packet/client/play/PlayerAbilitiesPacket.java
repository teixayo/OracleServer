package me.teixayo.server.protocol.packet.client.play;

import lombok.Getter;
import me.teixayo.server.protocol.packet.client.ClientPacket;

@Getter
public class PlayerAbilitiesPacket extends ClientPacket {

    private byte flags;
    /* Bit mask. 0x08: damage disabled (god mode), 0x04: can fly, 0x02: is flying, 0x01: is Creative */
    private float flyingSpeed;
    private float walkingSpeed;

    @Override
    public void read() {
        flags = readByte();
        flyingSpeed = readFloat();
        walkingSpeed = readFloat();
    }
}
