package me.teixayo.server.networking;

import io.netty.channel.ChannelFactory;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.kqueue.KQueue;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.ThreadFactory;

public enum TransportType {
    EPOLL(EpollServerSocketChannel::new, () -> new EpollEventLoopGroup(0, createThreadFactory("Epoll"))),
    KQUEUE(KQueueServerSocketChannel::new, () -> new KQueueEventLoopGroup(0, createThreadFactory("KQueue"))),
    NIO(NioServerSocketChannel::new, () -> new NioEventLoopGroup(0, createThreadFactory("NIO")));

    final ChannelFactory<? extends ServerSocketChannel> serverSocketCreator;
    final Creator<? extends EventLoopGroup> eventLoopGroupCreator;

    TransportType(ChannelFactory<? extends ServerSocketChannel> serverSocketCreator, Creator<? extends EventLoopGroup> eventLoopGroupCreator) {
        this.serverSocketCreator = serverSocketCreator;
        this.eventLoopGroupCreator = eventLoopGroupCreator;
    }

    private static ThreadFactory createThreadFactory(final String name) {
        return new FastNettyThreadFactory("Netty " + name + " Worker #%d");
    }

    public static TransportType bestTransportType() {
        if (Epoll.isAvailable()) return EPOLL;
        if (KQueue.isAvailable()) return KQUEUE;
        return NIO;
    }

    public interface Creator<T> {
        T create();
    }
}
