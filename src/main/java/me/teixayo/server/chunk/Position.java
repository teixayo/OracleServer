package me.teixayo.server.chunk;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Position {
    private int x;
    private int y;
    private int z;

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Position() {
    }
}
