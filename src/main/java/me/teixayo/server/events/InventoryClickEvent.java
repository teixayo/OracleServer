package me.teixayo.server.events;

import me.teixayo.server.entity.player.Player;
import me.teixayo.server.inventory.Inventory;

public class InventoryClickEvent {

    private final Player player;
    private final Inventory inventory;
    private final int slot;
    private boolean cancelled;


    public InventoryClickEvent(Player player, Inventory inventory, int slot) {
        this.player = player;
        this.inventory = inventory;
        this.slot = slot;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public Player getPlayer() {
        return player;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getSlot() {
        return slot;
    }
}
