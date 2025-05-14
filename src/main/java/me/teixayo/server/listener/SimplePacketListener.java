package me.teixayo.server.listener;

import io.netty.channel.ChannelHandlerContext;
import me.teixayo.server.protocol.packet.PacketListener;
import me.teixayo.server.protocol.packet.client.ClientPacket;
import me.teixayo.server.protocol.packet.server.ServerPacket;

public class SimplePacketListener implements PacketListener {

    public static int packetWriteCounterPerSecond = 0;
    public static int packetReadCounterPerSecond = 0;

    private int packetWriteCounter;
    private int packetReadCounter;
    private long timer = System.currentTimeMillis();

    @Override
    public boolean write(ChannelHandlerContext ctx, ServerPacket packet) {
        packetWriteCounter++;
        if (System.currentTimeMillis() - timer >= 1000) {
            packetWriteCounterPerSecond = packetWriteCounter;
            packetReadCounterPerSecond = packetReadCounter;
            packetReadCounter = 0;
            packetWriteCounter = 0;
            timer = System.currentTimeMillis();
        }
        return true;
    }

    @Override
    public boolean read(ChannelHandlerContext ctx, ClientPacket packet) {
        packetReadCounter++;
        if (System.currentTimeMillis() - timer >= 1000) {
            packetWriteCounterPerSecond = packetWriteCounter;
            packetReadCounterPerSecond = packetReadCounter;
            packetReadCounter = 0;
            packetWriteCounter = 0;
            timer = System.currentTimeMillis();
        }
        return true;
    }
}
