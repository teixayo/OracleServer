package me.teixayo.server.listener;

import me.teixayo.server.Server;
import me.teixayo.server.chunk.Position;
import me.teixayo.server.entity.player.Player;
import me.teixayo.server.event.EventHandler;
import me.teixayo.server.events.*;
import me.teixayo.server.math.Location;
import me.teixayo.server.protocol.packet.server.play.BlockChangePacket;
import me.teixayo.server.protocol.packet.server.play.ParticlePacket;
import me.teixayo.server.protocol.packet.server.play.ParticleType;

import java.net.InetSocketAddress;

public class InfoListener {


    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent event) {
        InetSocketAddress socketAddress = (InetSocketAddress) event.getPlayer().getPlayerConnection().getChannel().channel().remoteAddress();
        Server.get().getLogger().info(event.getPlayer().getName() + "[" + socketAddress.getHostName() + ":" + socketAddress.getPort() + "] disconnected.");
    }

    @EventHandler
    public void onChat(PlayerChatEvent event) {

        Server.get().getLogger().info(event.getPlayer().getName() + " > " + event.getMessage());
        if (event.getMessage().startsWith("/")) return;

        for (Player player : Server.get().getPlayers()) {
            player.sendMessageColorized(event.getPlayer().getName() + " > " + event.getMessage());
        }

    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        event.setCanceled(true);
    }

    @EventHandler
    public void onOpenInventory(InventoryOpenEvent event) {
        Server.get().getLogger().info(event.getPlayer().getName() + " has opened inventory");
    }

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent event) {
        Server.get().getLogger().info(event.getPlayer().getName() + " has closed inventory");
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent event) {
        Server.get().getLogger().info("The slot " + event.getSlot() + " of inventory has been clicked by " + event.getPlayer().getName());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        InetSocketAddress socketAddress = (InetSocketAddress) event.getPlayer().getPlayerConnection().getChannel().channel().remoteAddress();
        Server.get().getLogger().info(event.getPlayer().getName() + "[" + socketAddress.getHostName() + ":" + socketAddress.getPort() + "] logged.");

        Player player = event.getPlayer();

        Server.get().getSchedulerManager().async().runTimer(schedulerTask -> {
            Location location = player.getLocation();

//            ParticlePool particlePool = new ParticlePool(80);
            for (float y = 0; y < 2; y += 0.1f) {
                for (int i = 0; i < 360; i += 90) {
                    float x = (float) (Math.sin(Math.toRadians(System.currentTimeMillis() / ((1000.0 / 90.0f))) + i) * 2.0);
                    float z = (float) (Math.cos(Math.toRadians(System.currentTimeMillis() / ((1000.0 / 90.0))) + i) * 2.0);
//                    particlePool.addParticle(ParticleType.FLAME, false, (float) location.x + x, (float) location.y + y, (float) location.z + z, 0.0f, 0.0f, 0.0f, 0.0f, 1);
                    player.sendPacket(new ParticlePacket(ParticleType.FLAME, false, (float) location.x + x, (float) location.y + y, (float) location.z + z, 0.0f, 0.0f, 0.0f, 0.0f, 1, null));
                }
            }
//            particlePool.write(player.getPlayerConnection().getChannel());

        }, 1, 1);

//    }


//        for (Player playerOnServer : Server.get().getPlayers()) {
//            if (playerOnServer.equals(player)) continue;
//
//            UUID uuid = player.getUuid();
//            playerOnServer.sendPacket(new PacketOutPlayerInfo(uuid, player.getName()));
//        }


//        PacketOutParticle packet = new PacketOutParticle(26, false, 0.0f,0.0f,0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1);
//

//        AtomicInteger entityID = new AtomicInteger();
        Server.get().getSchedulerManager().async().runTimer(schedulerTask -> {
            Location location = player.getLocation();
            BlockChangePacket packet1 = new BlockChangePacket(new Position((int) location.x, (int) location.y, (int) location.z), 57);
            player.sendPacket(packet1);
        }, 1, 1);
//        Consumer<SchedulerTask> taskConsumer = new Consumer<>() {
//            byte slot;
//
//            @Override
//            public void accept(SchedulerTask schedulerTask) {
//                if(slot==9) {
//                    slot=0;
//                }
//                player.sendPacket(new PacketOutHeldItemChange(slot));
//                slot++;
//            }
//        };
//        Server.get().getSchedulerManager().async().runTimer(taskConsumer,1,1);


//        Consumer<SchedulerTask> healthUpdater = new Consumer<>() {
//
//            int a = 0;
//            @Override
//            public void accept(SchedulerTask task) {
//                player.setFood(a);
//                a++;
//                if(a==20) {
//                    a = 0;
//                }
//            }
//        };
//
//        Server.get().getSchedulerManager().async().runTimer(healthUpdater,1,1);
//
//        Server.get().getSchedulerManager().async().runTimer(schedulerTask -> {
//            PacketOutEntityVelocity velocity = new PacketOutEntityVelocity(1, (short) 0, (short) 8000, (short) 0);
//            player.sendPacket(velocity);
//        },40,40);
//
//        Server.get().getSchedulerManager().async().runTimer(task1 -> {
//            player.setHeaderAndFooterColorized("&b&lSuper Lightweight Server", ChatColor.random() + "&lMade by teixayo" +
//                    ChatColor.random() + "\n&lMade by teixayo" +
//                    ChatColor.random() + "\n&lMade by teixayo" +
//                    ChatColor.random() + "\n&lMade by teixayo" +
//                    ChatColor.random() + "\n&lMade by teixayo" +
//                    ChatColor.random() + "\n&lMade by teixayo" +
//                    ChatColor.random() + "\n&lMade by teixayo" +
//                    ChatColor.random() + "\n&lMade by teixayo" +
//                    ChatColor.random() + "\n&lMade by teixayo" +
//                    ChatColor.random() + "\n&lMade by teixayo" +
//                    ChatColor.random() + "\n&lMade by teixayo" +
//                    ChatColor.random() + "\n&lMade by teixayo" +
//                    ChatColor.random() + "\n&lMade by teixayo" +
//                    ChatColor.random() + "\n&lMade by teixayo" +
//                    ChatColor.random() + "\n&lMade by teixayo" +
//                    ChatColor.random() + "\n&lMade by teixayo" +
//                    ChatColor.random() + "\n&lMade by teixayo" +
//                    ChatColor.random() + "\n&lMade by teixayo"
//            );
//        }, 1, 1);
//
//    }
//        Server.get().getSchedulerManager().async().runTimer(task2 -> {
//
//            DecimalFormat format = new DecimalFormat("0.00");
//
//            long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
//            double present = (double) usedMemory / Runtime.getRuntime().totalMemory() * 100.0;
//
//
//            String message = "&eTPS: &a" + format.format(Server.get().getCurrentTPS()) +
//                    "&7, &eMSPT: &a" + format.format(Server.get().getCurrentMSPT()) +
//                    "&7, &eRAM: &a" + format.format(usedMemory / 1048576.0) + "&7/&a" + format.format(Runtime.getRuntime().totalMemory() / 1048576.0) + " MB&7, &a" + format.format(present) + "%" +
//                    "&7, &eCPU: &a" + format.format(Server.get().getCpuUsage()) + "%" +
//                    "&7, &ePING: &a " + format.format(PacketInKeepAlive.ping) + " MS";
//
//            player.sendActionbarColorized(message);
//
//            player.sendMessage("used bandwidth: " + format.format(PacketPrepender.getUsedBandwidth(player.getPlayerConnection().getChannel()) / (1024.0f * 1024.0f)) + " MB");
//        }, 5, 5);

//        Server.get().getSchedulerManager().async().runLater(schedulerTask -> {
//            PacketOutSpawnMob packet1 = new PacketOutSpawnMob(Entity.ENTITY_ID++, (short) 53,0,0,0, (byte) 0, (byte) 0, (byte) 0, (short) 0, (short) 0,(short) 0);
//            player.sendPacket(packet1);
//        },20);
    }
}
