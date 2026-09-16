package ru.onixmine.onixbuyer;

import org.bukkit.potion.PotionType;

import java.util.HashMap;
import java.util.Map;

public class PotionNames {

    private static final Map<PotionType, String> NAMES = new HashMap<>();

    static {
        NAMES.put(PotionType.HEALING,               "\u0417\u0435\u043b\u044c\u0435 \u043b\u0435\u0447\u0435\u043d\u0438\u044f");
        NAMES.put(PotionType.STRONG_HEALING,        "\u0417\u0435\u043b\u044c\u0435 \u043b\u0435\u0447\u0435\u043d\u0438\u044f II");
        NAMES.put(PotionType.REGENERATION,          "\u0417\u0435\u043b\u044c\u0435 \u0440\u0435\u0433\u0435\u043d\u0435\u0440\u0430\u0446\u0438\u0438");
        NAMES.put(PotionType.LONG_REGENERATION,     "\u0417\u0435\u043b\u044c\u0435 \u0440\u0435\u0433\u0435\u043d\u0435\u0440\u0430\u0446\u0438\u0438 (+)");
        NAMES.put(PotionType.STRENGTH,              "\u0417\u0435\u043b\u044c\u0435 \u0441\u0438\u043b\u044b");
        NAMES.put(PotionType.LONG_STRENGTH,         "\u0417\u0435\u043b\u044c\u0435 \u0441\u0438\u043b\u044b (+)");
        NAMES.put(PotionType.STRONG_STRENGTH,       "\u0417\u0435\u043b\u044c\u0435 \u0441\u0438\u043b\u044b II");
        NAMES.put(PotionType.SWIFTNESS,             "\u0417\u0435\u043b\u044c\u0435 \u0431\u044b\u0441\u0442\u0440\u043e\u0442\u044b");
        NAMES.put(PotionType.LONG_SWIFTNESS,        "\u0417\u0435\u043b\u044c\u0435 \u0431\u044b\u0441\u0442\u0440\u043e\u0442\u044b (+)");
        NAMES.put(PotionType.STRONG_SWIFTNESS,      "\u0417\u0435\u043b\u044c\u0435 \u0431\u044b\u0441\u0442\u0440\u043e\u0442\u044b II");
        NAMES.put(PotionType.FIRE_RESISTANCE,       "\u0417\u0435\u043b\u044c\u0435 \u043e\u0433\u043d\u0435\u0441\u0442\u043e\u0439\u043a\u043e\u0441\u0442\u0438");
        NAMES.put(PotionType.LONG_FIRE_RESISTANCE,  "\u0417\u0435\u043b\u044c\u0435 \u043e\u0433\u043d\u0435\u0441\u0442\u043e\u0439\u043a\u043e\u0441\u0442\u0438 (+)");
        NAMES.put(PotionType.NIGHT_VISION,          "\u0417\u0435\u043b\u044c\u0435 \u043d\u043e\u0447\u043d\u043e\u0433\u043e \u0437\u0440\u0435\u043d\u0438\u044f");
        NAMES.put(PotionType.LONG_NIGHT_VISION,     "\u0417\u0435\u043b\u044c\u0435 \u043d\u043e\u0447\u043d\u043e\u0433\u043e \u0437\u0440\u0435\u043d\u0438\u044f (+)");
        NAMES.put(PotionType.INVISIBILITY,          "\u0417\u0435\u043b\u044c\u0435 \u043d\u0435\u0432\u0438\u0434\u0438\u043c\u043e\u0441\u0442\u0438");
        NAMES.put(PotionType.LONG_INVISIBILITY,     "\u0417\u0435\u043b\u044c\u0435 \u043d\u0435\u0432\u0438\u0434\u0438\u043c\u043e\u0441\u0442\u0438 (+)");
        NAMES.put(PotionType.WATER_BREATHING,       "\u0417\u0435\u043b\u044c\u0435 \u0432\u043e\u0434\u043d\u043e\u0433\u043e \u0434\u044b\u0445\u0430\u043d\u0438\u044f");
        NAMES.put(PotionType.LONG_WATER_BREATHING,  "\u0417\u0435\u043b\u044c\u0435 \u0432\u043e\u0434\u043d\u043e\u0433\u043e \u0434\u044b\u0445\u0430\u043d\u0438\u044f (+)");
        NAMES.put(PotionType.LEAPING,               "\u0417\u0435\u043b\u044c\u0435 \u043f\u0440\u044b\u0436\u043a\u043e\u0432\u0438\u0442\u043e\u0441\u0442\u0438");
        NAMES.put(PotionType.LONG_LEAPING,          "\u0417\u0435\u043b\u044c\u0435 \u043f\u0440\u044b\u0436\u043a\u043e\u0432\u0438\u0442\u043e\u0441\u0442\u0438 (+)");
        NAMES.put(PotionType.STRONG_LEAPING,        "\u0417\u0435\u043b\u044c\u0435 \u043f\u0440\u044b\u0436\u043a\u043e\u0432\u0438\u0442\u043e\u0441\u0442\u0438 II");
        NAMES.put(PotionType.SLOW_FALLING,          "\u0417\u0435\u043b\u044c\u0435 \u043c\u0435\u0434\u043b\u0435\u043d\u043d\u043e\u0433\u043e \u043f\u0430\u0434\u0435\u043d\u0438\u044f");
        NAMES.put(PotionType.LONG_SLOW_FALLING,     "\u0417\u0435\u043b\u044c\u0435 \u043c\u0435\u0434\u043b\u0435\u043d\u043d\u043e\u0433\u043e \u043f\u0430\u0434\u0435\u043d\u0438\u044f (+)");
    }

    public static String get(PotionType pt) {
        return NAMES.getOrDefault(pt, pt.name());
    }
}
