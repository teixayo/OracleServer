package me.teixayo.server.protocol.packet.client.play;

import lombok.Getter;
import me.teixayo.server.protocol.packet.client.ClientPacket;


@Getter
public class CloseWindowPacket extends ClientPacket {
    private byte windowID;
    /*clients send a Close Window packet with Window ID 0 to close their inventory even though there is never an Open Window packet for the inventory.*/

    @Override
    public void read() {
        windowID = readByte();
    }
}
