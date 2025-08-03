package net.wispwood.bounty.cmd;

import net.wispwood.bounty.BP;
import net.wispwood.bounty.gui.TopGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TopCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(BP.getInstance().getMessages().getMessage(
                    "error_sender_isnt_a_player"
            ));
            return false;
        }
        ((Player) sender).openInventory(TopGui.get());
        return true;
    }
}
