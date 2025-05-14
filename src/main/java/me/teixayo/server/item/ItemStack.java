package me.teixayo.server.item;

import lombok.Getter;
import lombok.Setter;
import me.teixayo.server.visual.ChatColor;
import org.jglrxavpok.hephaistos.nbt.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ItemStack {
    public static ItemStack air = builder(Material.AIR).build();

    private Material material;
    private String displayName;
    private int flags;
    private int amount;
    private short durability;
    private boolean unbreakable;
    private HashMap<EnchantmentType, Short> enchantments;
    private List<String> lore;
    private NBTCompound tag;

    //TODO add potion effect
    //TODO add skull owner
    //TODO add skull with game profile
    //TODO add leather color

    public ItemStack(Material material, String displayName, int flags, int amount, short durability, boolean unbreakable, HashMap<EnchantmentType, Short> enchantments, List<String> lore) {
        this.material = material;
        this.displayName = displayName;
        this.flags = flags;
        this.amount = amount;
        this.durability = durability;
        this.unbreakable = unbreakable;
        this.enchantments = enchantments;
        this.lore = lore;
    }

    public static ItemBuilder builder(Material material) {
        return new ItemBuilder(material);
    }

    public void setTAG(NBTCompound tag) {
        if (tag.contains("display")) {
            NBTCompound display = tag.getCompound("display");
            if (display.contains("Name")) {
                displayName = display.getString("Name");
            }

            if (display.contains("Lore")) {
                NBTList<NBT> loreNBT = display.getList("Lore");
                lore = new ArrayList<>();
                for (int i = 0; i < loreNBT.getSize(); i++) {
                    NBTString nbt = (NBTString) loreNBT.get(i);
                    lore.add(nbt.getValue());
                }
            }
            if (tag.contains("Unbreakable")) {
                unbreakable = tag.getBoolean("Unbreakable");
            }
            if (tag.contains("HideFlags")) {
                flags = tag.getInt("HideFlags");
            }
        }
        if (tag.contains("ench")) {
            enchantments = new HashMap<>();
            NBTList<NBTCompound> list = tag.getList("ench");
            for (int i = 0; i < list.getSize(); i++) {
                NBTCompound nbtCompound = list.get(i);
                short id = nbtCompound.getShort("id");
                short lvl = nbtCompound.getShort("lvl");
                enchantments.put(EnchantmentType.getEnhancementByID(id), lvl);
            }

        }
    }

    public void createTag() {


        Map<String, NBT> map = new HashMap<>();
        NBTString nameTag = null;
        if (displayName != null) {
            nameTag = NBT.String(displayName);
        }
        NBTList<NBTString> loreTag = null;
        if (lore != null && !lore.isEmpty()) {
            NBTString[] nbtStrings = new NBTString[lore.size()];
            for (int i = 0; i < lore.size(); i++) {
                nbtStrings[i] = NBT.String(lore.get(i));
            }

            loreTag = NBT.List(NBTType.TAG_String, nbtStrings);
        }
        if (flags != 0) {
            NBTInt flagsNBT = NBT.Int(flags);
            map.put("HideFlags", flagsNBT);
        }
        if (displayName != null || (lore != null && !lore.isEmpty())) {
            HashMap<String, NBT> displayHashMap = new HashMap<>();
            if (displayName != null) {
                displayHashMap.put("Name", nameTag);

            }
            if (lore != null && !lore.isEmpty()) {
                displayHashMap.put("Lore", loreTag);
            }
            map.put("display", NBT.Compound(displayHashMap));
        }
        if (enchantments != null && !enchantments.isEmpty()) {
            List<NBTCompound> compounds = new ArrayList<>();
            for (Map.Entry<EnchantmentType, Short> entry : enchantments.entrySet()) {
                NBTCompound nbtCompound = NBT.Compound(Map.of(
                        "id", NBT.Short(entry.getKey().getId()),
                        "lvl", NBT.Short(entry.getValue())
                ));
                compounds.add(nbtCompound);
            }

            NBTList<NBTCompound> list = NBT.List(NBTType.TAG_Compound, compounds);
            map.put("ench", list);
        }
        if (unbreakable) {
            NBTByte unbreakableTag = NBT.Boolean(true);
            map.put("Unbreakable", unbreakableTag);
        }

        if (map.isEmpty()) return;
        tag = NBT.Compound(map);
    }

    @Getter
    public static class ItemBuilder {
        private Material material;
        private String displayName;
        private int flags;
        private int amount;
        private short durability;
        private boolean unbreakable;
        private HashMap<EnchantmentType, Short> enchantments;
        private List<String> lore;

        public ItemBuilder(Material material) {
            this.amount = 1;
            this.unbreakable = false;
            this.material = material;

        }

        public ItemBuilder setMaterial(Material material) {
            this.material = material;
            return this;
        }

        public ItemBuilder setDisplayName(String displayName) {
            this.displayName = ChatColor.translate(displayName);
            return this;
        }

        public ItemBuilder setFlag(ItemFlag... itemFlags) {
            for (ItemFlag flag : itemFlags) {
                flags |= flag.getBitFieldPart();
            }
            return this;
        }

        public ItemBuilder setAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public ItemBuilder setDurability(short durability) {
            this.durability = durability;
            return this;
        }

        public ItemBuilder setUnbreakable(boolean unbreakable) {
            this.unbreakable = unbreakable;
            return this;
        }

        public ItemBuilder addEnchantment(EnchantmentType enchantment, short lvl) {
            if (enchantments == null) {
                this.enchantments = new HashMap<>();
            }
            enchantments.put(enchantment, lvl);
            return this;
        }

        public ItemBuilder removeEnchantment(EnchantmentType enchantment) {
            enchantments.remove(enchantment);
            return this;
        }

        public ItemBuilder clearEnchantments() {
            enchantments.clear();
            return this;
        }

        public ItemBuilder setLore(List<String> lines) {
            this.lore = ChatColor.translateList(lines);
            return this;
        }

        public ItemBuilder setLore(String... lines) {
            this.lore = ChatColor.translateList(lines);
            return this;
        }

        public ItemBuilder addLore(String... lines) {
            if (this.lore == null) {
                this.lore = new ArrayList<>();
            }
            this.lore.addAll(ChatColor.translateList(lines));
            return this;
        }

        public ItemBuilder resetLore() {
            this.lore.clear();
            return this;
        }

        public ItemStack build() {
            return new ItemStack(material, displayName, flags, amount, durability, unbreakable, enchantments, lore);
        }
    }

}
