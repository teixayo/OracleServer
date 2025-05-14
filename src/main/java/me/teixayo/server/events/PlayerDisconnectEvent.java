package me.teixayo.server.events;

import me.teixayo.server.entity.player.Player;

public class PlayerDisconnectEvent {

    private final Player player;

    public PlayerDisconnectEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
