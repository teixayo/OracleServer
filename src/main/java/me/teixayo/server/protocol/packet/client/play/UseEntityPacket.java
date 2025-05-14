package me.teixayo.server.protocol.packet.client.play;

import lombok.Getter;
import me.teixayo.server.protocol.packet.client.ClientPacket;

@Getter
public class UseEntityPacket extends ClientPacket {

    private int target;
    private int type;
    private float targetX;
    private float targetY;
    private float targetZ;

    @Override
    public void read() {
        target = readVarInt();
        type = readVarInt();
        if (buffer.isReadable()) {
            targetX = readFloat();
            targetY = readFloat();
            targetZ = readFloat();
        }
    }
}
