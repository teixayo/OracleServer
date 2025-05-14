package me.teixayo.server.visual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public enum ChatColor {
    BLACK('0'),
    DARK_BLUE('1'),
    DARK_GREEN('2'),
    DARK_AQUA('3'),
    DARK_RED('4'),
    DARK_PURPLE('5'),
    GOLD('6'),
    GRAY('7'),
    DARK_GRAY('8'),
    BLUE('9'),
    GREEN('a'),
    AQUA('b'),
    RED('c'),
    LIGHT_PURPLE('d'),
    YELLOW('e'),
    WHITE('f'),
    MAGIC('k'),
    BOLD('l'),
    STRIKETHROUGH('m'),
    UNDERLINE('n'),
    ITALIC('o'),
    RESET('r');

    public static final char COLOR_CHAR = '§';
    public final char icon;

    ChatColor(char icon) {
        this.icon = icon;
    }

    public static String translate(char altColorChar, String textToTranslate) {
        char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
                b[i] = COLOR_CHAR;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }

    public static String translate(String textToTranslate) {
        return translate('&', textToTranslate);
    }

    public static List<String> translateList(List<String> list) {
        list.replaceAll(ChatColor::translate);
        return list;
    }

    public static List<String> translateList(String... list) {
        List<String> newList = new ArrayList<>(Arrays.asList(list));
        return translateList(newList);
    }

    public static ChatColor random() {
        return values()[ThreadLocalRandom.current().nextInt(values().length)];
    }

    @Override
    public String toString() {
        return String.valueOf(COLOR_CHAR) + icon;
    }
}