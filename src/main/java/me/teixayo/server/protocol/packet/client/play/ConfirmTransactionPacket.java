package me.teixayo.server.protocol.packet.client.play;

import lombok.Getter;
import me.teixayo.server.protocol.packet.client.ClientPacket;


@Getter
public class ConfirmTransactionPacket extends ClientPacket {
    private byte windowID;
    private short actionNumber;
    private boolean accepted;

    @Override
    public void read() {
        windowID = readByte();
        actionNumber = readShort();
        accepted = readBoolean();
    }
}
