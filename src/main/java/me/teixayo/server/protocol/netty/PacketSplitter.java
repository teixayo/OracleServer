package me.teixayo.server.protocol.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.util.List;

public class PacketSplitter extends ByteToMessageDecoder {
    public int readVarInt(ByteBuf byteBuf) {
        int i = 0;
        int j = 0;
        while (true) {
            byte b0 = byteBuf.readByte();
            i |= (b0 & Byte.MAX_VALUE) << j++ * 7;
            if (j > 5) throw new RuntimeException("VarInt too big");
            if ((b0 & 0x80) != 128) return i;
        }
    }

    protected void decode(ChannelHandlerContext channelhandlercontext, ByteBuf in, List<Object> out) {
        if (!in.isReadable()) return;
        int origReaderIndex = in.readerIndex();
        for (int i = 0; i < 3; i++) {
            if (!in.isReadable()) {
                in.readerIndex(origReaderIndex);
                return;
            }
            byte read = in.readByte();
            if (read >= 0) {
                in.readerIndex(origReaderIndex);
                int length = readVarInt(in);
                if (length == 0) return;
                if (in.readableBytes() < length) {
                    in.readerIndex(origReaderIndex);
                    return;
                }
                out.add(in.readRetainedSlice(length));
                return;
            }
        }
        throw new CorruptedFrameException("length wider than 21-bit");
    }
}

