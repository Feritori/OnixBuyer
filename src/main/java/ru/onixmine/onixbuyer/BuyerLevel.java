package ru.onixmine.onixbuyer;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Хранит уровень и XP скупщика для каждого игрока.
 * Уровень влияет на множитель цены: уровень N -> 1.0 + N * 0.1
 */
public class BuyerLevel {

    private final OnixBuyerPlugin plugin;
    // uuid -> [level, xp]
    private final Map<UUID, long[]> data = new HashMap<>();

    private File file;
    private FileConfiguration cfg;

    public BuyerLevel(OnixBuyerPlugin plugin) {
        this.plugin = plugin;
        load();
    }

    public int getLevel(UUID uuid) {
        return (int) get(uuid)[0];
    }

    public long getXp(UUID uuid) {
        return get(uuid)[1];
    }

    public long getXpForNext(UUID uuid) {
        int level = getLevel(uuid);
        return plugin.getConfig().getLong("level-xp." + (level + 1), -1L);
    }

    /** Множитель цены: ур.0 = 1.0x, ур.1 = 1.1x, ур.2 = 1.2x ... */
    public double getMultiplier(UUID uuid) {
        return 1.0 + getLevel(uuid) * 0.1;
    }

    /** Добавить XP, вернуть true если был левел-ап */
    public boolean addXp(UUID uuid, long amount) {
        long[] d = get(uuid);
        d[1] += amount;
        boolean levelUp = false;
        while (true) {
            long need = plugin.getConfig().getLong("level-xp." + ((int) d[0] + 1), -1L);
            if (need < 0 || d[1] < need) break;
            d[1] -= need;
            d[0]++;
            levelUp = true;
        }
        if (Math.random() < 0.05) save();
        return levelUp;
    }

    private long[] get(UUID uuid) {
        return data.computeIfAbsent(uuid, k -> new long[]{0, 0});
    }

    public void save() {
        cfg.set("players", null);
        for (Map.Entry<UUID, long[]> e : data.entrySet()) {
            String base = "players." + e.getKey();
            cfg.set(base + ".level", e.getValue()[0]);
            cfg.set(base + ".xp",    e.getValue()[1]);
        }
        try { cfg.save(file); } catch (IOException ex) {
            plugin.getLogger().warning("Ошибка сохранения buyer_levels.yml");
        }
    }

    private void load() {
        file = new File(plugin.getDataFolder(), "buyer_levels.yml");
        if (!file.exists()) {
            try { file.createNewFile(); } catch (IOException ignored) {}
        }
        cfg = YamlConfiguration.loadConfiguration(file);
        var sec = cfg.getConfigurationSection("players");
        if (sec == null) return;
        for (String key : sec.getKeys(false)) {
            try {
                UUID uuid = UUID.fromString(key);
                long level = cfg.getLong("players." + key + ".level", 0);
                long xp    = cfg.getLong("players." + key + ".xp", 0);
                data.put(uuid, new long[]{level, xp});
            } catch (Exception ignored) {}
        }
    }
}
