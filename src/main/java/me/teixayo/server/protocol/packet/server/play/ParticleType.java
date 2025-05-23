package me.teixayo.server.protocol.packet.server.play;

public enum ParticleType {

    EXPLOSION_NORMAL(0),
    EXPLOSION_LARGE(1),
    EXPLOSION_HUGE(2),
    FIREWORKS_SPARK(3),
    WATER_BUBBLE(4),
    WATER_SPLASH(5),
    WATER_WAKE(6),
    SUSPENDED(7),
    SUSPENDED_DEPTH(8),
    CRIT(9),
    CRIT_MAGIC(10),
    SMOKE_NORMAL(11),
    SMOKE_LARGE(12),
    SPELL(13),
    SPELL_INSTANT(14),
    SPELL_MOB(15),
    SPELL_MOB_AMBIENT(16),
    SPELL_WITCH(17),
    DRIP_WATER(18),
    DRIP_LAVA(19),
    VILLAGER_ANGRY(20),
    VILLAGER_HAPPY(21),
    TOWN_AURA(22),
    NOTE(23),
    PORTAL(24),
    ENCHANTMENT_TABLE(25),
    FLAME(26),
    LAVA(27),
    FOOTSTEP(28),
    CLOUD(29),
    REDSTONE(30),
    SNOWBALL(31),
    SNOW_SHOVEL(32),
    SLIME(33),
    HEART(34),
    BARRIER(35),
    ITEM_CRACK(36),
    BLOCK_CRACK(37),
    BLOCK_DUST(38),
    WATER_DROP(39),
    ITEM_TAKE(40),
    MOB_APPEARANCE(41);

    private final int id;

    ParticleType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
