package me.teixayo.server.protocol.packet.client.play;

import lombok.Getter;
import me.teixayo.server.protocol.packet.client.ClientPacket;


@Getter
public class ClientStatusPacket extends ClientPacket {

    private int actionID;

    /*
    0	Perform respawn
    1	Request stats
    2	Taking Inventory achievement
     */
    @Override
    public void read() {
        actionID = readVarInt();
    }
}
