package net.wispwood.bounty.cmd;

import net.wispwood.bounty.BountiesManager;
import net.wispwood.bounty.Bounty;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BountyCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if((!(sender instanceof Player))) {
            sender.sendMessage("§cYou must be a player to run this.");
            return false;
        }

        if(args.length != 2) {
            sender.sendMessage("§cUsage: /bounty <Player> <Amount>");
            return false;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        double amount = 0.00;
        try {
            amount = Double.parseDouble(args[1]);
        } catch(IllegalArgumentException ex) {
            sender.sendMessage("§cThat is not a number!");
            return false;
        }

        if(Bounty.getEcon().getBalance((OfflinePlayer) sender) < amount) {
            sender.sendMessage("§cYou don't have enough money!");
            return false;
        }
        if(Bounty.getInstance().getConfig().getBoolean("bounty.targetMustBeOnline")) {
            if(!target.isOnline()) {
                sender.sendMessage("§cThat player is not online right now!");
                return false;
            }
        }


        BountiesManager.addBounty((Player) sender, target, amount);
        sender.sendMessage("§6You placed a bounty on " + target.getName() + " for $" + amount + "!");
        if(target.isOnline()) {
            ((Player) target).sendMessage("§bYou have had a bounty placed on you by §c" + sender.getName() + "§b for §c$" + amount + "§b!");
        }
        Bounty.getEcon().withdrawPlayer((OfflinePlayer) sender, amount);

        return false;
    }
}
