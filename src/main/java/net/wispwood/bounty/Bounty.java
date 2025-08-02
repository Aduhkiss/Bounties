package net.wispwood.bounty;

import net.milkbowl.vault.economy.Economy;
import net.wispwood.bounty.cmd.BountyCmd;
import net.wispwood.bounty.cmd.ReloadCmd;
import net.wispwood.bounty.listeners.BountyHandler;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Bounty extends JavaPlugin {

    private static Bounty instance;
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

        getServer().getPluginManager().registerEvents(new BountyHandler(), this);
    }
    public MessagesManager getMessages() {
        return messages;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        instance = null;
    }
    public static Bounty getInstance() {
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
