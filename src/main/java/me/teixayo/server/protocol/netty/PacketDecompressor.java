package me.teixayo.server.protocol.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import me.teixayo.server.Server;

import java.util.List;
import java.util.zip.Inflater;

public class PacketDecompressor extends ByteToMessageDecoder {
    private final Inflater inflater;

    private int threshold;

    public PacketDecompressor(int compressionThreshold) {
        this.threshold = compressionThreshold;
        this.inflater = new Inflater();

    }

    protected void decode(ChannelHandlerContext var1, ByteBuf var2, List<Object> var3) throws Exception {
        if (var2.readableBytes() != 0) {
            int var5 = PacketDataSerializer.readVarInt(var2);
            if (var5 == 0) {
                var3.add(var2.readBytes(var2.readableBytes()));
            } else {
                if (var5 < this.threshold)
                    Server.get().getLogger().error("Badly compressed packet - size of " + var5 + " is below server threshold of " + this.threshold, new DecoderException());
                if (var5 > 2097152)
                    Server.get().getLogger().error("Badly compressed packet - size of " + var5 + " is larger than protocol maximum of " + 2097152, new DecoderException());
                byte[] var6 = new byte[var2.readableBytes()];
                var2.readBytes(var6);
                this.inflater.setInput(var6);

                byte[] var7 = new byte[var5];

                this.inflater.inflate(var7);
                var3.add(Unpooled.wrappedBuffer(var7));
                this.inflater.reset();
            }
        }
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}
