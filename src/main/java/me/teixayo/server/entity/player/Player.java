package me.teixayo.server.entity.player;

import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;
import me.teixayo.server.Server;
import me.teixayo.server.chunk.Chunk;
import me.teixayo.server.chunk.World;
import me.teixayo.server.entity.Entity;
import me.teixayo.server.events.PlayerDisconnectEvent;
import me.teixayo.server.inventory.Inventory;
import me.teixayo.server.item.ItemStack;
import me.teixayo.server.math.Location;
import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.play.*;
import me.teixayo.server.protocol.packet.server.play.chunk.ChunkDataPacket;
import me.teixayo.server.protocol.packet.server.play.window.SetSlotPacket;
import me.teixayo.server.visual.ChatColor;

import java.util.UUID;

public class Player extends Entity {

    @Getter
    private final Location location = new Location();
    private final UUID uuid;
    @Getter
    private final String name;
    @Getter
    private final PlayerConnection playerConnection;
    private final long joinedTime;
    private float health = 20;
    private int food = 20;
    private float foodSaturation = 0.0F;
    @Setter
    @Getter
    private boolean onGround;
    @Getter
    private GameMode gameMode = GameMode.Creative;
    @Getter
    @Setter
    private Inventory currentInventory;
    @Getter
    private Inventory playerInventory;

    public Player(UUID uuid, String name, ChannelHandlerContext channel) {
        this.uuid = uuid;
        this.name = name;
        this.playerConnection = new PlayerConnection(channel, this);
        this.joinedTime = System.nanoTime();
    }

    public void openInventory(Inventory inventory) {
        this.currentInventory = inventory;
        inventory.open(this);
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
        ChangeGameStatePacket changeGameStatePacket = new ChangeGameStatePacket(gameMode);
        sendPacket(changeGameStatePacket);
    }

    public void setHelmet(ItemStack helmetItem) {
        SetSlotPacket setSlotPacket = new SetSlotPacket(0, 5, helmetItem);
        sendPacket(setSlotPacket);
    }

    public void setChestplate(ItemStack chestplateItem) {
        SetSlotPacket setSlotPacket = new SetSlotPacket(0, 6, chestplateItem);
        sendPacket(setSlotPacket);
    }

    public void setLeggings(ItemStack leggingsItem) {
        SetSlotPacket setSlotPacket = new SetSlotPacket(0, 7, leggingsItem);
        sendPacket(setSlotPacket);
    }

    public void setBoots(ItemStack bootsItem) {
        SetSlotPacket setSlotPacket = new SetSlotPacket(0, 8, bootsItem);
        sendPacket(setSlotPacket);
    }


    public void setHealth(float health) {
        if (this.health == health) return;
        this.health = health;
        UpdateHealthPacket packet = new UpdateHealthPacket(health, food, foodSaturation);
        sendPacket(packet);
    }

    public void setFood(int food) {
        if (this.food == food) return;
        this.food = food;
        UpdateHealthPacket packet = new UpdateHealthPacket(health, food, foodSaturation);
        sendPacket(packet);
    }

    public void setFoodAndHealth(float health, int food) {
        if (this.food == food) return;
        if (this.health == health) return;
        this.food = food;
        this.health = health;
        UpdateHealthPacket packet = new UpdateHealthPacket(health, food, foodSaturation);
        sendPacket(packet);
    }

    public void setFoodSaturation(float foodSaturation) {
        if (this.foodSaturation == foodSaturation) return;
        this.foodSaturation = foodSaturation;
        UpdateHealthPacket packet = new UpdateHealthPacket(health, food, foodSaturation);
        sendPacket(packet);
    }

    public void disconnect(String reason) {
        ServerDisconnectPacket disconnectPacket = new ServerDisconnectPacket(reason);
        playerConnection.getChannel().writeAndFlush(disconnectPacket);
        playerConnection.disconnect();

        PlayerDisconnectEvent event = new PlayerDisconnectEvent(this);
        Server.get().getSchedulerManager().sync().run(task -> Server.get().getEventManager().call(event));
        Server.get().getPlayers().remove(this);
    }

    public void setSelectionSlot(int slot) {
        HeldItemChangePacket heldItemChangePacket = new HeldItemChangePacket(slot);
        sendPacket(heldItemChangePacket);
    }

    public void setHeaderAndFooterColorized(String header, String footer) {
        setHeaderAndFooter(ChatColor.translate(header), ChatColor.translate(footer));
    }

    public void setHeaderAndFooter(String header, String footer) {
        PlayerHeaderFooterPacket packet = new PlayerHeaderFooterPacket(header, footer);
        sendPacket(packet);
    }

    public void sendMessageColorized(String message) {
        sendMessage(ChatColor.translate(message));
    }

    public void sendMessage(String message) {
        playerConnection.sendPacket(new ServerChatMessagePacket(message, (byte) 0));
    }


    public void sendActionbarColorized(String message) {
        sendActionbar(ChatColor.translate(message));
    }

    public void sendActionbar(String message) {
        playerConnection.sendPacket(new ServerChatMessagePacket(message, (byte) 2));
    }

    public void setExperience(float experienceBar, int level, int totalExperience) {
        SetExperiencePacket setExperiencePacket = new SetExperiencePacket(experienceBar, level, totalExperience);
        sendPacket(setExperiencePacket);
    }

    public void tick() {

        playerConnection.tick();
        if (System.nanoTime() - joinedTime >= 1E9) {
            Server.get().getSchedulerManager().async().run(task -> {

                World world = Server.get().getDefaultWorld();
                int x = (int) location.x >> 4;
                int z = (int) location.z >> 4;

                int minX = x - 8;
                int maxX = x + 8;
                int minZ = z - 8;
                int maxZ = z + 8;
                for (int xChunk = minX; xChunk < maxX; xChunk++) {
                    for (int zChunk = minZ; zChunk < maxZ; zChunk++) {
                        if (world.getChunk(xChunk, zChunk) == null) {
                            world.loadChunk(xChunk, zChunk);
                        }
                    }
                }

                for (int xChunk = minX; xChunk < maxX; xChunk++) {
                    for (int zChunk = minZ; zChunk < maxZ; zChunk++) {
                        if (world.getChunk(xChunk, zChunk) == null) {
                            Chunk chunk = new Chunk(xChunk, zChunk);
                            chunk.initialize();

                            sendPacket(new ChunkDataPacket(chunk, false));

                        }
                    }
                }

            });
        }

        playerConnection.processPackets();
    }

    public void sendPacket(ServerPacket packet) {
        playerConnection.sendPacket(packet);
    }


    public UUID getUniqueID() {
        return uuid;
    }

}
