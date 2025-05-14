package me.teixayo.server.protocol.packet;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import me.teixayo.server.Server;
import me.teixayo.server.chunk.*;
import me.teixayo.server.entity.DataWatcher;
import me.teixayo.server.entity.player.GameMode;
import me.teixayo.server.entity.player.Player;
import me.teixayo.server.events.PlayerJoinEvent;
import me.teixayo.server.math.Location;
import me.teixayo.server.protocol.netty.PacketCompressor;
import me.teixayo.server.protocol.netty.PacketDecoder;
import me.teixayo.server.protocol.netty.PacketDecompressor;
import me.teixayo.server.protocol.packet.client.ClientPacket;
import me.teixayo.server.protocol.packet.client.handshake.ClientHandshakePacket;
import me.teixayo.server.protocol.packet.client.login.ClientLoginStartPacket;
import me.teixayo.server.protocol.packet.client.status.StatusPingPacket;
import me.teixayo.server.protocol.packet.client.status.StatusRequestPacket;
import me.teixayo.server.protocol.packet.server.login.LoginSuccessPacket;
import me.teixayo.server.protocol.packet.server.login.SetCompressionPacket;
import me.teixayo.server.protocol.packet.server.play.*;
import me.teixayo.server.protocol.packet.server.play.chunk.ChunkDataPacket;
import me.teixayo.server.protocol.packet.server.status.StatusPongPacket;
import me.teixayo.server.protocol.packet.server.status.StatusResponsePacket;

import java.util.Collections;
import java.util.UUID;


@ChannelHandler.Sharable
public class PacketHandler extends SimpleChannelInboundHandler<ClientPacket> {

    public static AttributeKey<Player> playerAttributeKey = AttributeKey.valueOf("player");
    private final int compressionThreshold = 255;

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ClientPacket packet) {

        for (PacketListener packetListener : Server.get().getNettyHandler().getListenerList()) {
            if (!packetListener.read(channelHandlerContext, packet)) return;
        }

        if (packet instanceof ClientHandshakePacket packetHandshake) {
            if (packetHandshake.getStatus() == 1) {


                channelHandlerContext.attr(PacketDecoder.attributeKey).set(1);
                channelHandlerContext.channel().config().setAutoRead(true);
            } else if (packetHandshake.getStatus() == 2) {
                channelHandlerContext.attr(PacketDecoder.attributeKey).set(2);
                channelHandlerContext.channel().config().setAutoRead(true);
            }
        }

        if (packet instanceof StatusRequestPacket) {
            StatusResponsePacket responsePacket = new StatusResponsePacket("Server", 47, 10, 0, ":D");
            channelHandlerContext.writeAndFlush(responsePacket);
        }
        if (packet instanceof StatusPingPacket statusPingPacket) {
            StatusPongPacket pongPacket = new StatusPongPacket(statusPingPacket.getPayLoad());
            channelHandlerContext.writeAndFlush(pongPacket);
        }

        if (packet instanceof ClientLoginStartPacket clientLoginStartPacket) {

            if (compressionThreshold != -1) {
                SetCompressionPacket packetOutSetCompression = new SetCompressionPacket(compressionThreshold);
                channelHandlerContext.writeAndFlush(packetOutSetCompression);
                channelHandlerContext.pipeline().addBefore("decoder", "decompress", new PacketDecompressor(compressionThreshold))
                        .addBefore("encoder", "compress", new PacketCompressor(compressionThreshold));
            }

            UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + clientLoginStartPacket.getUsername()).getBytes());
            LoginSuccessPacket loginSuccessPacket = new LoginSuccessPacket(uuid, clientLoginStartPacket.getUsername());
            channelHandlerContext.writeAndFlush(loginSuccessPacket);

            Player player = new Player(uuid, clientLoginStartPacket.getUsername(), channelHandlerContext);

            Server.get().addPlayer(player);

            channelHandlerContext.attr(playerAttributeKey).set(player);

            Server.get().getSchedulerManager().sync().run(task -> {
                PlayerJoinEvent playerJoinEvent = PlayerJoinEvent.newInstance(player);
                Server.get().getEventManager().call(playerJoinEvent);
            });

            channelHandlerContext.attr(PacketDecoder.attributeKey).set(0);
            channelHandlerContext.channel().config().setAutoRead(true);


            JoinGamePacket joinPacket = new JoinGamePacket(player.getEntityId(), GameMode.Creative, Dimension.Overworld, Difficulty.Peaceful,
                    100, LevelType.Flat, false);
            player.sendPacket(joinPacket);
            SpawnPositionPacket spawnPositionPacket = new SpawnPositionPacket(new Position(0, 100, 0));
            player.sendPacket(spawnPositionPacket);
            PlayerPositionAndLockPacket packetPositionLock = new PlayerPositionAndLockPacket(0.0, 100.0, 0.0, 0.0f, 0.0f, (byte) 0);
            player.sendPacket(packetPositionLock);


            Chunk emptyChunk = new Chunk(0, 0);
            emptyChunk.initialize();
            player.sendPacket(new ChunkDataPacket(emptyChunk, false));

            PlayerListItemPacket playerListItemPacket = new PlayerListItemPacket(PlayerListItemPacket.EnumPlayerInfoAction.ADD_PLAYER, Collections.singletonList(player));


            DataWatcher dataWatcher = new DataWatcher();
            dataWatcher.addWatchableObject(0, (byte) 0);
            dataWatcher.addWatchableObject(1, (short) 300);
            dataWatcher.addWatchableObject(2, "");
            dataWatcher.addWatchableObject(3, (byte) 0);
            dataWatcher.addWatchableObject(4, (byte) 0);
            SpawnPlayerPacket spawnPlayerPacket = new SpawnPlayerPacket(player.getEntityId(), uuid, 0, 100, 0, 0.0f, 0.0f, (short) 0, dataWatcher);

            for (Player serverPlayers : Server.get().getPlayers()) {
                serverPlayers.sendPacket(playerListItemPacket);

                if (!player.equals(serverPlayers)) {
                    serverPlayers.sendPacket(spawnPlayerPacket);

                    PlayerListItemPacket serverPlayerListItemPacket = new PlayerListItemPacket(PlayerListItemPacket.EnumPlayerInfoAction.ADD_PLAYER, Collections.singletonList(serverPlayers));

                    Location location = serverPlayers.getLocation();

                    SpawnPlayerPacket serverPlayerspawnPlayerPacket = new SpawnPlayerPacket(serverPlayers.getEntityId(), uuid, location.x, location.y, location.z, location.yaw, location.pitch, (short) 0, dataWatcher);
                    player.sendPacket(serverPlayerListItemPacket);
                    player.sendPacket(serverPlayerspawnPlayerPacket);
                }
            }
        }

        if (channelHandlerContext.attr(PacketDecoder.attributeKey).get() == 0) {
            Player player = channelHandlerContext.attr(playerAttributeKey).get();
            player.getPlayerConnection().handleEncodePacket(packet);
        }
    }


}
