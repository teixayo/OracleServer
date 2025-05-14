package me.teixayo.server.protocol.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.AttributeKey;
import me.teixayo.server.Server;
import me.teixayo.server.protocol.EnumProtocol;
import me.teixayo.server.protocol.EnumProtocolDirection;
import me.teixayo.server.protocol.packet.PacketHandler;
import me.teixayo.server.protocol.packet.client.ClientPacket;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {


    public static AttributeKey<Integer> attributeKey = AttributeKey.valueOf("protocol");

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (!in.isReadable()) return;

        int packetId = PacketDataSerializer.readVarInt(in);
        Integer state = ctx.channel().attr(attributeKey).get();

        if (state == 0 && packetId == 0x03) {
            boolean inGround = in.readBoolean();
            ctx.attr(PacketHandler.playerAttributeKey).get().setOnGround(inGround);
            return;
        }

        Class<? extends ClientPacket> packetClass = EnumProtocol.getPacket(state, EnumProtocolDirection.CLIENTBOUND, packetId);


        if (packetClass == null) {
            Server.get().getLogger().warn("Bad packet id " + packetId);
            in.readBytes(in.readableBytes());
            return;
        }
        try {
            ClientPacket packet = packetClass.newInstance();

            packet.buffer = in;
            packet.read();
            out.add(packet);
        } catch (Exception e) {
            in.readBytes(in.readableBytes());
        }
    }
}
