package me.teixayo.server.protocol.packet.server.login;

import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;

public class EncryptionRequestPacket extends ServerPacket {
    private final String serverId;
    private final byte[] publicKey;
    private final byte[] verifyToken;

    public EncryptionRequestPacket(String serverId, byte[] publicKey, byte[] verifyToken) {
        this.serverId = serverId;
        this.publicKey = publicKey;
        this.verifyToken = verifyToken;
    }

    @Override
    public void write() {
        //TODO add the encryption request packet format
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.LOGIN_ENCRYPTION_REQUEST;
    }
}
