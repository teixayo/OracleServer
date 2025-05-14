package me.teixayo.server.chunk;

import lombok.Getter;

@Getter
public enum Difficulty {
    Peaceful((byte) 0),
    Easy((byte) 1),
    Normal((byte) 2),
    Hard((byte) 3);


    private final byte data;

    Difficulty(byte data) {
        this.data = data;
    }
}
