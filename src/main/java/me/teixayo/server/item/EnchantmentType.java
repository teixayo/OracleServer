package me.teixayo.server.item;

import lombok.Getter;

import java.util.HashMap;

@Getter
public enum EnchantmentType {
    PROTECTION_ENVIRONMENTAL(0, 4),
    PROTECTION_FIRE(1, 4),
    PROTECTION_FALL(2, 4),
    PROTECTION_EXPLOSIONS(3, 4),
    PROTECTION_PROJECTILE(4, 4),
    OXYGEN(5, 3),
    WATER_WORKER(6, 1),
    THORNS(7, 3),
    DEPTH_STRIDER(8, 3),
    DAMAGE_ALL(16, 5),
    DAMAGE_UNDEAD(17, 5),
    DAMAGE_ARTHROPODS(18, 5),
    KNOCKBACK(19, 2),
    FIRE_ASPECT(20, 2),
    LOOT_BONUS_MOBS(21, 3),
    DIG_SPEED(32, 5),
    SILK_TOUCH(33, 1),
    DURABILITY(34, 3),
    LOOT_BONUS_BLOCKS(35, 3),
    ARROW_DAMAGE(48, 5),
    ARROW_KNOCKBACK(49, 2),
    ARROW_FIRE(50, 1),
    ARROW_INFINITE(51, 1),
    LUCK(61, 3),
    LURE(62, 3);

    private static final HashMap<Integer, EnchantmentType> ids = new HashMap<>();

    static {
        for (EnchantmentType enchantmentType : values()) {
            ids.put(enchantmentType.id, enchantmentType);
        }
    }

    private final int maxLevel;
    private final int id;

    EnchantmentType(int id, int maxLevel) {
        this.maxLevel = maxLevel;
        this.id = id;
    }

    public static EnchantmentType getEnhancementByID(int id) {
        return ids.get(id);
    }

}
