package me.teixayo.server.chunk;

import lombok.Getter;

@Getter
public enum Dimension {
    Nether((byte) -1),
    Overworld((byte) 0),
    End((byte) 1);


    private final byte data;

    Dimension(byte data) {
        this.data = data;
    }
}
