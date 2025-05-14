package me.teixayo.server.events;

import lombok.Getter;
import me.teixayo.server.entity.player.Player;
import me.teixayo.server.inventory.Inventory;

public class InventoryCloseEvent {

    @Getter
    private final Player player;
    @Getter
    private final Inventory inventory;

    public InventoryCloseEvent(Player player, Inventory inventory) {
        this.player = player;
        this.inventory = inventory;
    }
}
