package me.teixayo.server.events;

import me.teixayo.server.chunk.Position;
import me.teixayo.server.entity.player.Player;

public class BlockPlaceEvent {

    private final Position position;
    private final Player player;
    private boolean canceled = false;

    public BlockPlaceEvent(Position position, Player player) {
        this.position = position;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isCanceled() {
        return canceled;
    }


    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
