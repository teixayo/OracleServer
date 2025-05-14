package me.teixayo.server.protocol.packet.server.play;

import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;

public class SetExperiencePacket extends ServerPacket {

    private final float experienceBar;
    private final int level;
    private final int totalExperience;

    public SetExperiencePacket(float experienceBar, int level, int totalExperience) {
        this.experienceBar = experienceBar;
        this.level = level;
        this.totalExperience = totalExperience;
    }

    @Override
    public void write() {
        writeFloat(experienceBar);
        writeVarInt(level);
        writeVarInt(totalExperience);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.SET_EXPERIENCE;
    }
}
