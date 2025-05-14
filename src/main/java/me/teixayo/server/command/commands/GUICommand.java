package me.teixayo.server.command.commands;

import me.teixayo.server.command.Command;
import me.teixayo.server.command.CommandExecutor;
import me.teixayo.server.entity.player.Player;
import me.teixayo.server.inventory.Inventory;
import me.teixayo.server.item.ItemStack;
import me.teixayo.server.item.Material;
import me.teixayo.server.protocol.packet.server.play.window.WindowType;

public class GUICommand extends Command implements CommandExecutor {
    public GUICommand() {
        super("gui");
        setCommandExecuter(this);
    }

    @Override
    public void apply(Player sender, String[] args) {
        Inventory inventory = new Inventory(WindowType.CHEST, 54);

        inventory.setItem(0, ItemStack.builder(Material.DIAMOND_PICKAXE)
                .build());
        sender.openInventory(inventory);
    }
}
