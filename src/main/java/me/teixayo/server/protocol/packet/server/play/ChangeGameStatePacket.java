package me.teixayo.server.protocol.packet.server.play;

import me.teixayo.server.entity.player.GameMode;
import me.teixayo.server.protocol.packet.server.ServerPacket;


public class ChangeGameStatePacket extends ServerPacket {
    private final GameMode gameMode;

    public ChangeGameStatePacket(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    @Override
    public void write() {
        writeByte((byte) 3);
        writeFloat(gameMode.getData());
    }

    @Override
    public int getId() {
        return 0x2B;
    }
}
