package me.teixayo.server.events;

import lombok.Getter;
import me.teixayo.server.entity.player.Player;

public class PlayerDisconnectEvent {

    @Getter
    private final Player player;

    public PlayerDisconnectEvent(Player player) {
        this.player = player;
    }

}
