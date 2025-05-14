package me.teixayo.server.command;

import me.teixayo.server.entity.player.Player;

public interface TabCompleter {
    String[] tabComplete(Player sender, String[] args);
}
