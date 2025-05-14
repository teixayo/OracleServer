package me.teixayo.server.events;

import me.teixayo.server.entity.player.Player;
import me.teixayo.server.inventory.Inventory;

public class InventoryCloseEvent {

    private final Player player;
    private final Inventory inventory;

    public InventoryCloseEvent(Player player, Inventory inventory) {
        this.player = player;
        this.inventory = inventory;
    }

    public Player getPlayer() {
        return player;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
