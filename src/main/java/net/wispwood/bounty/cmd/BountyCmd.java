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
            sender.sendMessage(Bounty.getInstance().getMessages().getMessage(
                    "error_sender_isnt_a_player"
            ));
            return false;
        }

        if(args.length != 2) {
            sender.sendMessage(Bounty.getInstance().getMessages().getMessage(
                    "bounty_command_usage"
            ));
            return false;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        double amount = 0.00;
        try {
            amount = Double.parseDouble(args[1]);
        } catch(IllegalArgumentException ex) {
            sender.sendMessage(Bounty.getInstance().getMessages().getMessage(
                    "error_not_a_number"
            ));
            return false;
        }

        if(Bounty.getEcon().getBalance((OfflinePlayer) sender) < amount) {
            sender.sendMessage(Bounty.getInstance().getMessages().getMessage(
                    "error_not_enough_money"
            ));
            return false;
        }
        if(Bounty.getInstance().getConfig().getBoolean("bounty.targetMustBeOnline")) {
            if(!target.isOnline()) {
                sender.sendMessage(Bounty.getInstance().getMessages().getMessage(
                        "error_target_not_online"
                ));
                return false;
            }
        }


        BountiesManager.addBounty((Player) sender, target, amount);
        //sender.sendMessage("§6You placed a bounty on " + target.getName() + " for $" + amount + "!");
        sender.sendMessage(Bounty.getInstance().getMessages().getMessage(
                "success_bounty_placed",
                "%target%", target.getName(),
                "%amount%", String.valueOf(amount)
        ));
        if(target.isOnline()) {
            ((Player) target).sendMessage(Bounty.getInstance().getMessages().getMessage(
                    "bounty_placed_on_me",
                    "%amount%", String.valueOf(amount),
                    "%attacker%", sender.getName()
            ));
        }
        Bounty.getEcon().withdrawPlayer((OfflinePlayer) sender, amount);

        return false;
    }
}
