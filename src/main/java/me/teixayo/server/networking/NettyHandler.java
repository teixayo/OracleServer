package me.teixayo.server.networking;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import lombok.Getter;
import me.teixayo.server.protocol.packet.PacketListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class NettyHandler {

    private static final Logger LOGGER = LogManager.getLogger("core");
    private static final WriteBufferWaterMark SERVER_WRITE_MARK = new WriteBufferWaterMark(1 << 20,
            1 << 21);
    @Getter
    private final List<PacketListener> listenerList = new ArrayList<>();
    public int threads = Runtime.getRuntime().availableProcessors();
    private EventLoopGroup workerGroup;
    private final ServerChannelInitializer serverChannelInitializer;

    public NettyHandler(String address, int port) {
        TransportType transportType = TransportType.bestTransportType();
        serverChannelInitializer = new ServerChannelInitializer();
        LOGGER.info("Using " + threads + " threads for Netty based " + transportType.name());
        new Thread(() -> {

            workerGroup = transportType.eventLoopGroupCreator.create();

            ServerBootstrap bootstrap = new ServerBootstrap()
                    .channelFactory(transportType.serverSocketCreator)
                    .group(workerGroup)
                    .childHandler(serverChannelInitializer)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, SERVER_WRITE_MARK)
                    .childOption(ChannelOption.IP_TOS, 0x18)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.TCP_FASTOPEN, 3)
                    .localAddress(new InetSocketAddress(address, port));

            try {
                bootstrap.bind()
                        .addListener((ChannelFutureListener) future -> {
                            Channel channel = future.channel();
                            if (future.isSuccess()) {
                                LOGGER.info("Listening on {}", channel.localAddress());
                                return;
                            }
                            LOGGER.error("Can't bind to {}", address, future.cause());
                        }).sync().channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            workerGroup.shutdownGracefully();
        }).start();
    }

    public void addListener(PacketListener listener) {
        listenerList.add(listener);
    }

}
