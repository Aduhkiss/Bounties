package net.wispwood.bounty.cmd;

import net.wispwood.bounty.Bounty;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        Bounty.getInstance().getMessages().reload();
        sender.sendMessage(Bounty.getInstance().getMessages().getMessage(
                "reload_success"
        ));
        return true;
    }
}
