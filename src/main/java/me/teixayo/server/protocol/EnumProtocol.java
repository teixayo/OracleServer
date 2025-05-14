package me.teixayo.server.protocol;

import io.netty.util.collection.IntObjectHashMap;
import io.netty.util.collection.IntObjectMap;
import me.teixayo.server.protocol.packet.client.ClientPacket;
import me.teixayo.server.protocol.packet.client.handshake.ClientHandshakePacket;
import me.teixayo.server.protocol.packet.client.login.ClientEncryptionResponsePacket;
import me.teixayo.server.protocol.packet.client.login.ClientLoginStartPacket;
import me.teixayo.server.protocol.packet.client.play.*;
import me.teixayo.server.protocol.packet.client.play.movement.PlayerLookPacket;
import me.teixayo.server.protocol.packet.client.play.movement.PlayerPositionAndLookPacket;
import me.teixayo.server.protocol.packet.client.play.movement.PlayerPositionPacket;
import me.teixayo.server.protocol.packet.client.status.StatusPingPacket;
import me.teixayo.server.protocol.packet.client.status.StatusRequestPacket;

import java.util.HashMap;
import java.util.Map;

public class EnumProtocol {


    private static final Map<Integer, Map<EnumProtocolDirection, IntObjectMap<Class<? extends ClientPacket>>>> packetMaps = new HashMap<>();
    private static final HashMap<Class<? extends ClientPacket>, Integer> packetClassToId = new HashMap<>(16, 0.5f);

    static {
        initialize(-1);
        registerPacket(-1, 0x00, ClientHandshakePacket.class);

        initialize(0);
        registerPacket(0, 0x00, PlayerKeepAlivePacket.class);
        registerPacket(0, 0x01, PlayerChatMessagePacket.class);
        registerPacket(0, 0x02, UseEntityPacket.class);
//        registerPacket(0, EnumProtocolDirection.CLIENTBOUND, 0x03, PlayerOnGroundPacket.class); //bad performance
        registerPacket(0, 0x04, PlayerPositionPacket.class);
        registerPacket(0, 0x05, PlayerLookPacket.class);
        registerPacket(0, 0x06, PlayerPositionAndLookPacket.class);
        registerPacket(0, 0x07, PlayerDiggingPacket.class);
        registerPacket(0, 0x08, PlayerBlockPlacementPacket.class);
        registerPacket(0, 0x09, PlayerDiggingPacket.class);
        registerPacket(0, 0x0A, AnimationPacket.class);
        registerPacket(0, 0x0B, EntityActionPacket.class);
        registerPacket(0, 0x0C, SteerVehiclePacket.class);
        registerPacket(0, 0x0D, CloseWindowPacket.class);
        registerPacket(0, 0x0E, ClickWindowPacket.class);
        registerPacket(0, 0x0F, ConfirmTransactionPacket.class);
        registerPacket(0, 0x10, CreativeInventoryActionPacket.class);
        registerPacket(0, 0x11, EnchantItemPacket.class);
        //Todo 0x12 (json)
        registerPacket(0, 0x13, PlayerAbilitiesPacket.class);
        registerPacket(0, 0x14, ClientTabCompletePacket.class);
        registerPacket(0, 0x15, ClientSettingsPacket.class);
        registerPacket(0, 0x16, ClientStatusPacket.class);
        registerPacket(0, 0x17, PluginMessagePacket.class);
        registerPacket(0, 0x18, SpectatePacket.class);
        registerPacket(0, 0x19, ResourcePackStatusPacket.class);

        initialize(1);
        registerPacket(1, 0x00, StatusRequestPacket.class);
        registerPacket(1, 0x01, StatusPingPacket.class);

        initialize(2);
        registerPacket(2, 0x00, ClientLoginStartPacket.class);
        registerPacket(2, 0x01, ClientEncryptionResponsePacket.class);


    }

    public static Class<? extends ClientPacket> getPacket(int state, EnumProtocolDirection direction, int packetID) {
        Map<EnumProtocolDirection, IntObjectMap<Class<? extends ClientPacket>>> enumProtocolDirectionIntObjectMapMap = packetMaps.get(state);
        if (enumProtocolDirectionIntObjectMapMap == null) return null;
        IntObjectMap<Class<? extends ClientPacket>> classIntObjectMap = enumProtocolDirectionIntObjectMapMap.get(direction);
        if (classIntObjectMap == null) return null;
        return classIntObjectMap
                .get(packetID);

    }

    public static Integer getPacketID(Class<? extends ClientPacket> clazz) {
        return packetClassToId.get(clazz);
    }

    private static void registerPacket(int state, int packetID, Class<? extends ClientPacket> clazz) {
        packetMaps.get(state)
                .get(EnumProtocolDirection.CLIENTBOUND)
                .put(packetID, clazz);
        packetClassToId.put(clazz, packetID);
    }

    private static void initialize(int state) {
        HashMap<EnumProtocolDirection, IntObjectMap<Class<? extends ClientPacket>>> value = new HashMap<>();
        value.put(EnumProtocolDirection.CLIENTBOUND, new IntObjectHashMap<>());
        value.put(EnumProtocolDirection.SERVERBOUND, new IntObjectHashMap<>());
        packetMaps.put(state, value);
    }
}
