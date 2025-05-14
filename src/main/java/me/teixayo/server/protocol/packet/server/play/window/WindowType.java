package me.teixayo.server.protocol.packet.server.play.window;

public enum WindowType {
    CHEST("minecraft:chest"),
    HOPPER("minecraft:hopper"),
    BEACON("minecraft:beacon"),
    BREWING_STAND("minecraft:brewing_stand"),
    ANVIL("minecraft:anvil"),
    WORK_BENCH("minecraft:crafting_table"),
    DISPENSER("minecraft:dispenser"),
    DROPPER("minecraft:dropper"),
    ENCHANT_TABLE("minecraft:enchanting_table"),
    FURNACE("minecraft:furnace");

    private final String type;

    WindowType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
