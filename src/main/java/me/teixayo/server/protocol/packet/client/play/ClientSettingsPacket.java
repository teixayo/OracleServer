package me.teixayo.server.protocol.packet.client.play;

import lombok.Getter;
import me.teixayo.server.protocol.packet.client.ClientPacket;

@Getter
public class ClientSettingsPacket extends ClientPacket {

    private String locale;
    /*en_GB*/
    private byte renderDistance;
    private byte chatMode;
    private boolean chatColors;
    private byte displayedSkinParts;
    /*
    Bit 0 (0x01): Cape enabled
    Bit 1 (0x02): Jacket enabled
    Bit 2 (0x04): Left Sleeve enabled
    Bit 3 (0x08): Right Sleeve enabled
    Bit 4 (0x10): Left Pants Leg enabled
    Bit 5 (0x20): Right Pants Leg enabled
    Bit 6 (0x40): Hat enabled
     */


    @Override
    public void read() {
        locale = readString();
        renderDistance = readByte();
        chatMode = readByte();
        chatColors = readBoolean();
        displayedSkinParts = readByte();
    }
}
