package me.teixayo.server.command.commands;

import me.teixayo.server.command.Command;
import me.teixayo.server.command.CommandExecutor;
import me.teixayo.server.entity.DataWatcher;
import me.teixayo.server.entity.player.GameMode;
import me.teixayo.server.entity.player.Player;
import me.teixayo.server.math.Location;
import me.teixayo.server.protocol.packet.server.play.PlayerListItemPacket;
import me.teixayo.server.protocol.packet.server.play.SpawnPlayerPacket;

import java.util.Collections;
import java.util.UUID;

public class NPCCommand extends Command implements CommandExecutor {
    public NPCCommand() {
        super("npc");
        setCommandExecuter(this);
    }

    @Override
    public void apply(Player sender, String[] args) {
        UUID uuid = UUID.randomUUID();

        Player player = new Player(uuid, "test", null);
        player.setGameMode(GameMode.Survival);

        PlayerListItemPacket playerListItemPacket = new PlayerListItemPacket(PlayerListItemPacket.EnumPlayerInfoAction.ADD_PLAYER, Collections.singletonList(player));
        sender.sendPacket(playerListItemPacket);

        Location location = sender.getLocation();

        DataWatcher dataWatcher = new DataWatcher();
        dataWatcher.addWatchableObject(0, (byte) 0);
        dataWatcher.addWatchableObject(1, (short) 300);
        dataWatcher.addWatchableObject(2, "");
        dataWatcher.addWatchableObject(3, (byte) 0);
        dataWatcher.addWatchableObject(4, (byte) 0);
        SpawnPlayerPacket spawnPlayerPacket = new SpawnPlayerPacket(5, uuid, location.x, location.y, location.z, location.yaw, location.pitch, (short) 0, dataWatcher);
        sender.sendPacket(spawnPlayerPacket);
    }
}
