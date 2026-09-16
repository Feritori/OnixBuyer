package ru.onixmine.onixbuyer;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import java.util.*;

public class BuyerGUI implements Listener {

    private final OnixBuyerPlugin plugin;

    private static final String TITLE_MAIN = ChatColor.DARK_BLUE + "◈ " + ChatColor.AQUA + ChatColor.BOLD + "OnixBuyer " + ChatColor.DARK_BLUE + "◈";
    private static final String TITLE_HOT  = ChatColor.DARK_BLUE + "◈ " + ChatColor.RED  + ChatColor.BOLD + "Горячие товары " + ChatColor.DARK_BLUE + "◈";
    // Префикс заголовка категории — по нему определяем клики
    private static final String TITLE_CAT_PREFIX = ChatColor.DARK_BLUE + "◈ ";

    public BuyerGUI(OnixBuyerPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    // ── Главное меню ──────────────────────────────────────────────────────────

    public void openMain(Player player) {
        List<ShopCategory> cats = plugin.getCategoryManager().getCategories();
        Inventory inv = Bukkit.createInventory(null, 54, TITLE_MAIN);
        fillBg(inv, 54);

        // Уровень скупщика — слот 4
        BuyerLevel bl = plugin.getBuyerLevel();
        int level = bl.getLevel(player.getUniqueId());
        long xp   = bl.getXp(player.getUniqueId());
        long need = bl.getXpForNext(player.getUniqueId());
        double mult = bl.getMultiplier(player.getUniqueId());

        List<String> lvlLore = new ArrayList<>(Arrays.asList(
                c("&7Уровень: &e" + level),
                c("&7Множитель: &a" + String.format("%.1fx", mult)),
                ""
        ));
        if (need > 0) {
            lvlLore.add(c("&7XP: &f" + xp + " &8/ &f" + need));
            lvlLore.add(buildBar(xp, need));
            lvlLore.add(c("&7Продавай больше для прокачки!"));
        } else {
            lvlLore.add(c("&6★ Максимальный уровень!"));
        }
        inv.setItem(4, makeGlow(Material.EXPERIENCE_BOTTLE,
                c("&e&l⚡ Уровень скупщика"), lvlLore));

        // Горячие товары — слот 49
        List<Material> hot = plugin.getHotItems().getCurrent();
        long sec = plugin.getHotItems().getSecondsUntilNext();
        List<String> hotLore = new ArrayList<>();
        hotLore.add(c("&7Особые товары по &c" + (long) plugin.getConfig().getDouble("hot-item-price", 5000) + " монет &7за шт."));
        hotLore.add(c("&7Макс. продажа: &e" + plugin.getConfig().getInt("hot-item-max-sell", 5) + " шт."));
        hotLore.add("");
        hotLore.add(c("&7Сейчас:"));
        for (Material m : hot) hotLore.add(c("  &f" + ItemNames.get(m)));
        hotLore.add("");
        hotLore.add(c("&8Обновление через: &e" + formatTime(sec)));
        hotLore.add(c("&c▶ Нажми для открытия"));
        inv.setItem(49, makeGlow(Material.FIRE_CHARGE,
                c("&c&l🔥 Горячие товары"), hotLore));

        // Категории — центрируем в рядах 2-4
        int[] catSlots = centeredSlots(cats.size(), 54);
        for (int i = 0; i < cats.size() && i < catSlots.length; i++) {
            ShopCategory cat = cats.get(i);
            int count = cat.isPotions ? cat.potions.size() : cat.items.size();
            inv.setItem(catSlots[i], make(cat.icon, c(cat.title), Arrays.asList(
                    c("&7Предметов: &e" + count),
                    "",
                    c("&b▶ Нажми для открытия")
            )));
        }

        player.openInventory(inv);
    }

    // ── Горячие товары ────────────────────────────────────────────────────────

    public void openHot(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, TITLE_HOT);
        fillBg(inv, 27);

        List<Material> hot = plugin.getHotItems().getCurrent();
        long sec = plugin.getHotItems().getSecondsUntilNext();
        double mult = plugin.getBuyerLevel().getMultiplier(player.getUniqueId());
        double price = plugin.getConfig().getDouble("hot-item-price", 5000.0);
        int maxSell = plugin.getConfig().getInt("hot-item-max-sell", 5);

        inv.setItem(4, make(Material.CLOCK,
                c("&e&l⏱ Обновление"),
                Arrays.asList(
                        c("&7Обновляются каждые &e5 часов"),
                        c("&8Следующее через: &e" + formatTime(sec))
                )));

        int[] hotSlots = {11, 13, 15};
        for (int i = 0; i < hot.size() && i < hotSlots.length; i++) {
            Material mat = hot.get(i);
            double fp = price * mult;
            int has = countItem(player, mat);
            inv.setItem(hotSlots[i], makeGlow(mat, c("&c&l🔥 " + ItemNames.get(mat)), Arrays.asList(
                    c("&7Цена: &e" + String.format("%.0f", fp) + " &7монет за шт."),
                    c("&7Макс. продажа: &e" + maxSell + " шт."),
                    c("&7Итого макс: &a" + String.format("%.0f", fp * maxSell)),
                    "",
                    c("&8У тебя: &f" + has + " шт."),
                    c("&8Твой множитель: &a" + String.format("%.1fx", mult)),
                    "",
                    c("&e▶ Нажми чтобы продать (макс " + maxSell + ")")
            )));
        }

        inv.setItem(22, make(Material.ARROW, c("&7← Назад"), List.of(c("&7Вернуться в главное меню"))));
        player.openInventory(inv);
    }

