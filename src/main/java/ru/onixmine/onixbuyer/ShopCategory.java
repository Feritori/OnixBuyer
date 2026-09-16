package ru.onixmine.onixbuyer;

import org.bukkit.Material;
import org.bukkit.potion.PotionType;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShopCategory {

    public final String key;
    public final String title;
    public final Material icon;
    public final boolean isPotions;
    public final Map<Material, Double> items;
    public final Map<PotionType, Double> potions;

    private ShopCategory(String key, String title, Material icon, boolean isPotions,
                         Map<Material, Double> items, Map<PotionType, Double> potions) {
        this.key       = key;
        this.title     = title;
        this.icon      = icon;
        this.isPotions = isPotions;
        this.items     = items;
        this.potions   = potions;
    }

    public static ShopCategory ofItems(String key, String title, Material icon, Map<Material, Double> items) {
        return new ShopCategory(key, title, icon, false, items, new LinkedHashMap<>());
    }

    public static ShopCategory ofPotions(String key, String title, Material icon, Map<PotionType, Double> potions) {
        return new ShopCategory(key, title, icon, true, new LinkedHashMap<>(), potions);
    }
}
