package me.teixayo.server.protocol.packet.server.play;

import me.teixayo.server.chunk.Difficulty;
import me.teixayo.server.chunk.Dimension;
import me.teixayo.server.chunk.LevelType;
import me.teixayo.server.entity.player.GameMode;
import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;

public class JoinGamePacket extends ServerPacket {


    private final int entityId;
    private final GameMode gameMode;
    private final Dimension dimension;
    private final Difficulty difficulty;

    private final int maxPlayers;
    private final LevelType levelType;
    private final boolean reduceDebugInfo;

    public JoinGamePacket(int entityId, GameMode gameMode, Dimension dimension, Difficulty difficulty, int maxPlayers, LevelType levelType, boolean reduceDebugInfo) {
        this.entityId = entityId;
        this.gameMode = gameMode;
        this.dimension = dimension;
        this.difficulty = difficulty;
        this.maxPlayers = maxPlayers;
        this.levelType = levelType;
        this.reduceDebugInfo = reduceDebugInfo;
    }

    @Override
    public void write() {
        writeInt(entityId);
        writeByte(gameMode.getData());
        writeByte(dimension.getData());
        writeByte(difficulty.getData());
        writeByte((byte) maxPlayers);
        writeString(levelType.getData());
        writeBoolean(reduceDebugInfo);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.JOIN_GAME;
    }
}
