package net.wispwood.bounty.listeners;

import net.wispwood.bounty.BountiesManager;
import net.wispwood.bounty.Bounty;
import net.wispwood.bounty.common.Hit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Random;

public class BountyHandler implements Listener {
    @EventHandler
    public void onPlayerKill(PlayerDeathEvent ev) {
        Player killer = ev.getEntity().getKiller();
        Random r = new Random();

        if(BountiesManager.hasHit(ev.getEntity())) {
            Hit hit = BountiesManager.getHit(ev.getEntity());
            Bounty.getEcon().depositPlayer(killer, hit.getAmount());
            killer.sendMessage("§bYou received §c$" + hit.getAmount() + " §bfor killing §c" + ev.getEntity().getName() + "§b!");
            BountiesManager.removeBounty(ev.getEntity());

            if(Bounty.getInstance().getConfig().getBoolean("playerHeads.canDrop")) {
                if(r.nextDouble() <= Bounty.getInstance().getConfig().getDouble("playerHeads.dropChance")) {
                    ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                    SkullMeta meta = (SkullMeta) head.getItemMeta();
                    meta.setOwningPlayer(ev.getEntity());
                    meta.setDisplayName("§e" + ev.getEntity().getName() + "'s Head");
                    head.setItemMeta(meta);

                    killer.getWorld().dropItemNaturally(ev.getPlayer().getLocation(), head);
                }
            }


        }
    }
}
