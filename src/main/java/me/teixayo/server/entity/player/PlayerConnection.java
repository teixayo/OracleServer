package me.teixayo.server.entity.player;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import me.teixayo.server.Server;
import me.teixayo.server.chunk.Chunk;
import me.teixayo.server.chunk.Position;
import me.teixayo.server.command.Command;
import me.teixayo.server.command.TabCompleter;
import me.teixayo.server.events.BlockPlaceEvent;
import me.teixayo.server.events.PlayerChatEvent;
import me.teixayo.server.item.ItemStack;
import me.teixayo.server.protocol.packet.PacketListener;
import me.teixayo.server.protocol.packet.client.ClientPacket;
import me.teixayo.server.protocol.packet.client.play.*;
import me.teixayo.server.protocol.packet.client.play.movement.PlayerLookPacket;
import me.teixayo.server.protocol.packet.client.play.movement.PlayerPositionAndLookPacket;
import me.teixayo.server.protocol.packet.client.play.movement.PlayerPositionPacket;
import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.play.BlockChangePacket;
import me.teixayo.server.protocol.packet.server.play.ServerKeepAlivePacket;
import me.teixayo.server.protocol.packet.server.play.ServerTabCompletePacket;

import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerConnection {

    @Getter
    private final ChannelHandlerContext channel;
    private final ConcurrentLinkedQueue<ServerPacket> packets = new ConcurrentLinkedQueue<>();
    private final Player player;

    @Getter
    private float ping;
    private long serverAlivePacketTime;

    @Getter
    private boolean online;

    private int ticks;
    private int lastKeepAliveID;

    public PlayerConnection(ChannelHandlerContext channel, Player player) {
        this.channel = channel;
        this.player = player;
        this.serverAlivePacketTime = System.nanoTime();
        this.online = false;
    }

    public void sendPacket(ServerPacket packet) {
        packets.add(packet);
    }

    public void disconnect() {

        Channel channel = this.channel.channel();
        channel.config().setAutoRead(false);

        if (!channel.isOpen()) {
            if (online) {
                online = false;
            } else {
                Server.get().getLogger().warn("disconnect() called twice");
            }

        }

    }

    public void tick() {
        ticks++;
        if (ticks % 300 == 0) {
            lastKeepAliveID = ThreadLocalRandom.current().nextInt();
            player.sendPacket(new ServerKeepAlivePacket(lastKeepAliveID));
            serverAlivePacketTime = System.nanoTime();
        }

        long l = System.nanoTime() - serverAlivePacketTime;
        if (l >= 30E9) {
            disconnect();
        }
    }

    public void processPackets() {
        if (channel.isRemoved()) return;
        boolean hasPacket = !packets.isEmpty();
        while (!packets.isEmpty() && channel.channel().isWritable()) {
            ServerPacket packet = packets.poll();
            for (PacketListener packetListener : Server.get().getNettyHandler().getListenerList()) {
                if (!packetListener.write(channel, packet)) return;
            }
            if (packet == null) continue;
            ChannelFuture channelFuture = channel.write(packet);
            channelFuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        }
        if (hasPacket) channel.flush();

    }

    public void handleEncodePacket(ClientPacket packet) {
        if (packet instanceof ClientTabCompletePacket clientTabCompletePacket) {
            String text = clientTabCompletePacket.getText();

            if (text.equals("/")) {
                Set<String> commandsSet = Server.get().getCommandManager().getCommandMap().keySet();
                String[] matches = new String[commandsSet.size()];
                int i = 0;
                for (String commandName : commandsSet) {
                    matches[i] = "/" + commandName;
                    i++;
                }
                ServerTabCompletePacket serverTabCompletePacket = new ServerTabCompletePacket(matches);
                player.sendPacket(serverTabCompletePacket);
                return;
            }
            if (text.startsWith("/")) {
                String[] splitMessage = text.split(" ");
                Command command = Server.get().getCommandManager().getCommand(splitMessage[0].replace("/", ""));
                if (command == null) return;
                String[] args = new String[splitMessage.length - 1];
                System.arraycopy(splitMessage, 1, args, 0, splitMessage.length - 1);

                try {
                    TabCompleter tabCompleter = command.getTabCompleter();

                    if (tabCompleter == null) return;
                    String[] matches = tabCompleter.tabComplete(player, args);
                    if (matches == null || matches.length == 0) return;
                    ServerTabCompletePacket serverTabCompletePacket = new ServerTabCompletePacket(matches);
                    player.sendPacket(serverTabCompletePacket);
                } catch (Exception exception) {
                    Server.get().getLogger().error("Error from '" + command.getName() + "' tab completer: ", exception.fillInStackTrace());
                }
                return;
            }
        }

        if (packet instanceof ClickWindowPacket clickWindowPacket) {
            if (clickWindowPacket.getWindowID() == 1) {
                if (player.getCurrentInventory() != null) {
                    player.getCurrentInventory().click(player, clickWindowPacket.getSlot());
                }
            }
        }
        if (packet instanceof CloseWindowPacket closeWindowPacket) {
            if (closeWindowPacket.getWindowID() == 1) {
                if (player.getCurrentInventory() != null) {
                    player.getCurrentInventory().close(player);
                    player.setCurrentInventory(null);
                }
            }
        }


        if (packet instanceof PlayerChatMessagePacket chat) {
            PlayerChatEvent playerChatEvent = new PlayerChatEvent(player, chat.getMessage());

            String message = playerChatEvent.getMessage();

            if (message.startsWith("/")) {
                String[] splitMessage = message.split(" ");
                Command command = Server.get().getCommandManager().getCommand(splitMessage[0].replace("/", ""));

                if (command == null) {
                    player.sendMessageColorized("&cCommand didn't found");
                    return;
                }

                String[] args = new String[splitMessage.length - 1];
                System.arraycopy(splitMessage, 1, args, 0, splitMessage.length - 1);

                try {
                    command.getCommandExecuter().apply(player, args);
                } catch (Exception exception) {
                    Server.get().getLogger().error("Error from '" + command.getName() + "' command: ", exception.fillInStackTrace());
                }
                return;
            }

            Server.get().getEventManager().call(playerChatEvent);
            if (playerChatEvent.isCancelled()) return;

        }

        if (packet instanceof PlayerKeepAlivePacket playerKeepAlivePacket) {
            if (playerKeepAlivePacket.getKeepAliveID() != lastKeepAliveID) {
                disconnect();
                return;
            }
            ping = (float) ((System.nanoTime() - serverAlivePacketTime) / 1E6);
        }

//        if(packet instanceof PlayerOnGroundPacket playerOnGroundPacket) {
//            player.setOnGround(playerOnGroundPacket.isOnGround());
//        }

        if (packet instanceof PlayerPositionPacket playerPositionPacket) {
            player.getLocation().x = playerPositionPacket.getX();
            player.getLocation().y = playerPositionPacket.getFeetY();
            player.getLocation().z = playerPositionPacket.getZ();
            player.setOnGround(playerPositionPacket.isOnGround());
        }

        if (packet instanceof PlayerLookPacket playerLookPacket) {
            player.getLocation().pitch = playerLookPacket.getPitch();
            player.getLocation().yaw = playerLookPacket.getYaw();
            player.setOnGround(playerLookPacket.isOnGround());
        }
        if (packet instanceof PlayerPositionAndLookPacket playerPositionAndLookPacket) {
            player.getLocation().x = playerPositionAndLookPacket.getX();
            player.getLocation().y = playerPositionAndLookPacket.getFeetY();
            player.getLocation().z = playerPositionAndLookPacket.getZ();
            player.getLocation().pitch = playerPositionAndLookPacket.getPitch();
            player.getLocation().yaw = playerPositionAndLookPacket.getYaw();
            player.setOnGround(playerPositionAndLookPacket.isOnGround());
        }

        if (packet instanceof PlayerBlockPlacementPacket playerBlockPlacementPacket) {
            Position position = playerBlockPlacementPacket.getLocation();
            ItemStack heldItem = playerBlockPlacementPacket.getHeldItem();

            if (!heldItem.getMaterial().isBlock()) return;

            PlayerDiggingPacket.Face face = playerBlockPlacementPacket.getFace();

            switch (face) {
                case Down -> position.setY(position.getY() - 1);
                case Up -> position.setY(position.getY() + 1);
                case Back -> position.setZ(position.getZ() - 1);
                case Front -> position.setZ(position.getZ() + 1);
                case Right -> position.setX(position.getX() - 1);
                case Left -> position.setX(position.getX() + 1);
            }

            int blockId = heldItem.getMaterial().getId();
            byte blockData = (byte) (heldItem.getDurability() & 0xf);

            int blockID = blockId << 4 | blockData;

            BlockPlaceEvent blockPlaceEvent = new BlockPlaceEvent(position, player);
            Server.get().getEventManager().call(blockPlaceEvent);
            if (!blockPlaceEvent.isCanceled()) {
                Chunk chunk = Server.get().getDefaultWorld().getChunk(position);
                if (chunk == null) {
                    chunk = Server.get().getDefaultWorld().loadEmptyChunk(position);
                }
                chunk.setBlock(position.getX(), position.getY(), position.getZ(), blockID, blockData);
            } else {
                BlockChangePacket blockChangePacket = new BlockChangePacket(position, 0);
                player.sendPacket(blockChangePacket);
            }
        }
    }
}
