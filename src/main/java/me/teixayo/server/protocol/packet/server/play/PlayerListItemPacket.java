package me.teixayo.server.protocol.packet.server.play;

import me.teixayo.server.entity.player.Player;
import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;

import java.util.List;

public class PlayerListItemPacket extends ServerPacket {

    private final EnumPlayerInfoAction enumPlayerInfoAction;
    private final List<Player> playersData;


    public PlayerListItemPacket(EnumPlayerInfoAction enumPlayerInfoAction, List<Player> players) {
        this.enumPlayerInfoAction = enumPlayerInfoAction;
        this.playersData = players;

    }

    @Override
    public void write() {
        writeVarInt(enumPlayerInfoAction.ordinal());
        writeVarInt(playersData.size());
        for (Player player : playersData) {
            writeUUIDLong(player.getUniqueID());
            if (enumPlayerInfoAction == EnumPlayerInfoAction.ADD_PLAYER) {
                writeString(player.getName());
                writeVarInt(0);
                writeVarInt(player.getGameMode().getData());
                writeVarInt((int) player.getPlayerConnection().getPing());
                writeBoolean(true);
                String displayName = "{\"text\": \"" + player.getName() + "\"}";
                writeString(displayName);

            }
        }
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.PLAYER_LIST_ITEM;
    }

    public enum EnumPlayerInfoAction {
        ADD_PLAYER, UPDATE_GAME_MODE, UPDATE_LATENCY, UPDATE_DISPLAY_NAME, REMOVE_PLAYER
    }
}
