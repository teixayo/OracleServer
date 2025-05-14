package me.teixayo.server.command;

import me.teixayo.server.entity.player.Player;

public interface CommandExecutor {
    void apply(Player sender, String[] args);
}
