package me.teixayo.server.protocol.packet.server.login;

import me.teixayo.server.protocol.packet.server.ServerPacket;
import me.teixayo.server.protocol.packet.server.ServerPacketIdentifier;

public class LoginDisconnectPacket extends ServerPacket {
    @Override
    public void write() {
        //TODO add the login disconnect packet format (json)
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.LOGIN_DISCONNECT;
    }
}
