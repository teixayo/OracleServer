package me.teixayo.server.events;

import lombok.Getter;
import lombok.Setter;
import me.teixayo.server.chunk.Position;
import me.teixayo.server.entity.player.Player;

public class BlockPlaceEvent {
    @Getter
    private final Position position;
    @Getter
    private final Player player;
    @Getter
    @Setter
    private boolean canceled = false;

    public BlockPlaceEvent(Position position, Player player) {
        this.position = position;
        this.player = player;
    }
}
