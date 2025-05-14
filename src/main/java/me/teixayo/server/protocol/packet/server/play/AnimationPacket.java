package me.teixayo.server.protocol.packet.server.play;

import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;

public class AnimationPacket extends ServerPacket {

    private final int entityID;
    private final byte animation;
    /*  0	Swing arm
        1	Take damage
        2	Leave bed
        3	Eat food
        4	Critical effect
        5	Magic critical effect
     */

    public AnimationPacket(int entityID, byte animation) {
        this.entityID = entityID;
        this.animation = animation;
    }

    @Override
    public void write() {
        writeVarInt(entityID);
        writeByte(animation);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.ANIMATION;
    }
}
