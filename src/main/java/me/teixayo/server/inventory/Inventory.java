package me.teixayo.server.inventory;

import lombok.Setter;
import me.teixayo.server.Server;
import me.teixayo.server.entity.player.Player;
import me.teixayo.server.events.InventoryClickEvent;
import me.teixayo.server.events.InventoryCloseEvent;
import me.teixayo.server.events.InventoryOpenEvent;
import me.teixayo.server.item.ItemStack;
import me.teixayo.server.protocol.packet.server.play.window.OpenWindowPacket;
import me.teixayo.server.protocol.packet.server.play.window.SetMultiSlotPacket;
import me.teixayo.server.protocol.packet.server.play.window.SetSlotPacket;
import me.teixayo.server.protocol.packet.server.play.window.WindowType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Inventory {

    private final ItemStack[] items;
    private final WindowType windowType;
    private final int size;

    private final List<Player> viewers;

    @Setter
    private Consumer<Player> clickAction;

    public Inventory(WindowType windowType, int size) {
        this.windowType = windowType;
        this.size = size;
        this.items = new ItemStack[size];
        this.viewers = new ArrayList<>();

    }

    public void setItem(int index, ItemStack itemStack) {
        items[index] = itemStack;
    }

    public ItemStack getItem(int index) {
        return items[index];
    }


    public void open(Player player) {

        viewers.add(player);
        OpenWindowPacket openWindowPacket = new OpenWindowPacket(1, WindowType.CHEST, "Test", (byte) 36);
        player.sendPacket(openWindowPacket);
        SetMultiSlotPacket setMultiSlotPacket = new SetMultiSlotPacket(1, items);
        player.sendPacket(setMultiSlotPacket);
        SetSlotPacket setSlotPacket = new SetSlotPacket(-1, -1, null);
        player.sendPacket(setSlotPacket);


        InventoryOpenEvent event = new InventoryOpenEvent(player, this);
        Server.get().getSchedulerManager().sync().run(task -> {
            Server.get().getEventManager().call(event);
        });
    }

    public void update(Player player) {
        SetMultiSlotPacket setMultiSlotPacket = new SetMultiSlotPacket(1, items);
        player.sendPacket(setMultiSlotPacket);
        SetSlotPacket setSlotPacket = new SetSlotPacket(-1, -1, null);
        player.sendPacket(setSlotPacket);
    }

    public void close(Player player) {
        viewers.remove(player);
        InventoryCloseEvent event = new InventoryCloseEvent(player, this);
        Server.get().getSchedulerManager().sync().run(task -> {
            Server.get().getEventManager().call(event);
        });
    }

    public void click(Player player, int slot) {
        InventoryClickEvent event = new InventoryClickEvent(player, this, slot);

        Server.get().getSchedulerManager().sync().run(task -> {
            Server.get().getEventManager().call(event);
            if (event.isCancelled()) return;
            if (clickAction == null) return;
            clickAction.accept(player);
        });
    }
}