    // ── Категория предметов ───────────────────────────────────────────────────

    public void openCategory(Player player, ShopCategory cat) {
        if (cat.isPotions) { openPotions(player, cat); return; }

        String title = catTitle(cat);
        List<Material> items = new ArrayList<>(cat.items.keySet());
        int invSize = items.size() > 21 ? 54 : (items.size() > 7 ? 36 : 27);
        int backSlot = invSize - 5;

        Inventory inv = Bukkit.createInventory(null, invSize, title);
        fillBg(inv, invSize);

        double mult = plugin.getBuyerLevel().getMultiplier(player.getUniqueId());
        int lvl = plugin.getBuyerLevel().getLevel(player.getUniqueId());

        inv.setItem(4, make(Material.EXPERIENCE_BOTTLE,
                c("&e&l⚡ Уровень: " + lvl),
                Arrays.asList(
                        c("&7Множитель: &a" + String.format("%.1fx", mult)),
                        c("&a▶ ЛКМ &7— продать всё"),
                        c("&b▶ ПКМ &7— продать 1 шт.")
                )));

        int[] slots = centeredSlots(items.size(), invSize);
        for (int i = 0; i < items.size() && i < slots.length; i++) {
            Material mat = items.get(i);
            double base = cat.items.get(mat);
            double fp = base * mult;
            int has = countItem(player, mat);
            inv.setItem(slots[i], make(mat, c("&f" + ItemNames.get(mat)), Arrays.asList(
                    c("&7Базовая цена: &8" + String.format("%.1f", base) + " монет"),
                    c("&7Твоя цена: &e" + String.format("%.1f", fp) + " монет за шт."),
                    "",
                    c("&8У тебя: &f" + has + " шт."),
                    c("&7Продать всё: &a" + String.format("%.1f", fp * has) + " монет"),
                    "",
                    c("&a▶ ЛКМ &7— продать всё"),
                    c("&b▶ ПКМ &7— продать 1 шт.")
            )));
        }

        inv.setItem(backSlot, make(Material.ARROW, c("&7← Назад"), List.of(c("&7Вернуться в главное меню"))));
        player.openInventory(inv);
    }

    // ── Категория зелий ───────────────────────────────────────────────────────

