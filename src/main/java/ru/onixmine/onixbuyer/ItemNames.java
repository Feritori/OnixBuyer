package ru.onixmine.onixbuyer;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class ItemNames {

    private static final Map<Material, String> NAMES = new HashMap<>();

    static {
        // Драгоценности
        NAMES.put(Material.COAL,              "\u0423\u0433\u043e\u043b\u044c");
        NAMES.put(Material.AMETHYST_SHARD,    "\u041e\u0441\u043a\u043e\u043b\u043e\u043a \u0430\u043c\u0435\u0442\u0438\u0441\u0442\u0430");
        NAMES.put(Material.EMERALD,           "\u0418\u0437\u0443\u043c\u0440\u0443\u0434");
        NAMES.put(Material.LAPIS_LAZULI,      "\u041b\u0430\u0437\u0443\u0440\u0438\u0442");
        NAMES.put(Material.DIAMOND,           "\u0410\u043b\u043c\u0430\u0437");
        NAMES.put(Material.COPPER_INGOT,      "\u041c\u0435\u0434\u043d\u044b\u0439 \u0441\u043b\u0438\u0442\u043e\u043a");
        NAMES.put(Material.IRON_INGOT,        "\u0416\u0435\u043b\u0435\u0437\u043d\u044b\u0439 \u0441\u043b\u0438\u0442\u043e\u043a");
        NAMES.put(Material.GOLD_INGOT,        "\u0417\u043e\u043b\u043e\u0442\u043e\u0439 \u0441\u043b\u0438\u0442\u043e\u043a");
        NAMES.put(Material.NETHERITE_SCRAP,   "\u041e\u0431\u043b\u043e\u043c\u043e\u043a \u043d\u0435\u0437\u0435\u0440\u0438\u0442\u0430");
        NAMES.put(Material.RAW_IRON,          "\u0421\u044b\u0440\u0430\u044f \u0436\u0435\u043b\u0435\u0437\u043d\u0430\u044f \u0440\u0443\u0434\u0430");
        NAMES.put(Material.REDSTONE,          "\u0420\u0435\u0434\u0441\u0442\u043e\u0443\u043d");
        NAMES.put(Material.QUARTZ,            "\u041a\u0432\u0430\u0440\u0446");

        // Нежить
        NAMES.put(Material.ROTTEN_FLESH,      "\u0413\u043d\u0438\u043b\u0430\u044f \u043f\u043b\u043e\u0442\u044c");
        NAMES.put(Material.LEATHER,           "\u041a\u043e\u0436\u0430");
        NAMES.put(Material.BONE,              "\u041a\u043e\u0441\u0442\u044c");
        NAMES.put(Material.BLAZE_ROD,         "\u0421\u0442\u0435\u0440\u0436\u0435\u043d\u044c \u043e\u0433\u043d\u0435\u043d\u043d\u043e\u0433\u043e");
        NAMES.put(Material.PRISMARINE_SHARD,  "\u041e\u0441\u043a\u043e\u043b\u043e\u043a \u043f\u0440\u0438\u0437\u043c\u0430\u0440\u0438\u043d\u0430");
        NAMES.put(Material.SLIME_BALL,        "\u0428\u0430\u0440 \u0441\u043b\u0430\u0439\u043c\u0430");
        NAMES.put(Material.MAGMA_CREAM,       "\u041c\u0430\u0433\u043c\u043e\u0432\u044b\u0439 \u043a\u0440\u0435\u043c");
        NAMES.put(Material.PRISMARINE_CRYSTALS, "\u041a\u0440\u0438\u0441\u0442\u0430\u043b\u043b\u044b \u043f\u0440\u0438\u0437\u043c\u0430\u0440\u0438\u043d\u0430");
        NAMES.put(Material.GHAST_TEAR,        "\u0421\u043b\u0435\u0437\u0430 \u0433\u0430\u0441\u0442\u0430");
        NAMES.put(Material.WITHER_SKELETON_SKULL, "\u0427\u0435\u0440\u0435\u043f \u0438\u0441\u0441\u0443\u0448\u0435\u043d\u043d\u043e\u0433\u043e \u0441\u043a\u0435\u043b\u0435\u0442\u0430");
        NAMES.put(Material.SHULKER_SHELL,     "\u041f\u0430\u043d\u0446\u0438\u0440\u044c \u0448\u0430\u043b\u043a\u0435\u0440\u0430");

        // Еда
        NAMES.put(Material.APPLE,             "\u042f\u0431\u043b\u043e\u043a\u043e");
        NAMES.put(Material.MELON_SLICE,       "\u0414\u043e\u043b\u044c\u043a\u0430 \u0430\u0440\u0431\u0443\u0437\u0430");
        NAMES.put(Material.SWEET_BERRIES,     "\u0421\u043b\u0430\u0434\u043a\u0430\u044f \u044f\u0433\u043e\u0434\u0430");
        NAMES.put(Material.GOLDEN_CARROT,     "\u0417\u043e\u043b\u043e\u0442\u0430\u044f \u043c\u043e\u0440\u043a\u043e\u0432\u044c");
        NAMES.put(Material.CARROT,            "\u041c\u043e\u0440\u043a\u043e\u0432\u044c");
        NAMES.put(Material.POTATO,            "\u041a\u0430\u0440\u0442\u043e\u0444\u0435\u043b\u044c");
        NAMES.put(Material.BEETROOT,          "\u0421\u0432\u0451\u043a\u043b\u0430");
        NAMES.put(Material.WHEAT,             "\u041f\u0448\u0435\u043d\u0438\u0446\u0430");
        NAMES.put(Material.BEEF,              "\u0413\u043e\u0432\u044f\u0434\u0438\u043d\u0430");
        NAMES.put(Material.PORKCHOP,          "\u0421\u0432\u0438\u043d\u0438\u043d\u0430");
        NAMES.put(Material.MUTTON,            "\u0411\u0430\u0440\u0430\u043d\u0438\u043d\u0430");
        NAMES.put(Material.RABBIT,            "\u041a\u0440\u043e\u043b\u0438\u043a\u0430\u0442\u0438\u043d\u0430");
        NAMES.put(Material.CHICKEN,           "\u041a\u0443\u0440\u0438\u0446\u0430");
        NAMES.put(Material.COD,               "\u0422\u0440\u0435\u0441\u043a\u0430");
        NAMES.put(Material.COOKED_BEEF,       "\u0416\u0430\u0440\u0435\u043d\u0430\u044f \u0433\u043e\u0432\u044f\u0434\u0438\u043d\u0430");
        NAMES.put(Material.COOKED_PORKCHOP,   "\u0416\u0430\u0440\u0435\u043d\u0430\u044f \u0441\u0432\u0438\u043d\u0438\u043d\u0430");
        NAMES.put(Material.COOKED_MUTTON,     "\u0416\u0430\u0440\u0435\u043d\u0430\u044f \u0431\u0430\u0440\u0430\u043d\u0438\u043d\u0430");
        NAMES.put(Material.COOKED_CHICKEN,    "\u0416\u0430\u0440\u0435\u043d\u0430\u044f \u043a\u0443\u0440\u0438\u0446\u0430");
        NAMES.put(Material.BREAD,             "\u0425\u043b\u0435\u0431");
        NAMES.put(Material.COOKIE,            "\u041f\u0435\u0447\u0435\u043d\u044c\u0435");
        NAMES.put(Material.CAKE,              "\u0422\u043e\u0440\u0442");
        NAMES.put(Material.PUMPKIN_PIE,       "\u0422\u044b\u043a\u0432\u0435\u043d\u043d\u044b\u0439 \u043f\u0438\u0440\u043e\u0433");

        // Фермер
        NAMES.put(Material.SUGAR_CANE,        "\u0422\u0440\u043e\u0441\u0442\u043d\u0438\u043a");
        NAMES.put(Material.CACTUS,            "\u041a\u0430\u043a\u0442\u0443\u0441");
        NAMES.put(Material.NETHER_WART,       "\u041d\u0435\u0437\u0435\u0440\u043d\u044b\u0439 \u043d\u0430\u0440\u043e\u0441\u0442");
        NAMES.put(Material.COCOA_BEANS,       "\u041a\u0430\u043a\u0430\u043e-\u0431\u043e\u0431\u044b");
        NAMES.put(Material.OAK_LOG,           "\u0414\u0443\u0431\u043e\u0432\u043e\u0435 \u0431\u0440\u0435\u0432\u043d\u043e");
        NAMES.put(Material.BIRCH_LOG,         "\u0411\u0435\u0440\u0451\u0437\u043e\u0432\u043e\u0435 \u0431\u0440\u0435\u0432\u043d\u043e");
        NAMES.put(Material.SPRUCE_LOG,        "\u0415\u043b\u043e\u0432\u043e\u0435 \u0431\u0440\u0435\u0432\u043d\u043e");
        NAMES.put(Material.JUNGLE_LOG,        "\u0414\u0436\u0443\u043d\u0433\u043b\u0435\u0432\u043e\u0435 \u0431\u0440\u0435\u0432\u043d\u043e");
        NAMES.put(Material.ACACIA_LOG,        "\u0410\u043a\u0430\u0446\u0438\u0435\u0432\u043e\u0435 \u0431\u0440\u0435\u0432\u043d\u043e");
        NAMES.put(Material.DARK_OAK_LOG,      "\u0422\u0451\u043c\u043d\u043e\u0434\u0443\u0431\u043e\u0432\u043e\u0435 \u0431\u0440\u0435\u0432\u043d\u043e");
        NAMES.put(Material.MANGROVE_LOG,      "\u041c\u0430\u043d\u0433\u0440\u043e\u0432\u043e\u0435 \u0431\u0440\u0435\u0432\u043d\u043e");
        NAMES.put(Material.CHERRY_LOG,        "\u0412\u0438\u0448\u043d\u0451\u0432\u043e\u0435 \u0431\u0440\u0435\u0432\u043d\u043e");
        NAMES.put(Material.BAMBOO,            "\u0411\u0430\u043c\u0431\u0443\u043a");
        NAMES.put(Material.MOSS_BLOCK,        "\u041c\u043e\u0445\u043e\u0432\u044b\u0439 \u0431\u043b\u043e\u043a");
        NAMES.put(Material.ALLIUM,            "\u0410\u043b\u043b\u0438\u0443\u043c");
    }

    public static String get(Material mat) {
        return NAMES.getOrDefault(mat, mat.name());
    }
}
