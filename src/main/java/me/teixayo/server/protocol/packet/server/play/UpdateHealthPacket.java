package me.teixayo.server.protocol.packet.server.play;

import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;

public class UpdateHealthPacket extends ServerPacket {

    private final float health;
    private final int food;
    private final float foodSaturation;
    /*Seems to vary from 0.0 to 5.0 in integer increments*/

    public UpdateHealthPacket(float health, int food, float foodSaturation) {
        this.health = health;
        this.food = food;
        this.foodSaturation = foodSaturation;
    }

    @Override
    public void write() {
        writeFloat(health);
        writeVarInt(food);
        writeFloat(foodSaturation);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.UPDATE_HEALTH;
    }
}
