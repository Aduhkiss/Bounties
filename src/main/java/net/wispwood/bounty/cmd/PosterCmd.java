package net.wispwood.bounty.cmd;

import net.wispwood.bounty.BP;
import net.wispwood.bounty.BountiesManager;
import net.wispwood.bounty.maprenderer.WantedPosterRenderer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

public class PosterCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(BP.getInstance().getMessages().getMessage(
                    "error_sender_isnt_a_player"
            ));
            return false;
        }
        Player player = (Player) sender;
        if(args.length != 1) {
            sender.sendMessage(BP.getInstance().getMessages().getMessage(
                    "poster_command_usage"
            ));
            return false;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        double pooled = BountiesManager.getPooledHitPrice(target);
        if(pooled == 0.00) {
            sender.sendMessage(BP.getInstance().getMessages().getMessage(
                    "error_no_bounties"
            ));
            return false;
        }

        ItemStack map = new ItemStack(Material.FILLED_MAP);
        MapMeta meta = (MapMeta) map.getItemMeta();

        MapView view = Bukkit.createMap(((Player) sender).getWorld());
        view.getRenderers().clear();
        view.addRenderer(new WantedPosterRenderer(target, pooled));
        view.setTrackingPosition(false);

        meta.setMapView(view);
        meta.setDisplayName(BP.getInstance().getMessages().getMessage(
                "poster_display_name",
                "%name%", target.getName()
        ));
        map.setItemMeta(meta);

        player.getInventory().addItem(map);
        player.sendMessage(BP.getInstance().getMessages().getMessage(
                "poster_received_message",
                "%name%", target.getName()
        ));
        return true;
    }
}
