package me.teixayo.server.events;

import lombok.Getter;
import me.teixayo.server.entity.player.Player;
import me.teixayo.server.inventory.Inventory;

public class InventoryClickEvent {

    @Getter
    private final Player player;
    @Getter
    private final Inventory inventory;
    @Getter
    private final int slot;
    @Getter
    private boolean cancelled;


    public InventoryClickEvent(Player player, Inventory inventory, int slot) {
        this.player = player;
        this.inventory = inventory;
        this.slot = slot;
    }
}
