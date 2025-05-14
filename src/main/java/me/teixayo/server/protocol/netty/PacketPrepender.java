package me.teixayo.server.protocol.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.HashMap;
import java.util.List;

@ChannelHandler.Sharable
public class PacketPrepender extends MessageToMessageEncoder<ByteBuf> {

    private static final HashMap<Channel, Long> usedBandwidth = new HashMap<>();

    public static long getUsedBandwidth(Channel channel) {
        return usedBandwidth.getOrDefault(channel, 0L);
    }

    public static void writeVarInt(ByteBuf buf, int value) {
        while (true) {
            if ((value & 0xFFFFFF80) == 0) {
                buf.writeByte(value);
                return;
            }
            buf.writeByte(value & 0x7F | 0x80);
            value >>>= 7;
        }
    }

    protected void encode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        ByteBuf lengthBuf = ctx.alloc().buffer(5);
        writeVarInt(lengthBuf, in.readableBytes());


        Channel channel = ctx.channel();

        Long aLong = usedBandwidth.get(channel);
        if (aLong == null) {
            usedBandwidth.put(channel, (long) (5 + in.readableBytes()));
        } else {
            usedBandwidth.replace(channel, aLong + (long) (5 + in.readableBytes()));
        }

        out.add(lengthBuf);
        out.add(in.retain());
    }
}
