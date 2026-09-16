package ru.onixmine.onixbuyer;

import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Горячие товары — обновляются каждые 5 часов.
 * Цена фиксированная (hot-item-price), макс продажа 5 штук.
 */
public class HotItems {

    // Пул предметов для горячих товаров (без черепков)
    private static final List<Material> POOL = List.of(
        Material.DIAMOND, Material.EMERALD, Material.NETHERITE_SCRAP,
        Material.BLAZE_ROD, Material.GHAST_TEAR, Material.ENDER_PEARL,
        Material.NETHER_STAR, Material.SHULKER_SHELL, Material.ELYTRA,
        Material.TOTEM_OF_UNDYING, Material.HEART_OF_THE_SEA,
        Material.NAUTILUS_SHELL, Material.PRISMARINE_CRYSTALS,
        Material.WITHER_SKELETON_SKULL, Material.DRAGON_BREATH,
        Material.GOLDEN_CARROT, Material.ENCHANTED_GOLDEN_APPLE,
        Material.ANCIENT_DEBRIS, Material.AMETHYST_SHARD,
        Material.ECHO_SHARD, Material.DISC_FRAGMENT_5
    );

    private final OnixBuyerPlugin plugin;
    private List<Material> current = new ArrayList<>();
    private long lastUpdate = 0;

    public HotItems(OnixBuyerPlugin plugin) {
        this.plugin = plugin;
        rotate();
        scheduleRotation();
    }

    public List<Material> getCurrent() {
        return Collections.unmodifiableList(current);
    }

    public long getSecondsUntilNext() {
        long intervalTicks = plugin.getConfig().getLong("hot-items-interval-ticks", 360000L);
        long intervalMs = intervalTicks * 50L;
        long elapsed = System.currentTimeMillis() - lastUpdate;
        long remaining = intervalMs - elapsed;
        return Math.max(0, remaining / 1000);
    }

    private void rotate() {
        int count = plugin.getConfig().getInt("hot-items-count", 3);
        List<Material> pool = new ArrayList<>(POOL);
        Collections.shuffle(pool);
        current = new ArrayList<>(pool.subList(0, Math.min(count, pool.size())));
        lastUpdate = System.currentTimeMillis();
        plugin.getLogger().info("[OnixBuyer] Горячие товары обновлены: " + current);
    }

    private void scheduleRotation() {
        long interval = plugin.getConfig().getLong("hot-items-interval-ticks", 360000L);
        new BukkitRunnable() {
            @Override public void run() { rotate(); }
        }.runTaskTimer(plugin, interval, interval);
    }
}
