package net.wispwood.bounty;

import net.milkbowl.vault.economy.Economy;
import net.wispwood.bounty.cmd.BountyCmd;
import net.wispwood.bounty.cmd.PosterCmd;
import net.wispwood.bounty.cmd.ReloadCmd;
import net.wispwood.bounty.cmd.TopCmd;
import net.wispwood.bounty.listeners.gameplay.BountyHandler;
import net.wispwood.bounty.listeners.gui.TopGuiListener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class BP extends JavaPlugin {

    private static BP instance;
    private static Economy econ;
    private MessagesManager messages;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        saveDefaultConfig();

        // then load the messages file please
        messages = new MessagesManager();

        if(!setupEconomy()) {
            getLogger().info("[Bounty]: Disabled plugin! You do not have an ecomomy plugin installed!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getCommand("bounty").setExecutor(new BountyCmd());
        getCommand("bountyreload").setExecutor(new ReloadCmd());
        getCommand("bountytop").setExecutor(new TopCmd());
        getCommand("bountyposter").setExecutor(new PosterCmd());

        getServer().getPluginManager().registerEvents(new BountyHandler(), this);
        getServer().getPluginManager().registerEvents(new TopGuiListener(), this);
    }
    public MessagesManager getMessages() {
        return messages;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        instance = null;
    }
    public static BP getInstance() {
        return instance;
    }

    public static Economy getEcon() {
        return econ;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