    public void openPotions(Player player, ShopCategory cat) {
        String title = catTitle(cat);
        List<PotionType> types = new ArrayList<>(cat.potions.keySet());
        int invSize = types.size() > 21 ? 54 : (types.size() > 7 ? 36 : 27);
        int backSlot = invSize - 5;

        Inventory inv = Bukkit.createInventory(null, invSize, title);
        fillBg(inv, invSize);

        double mult = plugin.getBuyerLevel().getMultiplier(player.getUniqueId());
        int lvl = plugin.getBuyerLevel().getLevel(player.getUniqueId());

        inv.setItem(4, make(Material.EXPERIENCE_BOTTLE,
                c("&e&l⚡ Уровень: " + lvl),
                Arrays.asList(
                        c("&7Множитель: &a" + String.format("%.1fx", mult)),
                        c("&7Принимает POTION, SPLASH и LINGERING"),
                        c("&a▶ ЛКМ &7— продать всё"),
                        c("&b▶ ПКМ &7— продать 1 шт.")
                )));

        int[] slots = centeredSlots(types.size(), invSize);
        for (int i = 0; i < types.size() && i < slots.length; i++) {
            PotionType pt = types.get(i);
            double base = cat.potions.get(pt);
            double fp = base * mult;
            int has = countPotion(player, pt);
            inv.setItem(slots[i], makePotionItem(pt, c("&f" + PotionNames.get(pt)), Arrays.asList(
                    c("&7Базовая цена: &8" + String.format("%.1f", base) + " монет"),
                    c("&7Твоя цена: &e" + String.format("%.1f", fp) + " монет за шт."),
                    "",
                    c("&8У тебя: &f" + has + " шт."),
                    c("&7Продать всё: &a" + String.format("%.1f", fp * has) + " монет"),
                    "",
                    c("&a▶ ЛКМ &7— продать всё"),
                    c("&b▶ ПКМ &7— продать 1 шт.")
            )));
        }

        inv.setItem(backSlot, make(Material.ARROW, c("&7← Назад"), List.of(c("&7Вернуться в главное меню"))));
        player.openInventory(inv);
    }

