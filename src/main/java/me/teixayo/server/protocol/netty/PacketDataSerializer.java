package me.teixayo.server.protocol.netty;

import io.netty.buffer.ByteBuf;
import me.teixayo.server.entity.WatchableObject;
import me.teixayo.server.item.ItemStack;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class PacketDataSerializer {

    public static String readString(ByteBuf byteBuf) {
        int addressLength = readVarInt(byteBuf);
        return byteBuf.readBytes(addressLength).toString(StandardCharsets.UTF_8);
    }

    public static void writePosition(ByteBuf byteBuf, int x, int y, int z) {
        long i = (((long) x & 0x3FFFFFF) << 38) | (((long) z & 0x3FFFFFF) << 12) | ((long) y & 0xFFF);

        byteBuf.writeLong(i);
    }

    public static int[] readPosition(ByteBuf byteBuf) {
        long value = byteBuf.readLong();
        return new int[]{(int) (value >> 38), (int) (value << 52 >> 52), (int) (value << 26 >> 38)};
    }

    public static int readVarInt(ByteBuf byteBuf) {
        byte k;
        int i = 0;
        int j = 0;
        do {
            k = byteBuf.readByte();
            i |= (k & 127) << j++ * 7;
            if (j <= 5) continue;
            throw new RuntimeException("VarInt too big");
        } while ((k & 128) == 128);
        return i;
    }

    public static void writeVarInt(ByteBuf byteBuf, int value) {
        do {
            if ((value & -128) == 0) {
                byteBuf.writeByte(value);
                return;
            }
            byteBuf.writeByte(value & 127 | 128);
            value >>>= 7;
        } while (true);
    }

    public static void writeVarInt(ByteBuf byteBuf, int index, int value) {
        byteBuf.writerIndex(index);
        do {
            if ((value & -128) == 0) {
                byteBuf.writeByte(value);
                return;
            }
            byteBuf.writeByte(value & 127 | 128);
            value >>>= 7;
        } while (true);
    }

    public static void writeString(ByteBuf byteBuf, String json) {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        writeVarInt(byteBuf, bytes.length);
        byteBuf.writeBytes(bytes);
    }

    public static UUID readUUID(ByteBuf byteBuf) {
        return new UUID(byteBuf.readLong(), byteBuf.readLong());
    }

    public static void writeItemStack(ByteBuf byteBuf, ItemStack item) {
//        if (item == null) {
//            byteBuf.writeShort(-1);
//            return;
//        }
//        byteBuf.writeShort(item.getItemID());
//        byteBuf.writeByte(item.getCount());
//        byteBuf.writeShort(item.getData());
////        NBTTagCompound nbttagcompound = null;
////        if (itemstack.getItem().usesDurability() || itemstack.getItem().p()) {
////            itemstack = itemstack.cloneItemStack();
////            CraftItemStack.setItemMeta(itemstack, CraftItemStack.getItemMeta(itemstack));
////            nbttagcompound = itemstack.getTag();
////        }
////
////        this.a(nbttagcompound);
//
//        byteBuf.writeByte(0);

    }

    public static void writeWatchableObject(ByteBuf byteBuf, WatchableObject object) {
        int i = (object.getObjectClassID() << 5 | object.getObjectIndex() & 0x1F) & 0xFF;
        byteBuf.writeByte(i);
        switch (object.getObjectClassID()) {
            case 0 -> byteBuf.writeByte((byte) object.getObject());
            case 1 -> byteBuf.writeShort((short) object.getObject());
            case 2 -> byteBuf.writeInt((int) object.getObject());
            case 3 -> byteBuf.writeFloat((float) object.getObject());
            case 4 -> writeString(byteBuf, (String) object.getObject());
            case 5 -> writeItemStack(byteBuf, (ItemStack) object.getObject());
//            case 6 -> writePosition(byteBuf,(Position)object.getObject());
        }
        byteBuf.writeByte(127);
    }
}