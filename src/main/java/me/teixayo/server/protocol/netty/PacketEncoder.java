package me.teixayo.server.protocol.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.teixayo.server.protocol.packet.server.ServerPacket;

public class PacketEncoder extends MessageToByteEncoder<ServerPacket> {


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ServerPacket packet, ByteBuf byteBuf) {

        int packetId = packet.getId();
        PacketDataSerializer.writeVarInt(byteBuf, packetId);

        packet.buffer = byteBuf;
        packet.write();

    }
}