    // ── Обработка кликов ──────────────────────────────────────────────────────

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player player)) return;
        String title = e.getView().getTitle();

        boolean isOurs = title.equals(TITLE_MAIN)
                || title.equals(TITLE_HOT)
                || title.startsWith(TITLE_CAT_PREFIX);
        if (!isOurs) return;

        e.setCancelled(true);
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
        int slot = e.getSlot();

        // ── Главное меню ──────────────────────────────────────────────────────
        if (title.equals(TITLE_MAIN)) {
            if (slot == 49) {
                player.closeInventory();
                Bukkit.getScheduler().runTaskLater(plugin, () -> openHot(player), 1L);
                return;
            }
            List<ShopCategory> cats = plugin.getCategoryManager().getCategories();
            int[] catSlots = centeredSlots(cats.size(), 54);
            for (int i = 0; i < catSlots.length; i++) {
                if (slot == catSlots[i] && i < cats.size()) {
                    final ShopCategory cat = cats.get(i);
                    player.closeInventory();
                    Bukkit.getScheduler().runTaskLater(plugin, () -> openCategory(player, cat), 1L);
                    return;
                }
            }
        }

        // ── Горячие товары ────────────────────────────────────────────────────
        else if (title.equals(TITLE_HOT)) {
            if (slot == 22) {
                player.closeInventory();
                Bukkit.getScheduler().runTaskLater(plugin, () -> openMain(player), 1L);
                return;
            }
            int[] hotSlots = {11, 13, 15};
            List<Material> hot = plugin.getHotItems().getCurrent();
            for (int i = 0; i < hotSlots.length; i++) {
                if (slot == hotSlots[i] && i < hot.size()) {
                    sellHot(player, hot.get(i));
                    player.closeInventory();
                    Bukkit.getScheduler().runTaskLater(plugin, () -> openHot(player), 1L);
                    return;
                }
            }
        }

        // ── Категория ─────────────────────────────────────────────────────────
        else if (title.startsWith(TITLE_CAT_PREFIX)) {
            ShopCategory cat = findCatByTitle(title);
            if (cat == null) return;

            int invSize = getInvSize(cat);
            int backSlot = invSize - 5;

            if (slot == backSlot) {
                player.closeInventory();
                Bukkit.getScheduler().runTaskLater(plugin, () -> openMain(player), 1L);
                return;
            }

            boolean sellAll = e.getClick() == ClickType.LEFT || e.getClick() == ClickType.SHIFT_LEFT;

            if (cat.isPotions) {
                List<PotionType> types = new ArrayList<>(cat.potions.keySet());
                int[] slots = centeredSlots(types.size(), invSize);
                for (int i = 0; i < slots.length; i++) {
                    if (slot == slots[i] && i < types.size()) {
                        PotionType pt = types.get(i);
                        int amount = sellAll ? countPotion(player, pt) : 1;
                        sellPotions(player, pt, cat.potions.get(pt), amount);
                        player.closeInventory();
                        final ShopCategory fc = cat;
                        Bukkit.getScheduler().runTaskLater(plugin, () -> openCategory(player, fc), 1L);
                        return;
                    }
                }
            } else {
                List<Material> items = new ArrayList<>(cat.items.keySet());
                int[] slots = centeredSlots(items.size(), invSize);
                for (int i = 0; i < slots.length; i++) {
                    if (slot == slots[i] && i < items.size()) {
                        Material mat = items.get(i);
                        int amount = sellAll ? countItem(player, mat) : 1;
                        sellItems(player, mat, cat.items.get(mat), amount);
                        player.closeInventory();
                        final ShopCategory fc = cat;
                        Bukkit.getScheduler().runTaskLater(plugin, () -> openCategory(player, fc), 1L);
                        return;
                    }
                }
            }
        }
    }

    // ── Продажа ───────────────────────────────────────────────────────────────

    private void sellHot(Player player, Material mat) {
        int maxSell = plugin.getConfig().getInt("hot-item-max-sell", 5);
        double price = plugin.getConfig().getDouble("hot-item-price", 5000.0);
        double fp = price * plugin.getBuyerLevel().getMultiplier(player.getUniqueId());
        int sell = Math.min(countItem(player, mat), maxSell);
        if (sell <= 0) { player.sendMessage(c("&cУ тебя нет &f" + ItemNames.get(mat) + "&c!")); return; }
        removeItem(player, mat, sell);
        double earned = fp * sell;
        plugin.getEconomy().depositPlayer(player, earned);
        boolean lvlUp = plugin.getBuyerLevel().addXp(player.getUniqueId(),
                sell * plugin.getConfig().getLong("xp-per-item", 1) * 10);
        player.sendMessage(c("&6[OnixBuyer] &aПродано &e" + sell + "x &f" + ItemNames.get(mat)
                + " &aза &e" + String.format("%.0f", earned) + " &aмонет!"));
        if (lvlUp) notifyLevelUp(player);
    }

    private void sellItems(Player player, Material mat, double basePrice, int amount) {
        if (amount <= 0) { player.sendMessage(c("&cУ тебя нет &f" + ItemNames.get(mat) + "&c!")); return; }
        double earned = basePrice * plugin.getBuyerLevel().getMultiplier(player.getUniqueId()) * amount;
        removeItem(player, mat, amount);
        plugin.getEconomy().depositPlayer(player, earned);
        boolean lvlUp = plugin.getBuyerLevel().addXp(player.getUniqueId(),
                amount * plugin.getConfig().getLong("xp-per-item", 1));
        player.sendMessage(c("&6[OnixBuyer] &aПродано &e" + amount + "x &f" + ItemNames.get(mat)
                + " &aза &e" + String.format("%.1f", earned) + " &aмонет!"));
        if (lvlUp) notifyLevelUp(player);
    }

    private void sellPotions(Player player, PotionType pt, double basePrice, int amount) {
        if (amount <= 0) { player.sendMessage(c("&cУ тебя нет зелья &f" + PotionNames.get(pt) + "&c!")); return; }
        double earned = basePrice * plugin.getBuyerLevel().getMultiplier(player.getUniqueId()) * amount;
        removePotions(player, pt, amount);
        plugin.getEconomy().depositPlayer(player, earned);
        boolean lvlUp = plugin.getBuyerLevel().addXp(player.getUniqueId(),
                amount * plugin.getConfig().getLong("xp-per-item", 1));
        player.sendMessage(c("&6[OnixBuyer] &aПродано &e" + amount + "x &f" + PotionNames.get(pt)
                + " &aза &e" + String.format("%.1f", earned) + " &aмонет!"));
        if (lvlUp) notifyLevelUp(player);
    }

    private void notifyLevelUp(Player player) {
        int lvl = plugin.getBuyerLevel().getLevel(player.getUniqueId());
        double mult = plugin.getBuyerLevel().getMultiplier(player.getUniqueId());
        player.sendMessage(c("&6&l✦ LEVEL UP! &eСкупщик уровень " + lvl
                + " &8→ &aмножитель &f" + String.format("%.1fx", mult)));
        player.playSound(player.getLocation(), org.bukkit.Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
    }

    // ── Утилиты ───────────────────────────────────────────────────────────────

    private String catTitle(ShopCategory cat) {
        return TITLE_CAT_PREFIX + c(cat.title) + " " + ChatColor.DARK_BLUE + "◈";
    }

    private ShopCategory findCatByTitle(String title) {
        for (ShopCategory cat : plugin.getCategoryManager().getCategories()) {
            if (title.equals(catTitle(cat))) return cat;
        }
        return null;
    }

    private int getInvSize(ShopCategory cat) {
        int count = cat.isPotions ? cat.potions.size() : cat.items.size();
        return count > 21 ? 54 : (count > 7 ? 36 : 27);
    }

    /** Центрированные слоты внутри рамки (столбцы 1-7, ряды 1..totalRows-2) */
    private int[] centeredSlots(int count, int invSize) {
        int totalRows = invSize / 9;
        int itemRows  = totalRows - 2;
        int maxPerRow = 7;
        count = Math.min(count, itemRows * maxPerRow);
        List<Integer> result = new ArrayList<>();
        int remaining = count;
        for (int row = 1; row <= itemRows && remaining > 0; row++) {
            int inRow = Math.min(remaining, maxPerRow);
            int offset = (maxPerRow - inRow) / 2;
            for (int i = 0; i < inRow; i++) result.add(row * 9 + 1 + offset + i);
            remaining -= inRow;
        }
        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    private int countItem(Player player, Material mat) {
        int count = 0;
        for (ItemStack item : player.getInventory().getContents())
            if (item != null && item.getType() == mat) count += item.getAmount();
        return count;
    }

    private void removeItem(Player player, Material mat, int amount) {
        int left = amount;
        ItemStack[] contents = player.getInventory().getContents();
        for (int i = 0; i < contents.length && left > 0; i++) {
            if (contents[i] != null && contents[i].getType() == mat) {
                int take = Math.min(contents[i].getAmount(), left);
                contents[i].setAmount(contents[i].getAmount() - take);
                left -= take;
            }
        }
        player.getInventory().setContents(contents);
    }

    private int countPotion(Player player, PotionType pt) {
        int count = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null) continue;
            if (isPotionMat(item.getType()) && getPotionType(item) == pt) count += item.getAmount();
        }
        return count;
    }

    private void removePotions(Player player, PotionType pt, int amount) {
        int left = amount;
        ItemStack[] contents = player.getInventory().getContents();
        for (int i = 0; i < contents.length && left > 0; i++) {
            ItemStack item = contents[i];
            if (item == null) continue;
            if (isPotionMat(item.getType()) && getPotionType(item) == pt) {
                int take = Math.min(item.getAmount(), left);
                item.setAmount(item.getAmount() - take);
                left -= take;
            }
        }
        player.getInventory().setContents(contents);
    }

    private boolean isPotionMat(Material mat) {
        return mat == Material.POTION || mat == Material.SPLASH_POTION || mat == Material.LINGERING_POTION;
    }

    private PotionType getPotionType(ItemStack item) {
        if (!(item.getItemMeta() instanceof PotionMeta pm)) return null;
        return pm.getBasePotionType();
    }

    private ItemStack makePotionItem(PotionType pt, String name, List<String> lore) {
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.setBasePotionType(pt);
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        item.setItemMeta(meta);
        return item;
    }

    private String buildBar(long cur, long max) {
        int bars = 20, filled = max > 0 ? (int)(cur * bars / max) : 0;
        StringBuilder sb = new StringBuilder("§8[");
        for (int i = 0; i < bars; i++) sb.append(i < filled ? "§e|" : "§8|");
        return sb.append("§8]").toString();
    }

    private String formatTime(long seconds) {
        long h = seconds / 3600, m = (seconds % 3600) / 60, s = seconds % 60;
        if (h > 0) return h + "ч " + m + "м";
        if (m > 0) return m + "м " + s + "с";
        return s + "с";
    }

    private void fillBg(Inventory inv, int size) {
        ItemStack bg   = glass(Material.BLUE_STAINED_GLASS_PANE);
        ItemStack dark = glass(Material.CYAN_STAINED_GLASS_PANE);
        for (int i = 0; i < size; i++) inv.setItem(i, bg);
        for (int i = 0; i < 9; i++) inv.setItem(i, dark);
        for (int i = size - 9; i < size; i++) inv.setItem(i, dark);
        for (int i = 0; i < size; i += 9) inv.setItem(i, dark);
        for (int i = 8; i < size; i += 9) inv.setItem(i, dark);
    }

    private ItemStack glass(Material mat) {
        ItemStack i = new ItemStack(mat);
        ItemMeta m = i.getItemMeta();
        m.setDisplayName(" ");
        m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        i.setItemMeta(m);
        return i;
    }

    private ItemStack make(Material mat, String name, List<String> lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        if (lore != null) meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack makeGlow(Material mat, String name, List<String> lore) {
        ItemStack item = make(mat, name, lore);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }

    private String c(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
