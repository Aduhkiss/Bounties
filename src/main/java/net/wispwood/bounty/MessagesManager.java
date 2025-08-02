package net.wispwood.bounty;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MessagesManager {
    private FileConfiguration messagesConfig;
    private File messagesFile;

    public MessagesManager() {
        createMessagesFile();
    }

    private void createMessagesFile() {
        messagesFile = new File(Bounty.getInstance().getDataFolder(), "messages.yml");

        if(!messagesFile.exists()) {
            Bukkit.getLogger().info("messages.yml does not exist! Creating one...");
            Bounty.getInstance().saveResource("messages.yml", false); // just copy the default file from the jar
        }

        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }
    public FileConfiguration getConfig() {
        return messagesConfig;
    }
    public String getMessage(String key, String... placeholders) {
        String prefix = getConfig().getString("prefix", "");
        String baseMessage = getConfig().getString(key);

        if (baseMessage == null) {
            Bukkit.getLogger().severe("[Bounty] Missing message key: " + key + " in messages.yml!");
            Bukkit.getLogger().severe("[Bounty] Please open a ticket in my discord server! --> https://discord.gg/aMvZSJewAE"); // hard-coded cause its probablly not gonna change....
            return ChatColor.RED + "[Missing message: " + key + "]";
        }

        String message = prefix + baseMessage;

        for (int i = 0; i < placeholders.length; i += 2) {
            message = message.replace(placeholders[i], placeholders[i + 1]);
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public void save() {
        try {
            messagesConfig.save(messagesFile);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Could not save messages.yml!");
            e.printStackTrace();
        }
    }

    public boolean reload() {
        try {
            messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
        } catch(Exception e) {
            return false;
        }
        return true;
    }
}
