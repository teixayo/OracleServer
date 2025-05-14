package me.teixayo.server.protocol.packet.client.play;

import lombok.Getter;
import me.teixayo.server.chunk.Position;
import me.teixayo.server.protocol.packet.client.ClientPacket;

import java.util.HashMap;


@Getter
public class PlayerDiggingPacket extends ClientPacket {

    private DiggingStatus diggingStatus;
    private Position location;
    private Face face;

    @Override
    public void read() {
        diggingStatus = DiggingStatus.getDiggingStatus(readByte());
        location = readPosition();
        face = Face.getFace(readByte());
    }

    public enum DiggingStatus {
        Digging((byte) 0),
        Cancelled_Digging((byte) 1),
        Finished_Digging((byte) 2),
        Drop_ItemStack((byte) 3),
        Drop_Item((byte) 4),
        Shoot_arrow_finish_eating((byte) 5);

        private static final HashMap<Byte, DiggingStatus> diggingStatuses = new HashMap<>();

        static {
            for (DiggingStatus status : values()) {
                diggingStatuses.put(status.data, status);
            }
        }

        private final byte data;

        DiggingStatus(byte data) {
            this.data = data;
        }

        public static DiggingStatus getDiggingStatus(byte data) {
            return diggingStatuses.get(data);
        }
    }

    public enum Face {
        Down((byte) 0), // -Y
        Up((byte) 1), // +Y
        Back((byte) 2), // -Z
        Front((byte) 3), // -Z
        Right((byte) 4), // -X
        Left((byte) 5); // +X

        private static final HashMap<Byte, Face> faces = new HashMap<>();

        static {
            for (Face status : values()) {
                faces.put(status.data, status);
            }
        }

        private final byte data;

        Face(byte data) {
            this.data = data;
        }

        public static Face getFace(byte data) {
            return faces.get(data);
        }
    }
}
