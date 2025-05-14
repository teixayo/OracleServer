package me.teixayo.server.protocol.packet.server.login;

import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;

import java.util.UUID;

public class LoginSuccessPacket extends ServerPacket {
    private final UUID uuid;
    private final String username;

    public LoginSuccessPacket(UUID uuid, String username) {
        this.uuid = uuid;
        this.username = username;
    }

    @Override
    public void write() {
        writeUUID(uuid);
        writeString(username);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.LOGIN_SUCCESS;
    }
}
