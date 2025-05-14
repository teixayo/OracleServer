package me.teixayo.server.protocol.packet.server;

import io.netty.buffer.ByteBuf;
import me.teixayo.server.chunk.Position;
import me.teixayo.server.entity.DataWatcher;
import me.teixayo.server.entity.WatchableObject;
import me.teixayo.server.item.ItemStack;
import org.jglrxavpok.hephaistos.nbt.CompressedProcesser;
import org.jglrxavpok.hephaistos.nbt.NBT;
import org.jglrxavpok.hephaistos.nbt.NBTWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public abstract class ServerPacket {


    public ByteBuf buffer = null;

    public abstract void write();

    public abstract int getId();

    public void writeBoolean(boolean value) {
        buffer.writeBoolean(value);
    }

    public void writeByte(byte value) {
        buffer.writeByte(value);
    }

    public void writeByte(int value) {
        buffer.writeByte(value);
    }

    public void writeShort(short value) {
        buffer.writeShort(value);
    }

    public void writeShort(int value) {
        buffer.writeShort(value);
    }

    public void writeInt(int value) {
        buffer.writeInt(value);
    }

    public void writeBytes(ByteBuf byteBuf) {
        byteBuf.writeBytes(byteBuf);
    }

    public void writeBytes(ByteBuffer byteBuffer) {
        buffer.writeBytes(byteBuffer);
    }

    public void writeLong(long value) {
        buffer.writeLong(value);
    }

    public void writeFloat(float value) {
        buffer.writeFloat(value);
    }

    public void writeDouble(double value) {
        buffer.writeDouble(value);
    }

    public void writePosition(Position value) {
        long data = (long) (value.getX() & 0x3ffffff) << 38 | (long) (value.getY() & 0xfff) << 26 | value.getZ() & 0x3ffffff;
        writeLong(data);
    }


    public void writeVarInt(int value) {
        do {
            if ((value & -128) == 0) {
                writeByte((byte) value);
                return;
            }
            writeByte((byte) (value & 127 | 128));
            value >>>= 7;
        } while (true);
    }

    public void writeString(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        writeVarInt(bytes.length);
        buffer.writeBytes(bytes);
    }

    public void writeNBT(NBT value) {
        writeByte((byte) value.getID().getOrdinal());
        writeShort((short) 0);

        NBTWriter nbtWriter = new NBTWriter(new OutputStream() {
            @Override
            public void write(int b) {
                writeByte((byte) b);
            }
        }, CompressedProcesser.NONE);

        try {
            nbtWriter.writeRaw(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeUUID(UUID value) {
        writeString(value.toString());

    }

    public void writeUUIDLong(UUID value) {
        writeLong(value.getMostSignificantBits());
        writeLong(value.getLeastSignificantBits());
    }

    public void writeDataWatcher(DataWatcher dataWatcher) {
        for (WatchableObject watchableObject : dataWatcher.getData().values()) {
            writeWatchableObject(watchableObject);

        }
        writeByte(127);
    }

    public void writeWatchableObject(WatchableObject object) {
        int i = (object.getObjectClassID() << 5 | object.getObjectIndex() & 0x1F) & 0xFF;
        writeByte(i);
        switch (object.getObjectClassID()) {
            case 0 -> writeByte((byte) object.getObject());
            case 1 -> writeShort((short) object.getObject());
            case 2 -> writeInt((int) object.getObject());
            case 3 -> writeFloat((float) object.getObject());
            case 4 -> writeString((String) object.getObject());
            case 5 -> writeItem((ItemStack) object.getObject());
            case 6 -> writePosition((Position) object.getObject());
        }

    }

    public void writeItem(ItemStack item) {

        if (item == null) {
            this.writeShort(-1);
            return;
        }
        writeShort((short) item.getMaterial().getId());
        writeByte((byte) item.getAmount());
        writeShort(item.getDurability());

        boolean taggable = (item.getLore() == null || item.getLore().isEmpty()) &&
                item.getDisplayName() == null &&
                (item.getEnchantments() == null || item.getEnchantments().isEmpty())
                && item.getFlags() == 0 && !item.isUnbreakable();
        if (taggable) {
            writeByte((byte) 0);
        } else {
            try {
                item.createTag();
                writeNBT(item.getTag());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
