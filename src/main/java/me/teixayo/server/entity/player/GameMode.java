package me.teixayo.server.entity.player;

import lombok.Getter;

@Getter
public enum GameMode {
    Survival((byte) 0), Creative((byte) 1), Adventure((byte) 2), Spectator((byte) 3);


    private final byte data;

    GameMode(byte data) {
        this.data = data;
    }
}
