package me.teixayo.server.protocol.packet.client.play;

import lombok.Getter;
import me.teixayo.server.protocol.packet.client.ClientPacket;


@Getter
public class SteerVehiclePacket extends ClientPacket {
    private float sideways;
    private float positive;
    private byte flags;

    @Override
    public void read() {
        sideways = readFloat();
        positive = readFloat();
        flags = readByte();
    }
}
