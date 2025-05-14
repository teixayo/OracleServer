package me.teixayo.server.item;

public enum ItemFlag {
    HIDE_ENCHANTS,
    HIDE_ATTRIBUTES,
    HIDE_UNBREAKABLE,
    HIDE_DESTROYS,
    HIDE_PLACED_ON,
    HIDE_POTION_EFFECTS;

    private final int bitFieldPart = 1 << this.ordinal();

    public int getBitFieldPart() {
        return bitFieldPart;
    }
}

