package me.teixayo.server.protocol.packet.server;

public final class ServerPacketIdentifier {


    public static final int STATUS_RESPONSE = 0x00;
    public static final int STATUS_PONG = 0x01;

    public static final int LOGIN_DISCONNECT = 0x00;
    public static final int LOGIN_ENCRYPTION_REQUEST = 0x01;
    public static final int LOGIN_SUCCESS = 0x02;
    public static final int LOGIN_SET_COMPRESSION = 0x03;

    public static final int KEEP_ALIVE = 0x00;
    public static final int JOIN_GAME = 0x01;
    public static final int CHAT_MESSAGE = 0x02;
    public static final int ENTITY_EQUIPMENT = 0x04;
    public static final int SPAWN_POSITION = 0x05;
    public static final int UPDATE_HEALTH = 0x06;

    public static final int PLAYER_POSITION_AND_LOCK = 0x08;
    public static final int HELD_ITEM_CHANGE = 0x09;

    public static final int ANIMATION = 0x0B;
    public static final int SPAWN_EXPERIENCE_ORB = 0x11;

    public static final int CHUNK_DATA = 0x21;
    public static final int BLOCK_CHANGE = 0x23;
    public static final int MAP_CHUNK_BULK_DATA = 0x26;
    public static final int HEADER_FOOTER = 0x47;
    public static final int SET_EXPERIENCE = 0x1F;
    public static final int PARTICLE = 0x2A;
    public static final int TAB_COMPLETE = 0x3A;
    public static final int OPEN_WINDOW = 0x2D;
    public static final int SET_SLOT = 0x2F;
    public static final int SET_MULTI_SLOT = 0x30;
    public static final int DISCONNECT = 0x40;

    public static final int PLAYER_LIST_ITEM = 0x38;
    public static final int SPAWN_PLAYER = 0x0C;
}
