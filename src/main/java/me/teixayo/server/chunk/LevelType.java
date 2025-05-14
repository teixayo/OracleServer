package me.teixayo.server.chunk;

import lombok.Getter;

@Getter
public enum LevelType {
    Default("default"),
    Flat("flat"),
    LargeBiomes("largeBiomes"),
    Amplified("amplified"),
    Default1_1("default_1_1");


    private final String data;

    LevelType(String data) {
        this.data = data;
    }
}
