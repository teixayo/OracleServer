package me.teixayo.server.networking;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.ReadTimeoutHandler;
import me.teixayo.server.protocol.netty.PacketDecoder;
import me.teixayo.server.protocol.netty.PacketEncoder;
import me.teixayo.server.protocol.netty.PacketPrepender;
import me.teixayo.server.protocol.netty.PacketSplitter;
import me.teixayo.server.protocol.packet.PacketHandler;

public class ServerChannelInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) {
        channel.pipeline()
                .addLast("timeout", new ReadTimeoutHandler(30))
                .addLast("splitter", new PacketSplitter())
                .addLast("decoder", new PacketDecoder())
                .addLast("prepender", new PacketPrepender())
                .addLast("encoder", new PacketEncoder())
                .addLast("packet_handler", new PacketHandler());

        channel.attr(PacketDecoder.attributeKey).set(-1);


    }
}
