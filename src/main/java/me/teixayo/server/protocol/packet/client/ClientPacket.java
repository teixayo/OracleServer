package me.teixayo.server.protocol.packet.client;

import io.netty.buffer.ByteBuf;
import me.teixayo.server.chunk.Position;
import me.teixayo.server.item.ItemStack;
import me.teixayo.server.item.Material;
import org.jglrxavpok.hephaistos.nbt.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;


public abstract class ClientPacket {

    public ByteBuf buffer = null;

    public abstract void read();

    protected boolean readBoolean() {
        return buffer.readBoolean();
    }

    protected byte readByte() {
        return buffer.readByte();
    }

    protected short readShort() {
        return buffer.readShort();
    }

    protected int readUnsignedShort() {
        return buffer.readUnsignedShort();
    }

    protected int readInt() {
        return buffer.readInt();
    }

    protected long readLong() {
        return buffer.readLong();
    }

    protected float readFloat() {
        return buffer.readFloat();
    }

    protected double readDouble() {
        return buffer.readDouble();
    }

    protected Position readPosition() {
        long value = readLong();
        int x = (int) (value >> 38);
        int y = (int) (value >> 26 & 0xfff);
        int z = (int) (value << 38 >> 38);
        return new Position(x, y, z);
    }

    protected int readVarInt() {
        byte k;
        int value = 0;
        int j = 0;
        do {
            k = readByte();
            value |= (k & 127) << j++ * 7;
            if (j <= 5) continue;
            throw new RuntimeException("VarInt too big");
        } while ((k & 128) == 128);
        return value;
    }

    protected String readString() {
        int addressLength = readVarInt();
        ByteBuf byteBuf = buffer.readBytes(addressLength);
        String string = byteBuf.toString(StandardCharsets.UTF_8);
        byteBuf.release();
        return string;
    }

    protected ByteBuf readAllBytes() {
        return buffer.readBytes(buffer.readableBytes());
    }

    protected NBT readNBT() {
        NBTReader nbtReader = new NBTReader(new InputStream() {
            @Override
            public int read() {
                return readByte() & 0xFF;
            }

            @Override
            public int available() {
                return buffer.readableBytes();
            }
        }, CompressedProcesser.NONE);
        try {
            byte tagId = readByte();
            readShort();
            if (tagId == NBTType.TAG_End.getOrdinal()) return NBTEnd.INSTANCE;
            return nbtReader.readRaw(tagId);
        } catch (IOException | NBTException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected UUID readUUID() {
        return java.util.UUID.fromString(readString());
//                return new UUID(buffer.buffer().readLong(), buffer.buffer().readLong());
    }

    protected ItemStack readItem() {
        short itemID = readShort();
        if (itemID >= 0) {
            byte amount = readByte();
            short durability = readShort();

            int i = buffer.readerIndex();
            byte nbtTag = readByte();
            if (nbtTag == 0) {
                return ItemStack.builder(Material.getMaterial(itemID))
                        .setAmount(amount)
                        .setDurability(durability).build();
            }

            buffer.readerIndex(i);
            NBTCompound nbt = (NBTCompound) readNBT();

            ItemStack item = ItemStack.builder(Material.getMaterial(itemID))
                    .setAmount(amount)
                    .setDurability(durability).build();

            item.setTAG(nbt);
            return item;

        }
        return ItemStack.air;
    }
}
