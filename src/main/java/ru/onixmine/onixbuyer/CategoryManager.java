package ru.onixmine.onixbuyer;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.potion.PotionType;

import java.util.*;

public class CategoryManager {

    private final OnixBuyerPlugin plugin;
    private final List<ShopCategory> categories = new ArrayList<>();

    public CategoryManager(OnixBuyerPlugin plugin) {
        this.plugin = plugin;
        load();
    }

    public void reload() {
        plugin.reloadConfig();
        categories.clear();
        load();
    }

    private void load() {
        ConfigurationSection sec = plugin.getConfig().getConfigurationSection("categories");
        if (sec == null) {
            plugin.getLogger().warning("Секция 'categories' не найдена в config.yml!");
            return;
        }
        for (String key : sec.getKeys(false)) {
            try {
                ConfigurationSection cat = sec.getConfigurationSection(key);
                if (cat == null) continue;

                String title = cat.getString("title", "&7" + key);
                String iconStr = cat.getString("icon", "CHEST");
                Material icon = Material.matchMaterial(iconStr);
                if (icon == null) { icon = Material.CHEST; }

                if (cat.contains("potions")) {
                    Map<PotionType, Double> potionPrices = new LinkedHashMap<>();
                    ConfigurationSection ps = cat.getConfigurationSection("potions");
                    if (ps != null) {
                        for (String ptKey : ps.getKeys(false)) {
                            try {
                                potionPrices.put(PotionType.valueOf(ptKey.toUpperCase()), ps.getDouble(ptKey));
                            } catch (IllegalArgumentException e) {
                                plugin.getLogger().warning("Неизвестный PotionType '" + ptKey + "' в " + key);
                            }
                        }
                    }
                    categories.add(ShopCategory.ofPotions(key, title, icon, potionPrices));
                } else {
                    Map<Material, Double> items = new LinkedHashMap<>();
                    ConfigurationSection is = cat.getConfigurationSection("items");
                    if (is != null) {
                        for (String matKey : is.getKeys(false)) {
                            Material mat = Material.matchMaterial(matKey.toUpperCase());
                            if (mat == null) {
                                plugin.getLogger().warning("Неизвестный Material '" + matKey + "' в " + key);
                                continue;
                            }
                            items.put(mat, is.getDouble(matKey));
                        }
                    }
                    categories.add(ShopCategory.ofItems(key, title, icon, items));
                }
            } catch (Exception e) {
                plugin.getLogger().warning("Ошибка загрузки категории '" + key + "': " + e.getMessage());
            }
        }
        plugin.getLogger().info("Загружено категорий: " + categories.size());
    }

    public List<ShopCategory> getCategories() { return Collections.unmodifiableList(categories); }
    public ShopCategory getByKey(String key)  { return categories.stream().filter(c -> c.key.equals(key)).findFirst().orElse(null); }
}
