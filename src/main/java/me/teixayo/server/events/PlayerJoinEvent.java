package me.teixayo.server.events;

import lombok.Getter;
import me.teixayo.server.entity.player.Player;

@Getter
public class PlayerJoinEvent {

    private static PlayerJoinEvent instance = null;
    private Player player;

    private PlayerJoinEvent(Player player) {
        this.player = player;
    }

    public static PlayerJoinEvent newInstance(Player player) {
        if (instance == null) {
            return instance = new PlayerJoinEvent(player);
        }
        instance.setPlayer(player);
        return instance;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
