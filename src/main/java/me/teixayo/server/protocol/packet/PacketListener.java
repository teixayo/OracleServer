package me.teixayo.server.protocol.packet;

import io.netty.channel.ChannelHandlerContext;
import me.teixayo.server.protocol.packet.client.ClientPacket;
import me.teixayo.server.protocol.packet.server.ServerPacket;

public interface PacketListener {

    boolean write(ChannelHandlerContext ctx, ServerPacket packet);

    boolean read(ChannelHandlerContext ctx, ClientPacket packet);
}
