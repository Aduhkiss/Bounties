package net.wispwood.bounty.gui;

import net.wispwood.bounty.BountiesManager;
import net.wispwood.bounty.BP;
import net.wispwood.bounty.common.Hit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class TopGui {
    public static Inventory get() {
        Inventory inv = Bukkit.createInventory(null, (6 * 9), BP.getInstance().getMessages().getMessage("guiname_bountytop"));

        int i = 0;
        for(Hit h : BountiesManager.getHits()) {
            ItemStack item = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setOwningPlayer(Bukkit.getOfflinePlayer(h.getTarget().getUniqueId()));
            meta.setDisplayName(BP.getInstance().getMessages().getMessageNoPrefix(
                    "guiskull_target_head_name",
                    "%name%", h.getTarget().getName()
            ));
            item.setItemMeta(meta);
            inv.setItem(i, item);
        }

        return inv;
    }
}
