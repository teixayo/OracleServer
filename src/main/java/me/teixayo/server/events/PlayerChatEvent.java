package me.teixayo.server.events;

import lombok.Getter;
import lombok.Setter;
import me.teixayo.server.entity.player.Player;

public class PlayerChatEvent {

    @Getter
    private final Player player;
    @Getter
    private final String message;
    @Getter
    @Setter
    private boolean cancelled;

    public PlayerChatEvent(Player player, String message) {
        this.player = player;
        this.message = message;
    }

}
