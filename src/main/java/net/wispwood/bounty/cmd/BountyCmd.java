package net.wispwood.bounty.cmd;

import net.wispwood.bounty.BountiesManager;
import net.wispwood.bounty.BP;
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
            sender.sendMessage(BP.getInstance().getMessages().getMessage(
                    "error_sender_isnt_a_player"
            ));
            return false;
        }

        if(args.length != 2) {
            sender.sendMessage(BP.getInstance().getMessages().getMessage(
                    "bounty_command_usage"
            ));
            return false;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        double amount = 0.00;
        try {
            amount = Double.parseDouble(args[1]);
        } catch(IllegalArgumentException ex) {
            sender.sendMessage(BP.getInstance().getMessages().getMessage(
                    "error_not_a_number"
            ));
            return false;
        }

        if(BP.getEcon().getBalance((OfflinePlayer) sender) < amount) {
            sender.sendMessage(BP.getInstance().getMessages().getMessage(
                    "error_not_enough_money"
            ));
            return false;
        }

        double min = BP.getInstance().getConfig().getDouble("limits.minimum_bounty");
        double max = BP.getInstance().getConfig().getDouble("limits.maximum_bounty");
        if(min != -1) {
            if(amount < min) {
                sender.sendMessage(BP.getInstance().getMessages().getMessage(
                        "error_not_meet_minimum",
                        "%minimum%", String.valueOf(min),
                        "%maximum%", String.valueOf(max)
                ));
                return false;
            }
        }
        if(max != -1) {
            if(amount > max) {
                sender.sendMessage(BP.getInstance().getMessages().getMessage(
                        "error_not_meet_maximum",
                        "%minimum%", String.valueOf(min),
                        "%maximum%", String.valueOf(max)
                ));
                return false;
            }
        }

        if(BP.getInstance().getConfig().getBoolean("bounty.targetMustBeOnline")) {
            if(!target.isOnline()) {
                sender.sendMessage(BP.getInstance().getMessages().getMessage(
                        "error_target_not_online"
                ));
                return false;
            }
        }
        if(((Player) sender).getUniqueId() == target.getUniqueId()) {
            sender.sendMessage(BP.getInstance().getMessages().getMessage(
                    "error_cannot_bounty_yourself"
            ));
            return false;
        }

        BountiesManager.addBounty((Player) sender, target, amount);

        sender.sendMessage(BP.getInstance().getMessages().getMessage(
                "success_bounty_placed",
                "%target%", target.getName(),
                "%amount%", String.valueOf(amount)
        ));
        if(target.isOnline() && BP.getInstance().getConfig().getBoolean("bounty.sendMessageToTarget")) {
            ((Player) target).sendMessage(BP.getInstance().getMessages().getMessage(
                    "bounty_placed_on_me",
                    "%amount%", String.valueOf(amount),
                    "%attacker%", sender.getName()
            ));
        }
        BP.getEcon().withdrawPlayer((OfflinePlayer) sender, amount);

        return false;
    }
}
