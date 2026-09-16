package ru.onixmine.onixbuyer;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class OnixBuyerPlugin extends JavaPlugin {

    private Economy         economy;
    private BuyerLevel      buyerLevel;
    private HotItems        hotItems;
    private BuyerGUI        buyerGUI;
    private CategoryManager categoryManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        RegisteredServiceProvider<Economy> rsp =
                getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp != null) {
            economy = rsp.getProvider();
            getLogger().info("Vault подключён: " + economy.getName());
        } else {
            getLogger().severe("Vault не найден! Плагин не будет работать.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        categoryManager = new CategoryManager(this);
        buyerLevel      = new BuyerLevel(this);
        hotItems        = new HotItems(this);
        buyerGUI        = new BuyerGUI(this);

        getCommand("buyer").setExecutor(this);
        getLogger().info("OnixBuyer включён.");
    }

    @Override
    public void onDisable() {
        if (buyerLevel != null) buyerLevel.save();
        getLogger().info("OnixBuyer выключен.");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        // /buyer reload — перезагрузить конфиг и категории
        if (args.length >= 1 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("onixbuyer.reload")) {
                sender.sendMessage("§cНет прав.");
                return true;
            }
            categoryManager.reload();
            sender.sendMessage("§a[OnixBuyer] Конфиг и категории перезагружены!");
            return true;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Только для игроков.");
            return true;
        }
        buyerGUI.openMain(player);
        return true;
    }

    public Economy         getEconomy()         { return economy; }
    public BuyerLevel      getBuyerLevel()       { return buyerLevel; }
    public HotItems        getHotItems()         { return hotItems; }
    public BuyerGUI        getBuyerGUI()         { return buyerGUI; }
    public CategoryManager getCategoryManager()  { return categoryManager; }
}
