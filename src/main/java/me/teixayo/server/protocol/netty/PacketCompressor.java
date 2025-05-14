package me.teixayo.server.protocol.netty;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.zip.Deflater;

public class PacketCompressor extends MessageToByteEncoder<ByteBuf> {
    private final byte[] encodeBuf;

    private final Deflater deflater;


    private int threshold;

    public PacketCompressor(int compressionThreshold) {
        this.threshold = compressionThreshold;
        this.encodeBuf = new byte[8192];
        this.deflater = new Deflater();

    }

    protected void encode(ChannelHandlerContext var1, ByteBuf input, ByteBuf output) {
        int readableBytes = input.readableBytes();
        if (readableBytes < this.threshold) {
            PacketDataSerializer.writeVarInt(output, 0);
            output.writeBytes(input);
        } else {
            PacketDataSerializer.writeVarInt(output, input.readableBytes());
            this.deflater.setInput(input.nioBuffer());
            this.deflater.finish();
            while (!this.deflater.finished()) {
                int length = this.deflater.deflate(this.encodeBuf);
                output.writeBytes(this.encodeBuf, 0, length);
            }
            this.deflater.reset();
        }
    }


    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}