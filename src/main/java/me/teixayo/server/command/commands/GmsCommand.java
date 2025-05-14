package me.teixayo.server.command.commands;

import me.teixayo.server.command.Command;
import me.teixayo.server.command.CommandExecutor;
import me.teixayo.server.entity.player.GameMode;
import me.teixayo.server.entity.player.Player;

public class GmsCommand extends Command implements CommandExecutor {

    public GmsCommand() {
        super("gms", "survival");
        setCommandExecuter(this);
    }

    @Override
    public void apply(Player sender, String[] args) {
        sender.setGameMode(GameMode.Survival);
    }
}
