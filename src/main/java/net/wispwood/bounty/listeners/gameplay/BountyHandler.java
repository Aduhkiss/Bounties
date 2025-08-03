package net.wispwood.bounty.listeners.gameplay;

import net.wispwood.bounty.BountiesManager;
import net.wispwood.bounty.BP;
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
            BP.getEcon().depositPlayer(killer, hit.getAmount());
            //killer.sendMessage("§bYou received §c$" + hit.getAmount() + " §bfor killing §c" + ev.getEntity().getName() + "§b!");

            killer.sendMessage(BP.getInstance().getMessages().getMessage(
                    "bounty_claimed_message",
                    "%hit_amount%", String.valueOf(hit.getAmount()),
                    "%hit_target%", ev.getEntity().getName()
            ));

            BountiesManager.removeBounty(ev.getEntity());

            if(BP.getInstance().getConfig().getBoolean("playerHeads.canDrop")) {
                if(r.nextDouble() <= BP.getInstance().getConfig().getDouble("playerHeads.dropChance")) {
                    ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                    SkullMeta meta = (SkullMeta) head.getItemMeta();
                    meta.setOwningPlayer(ev.getEntity());
                    meta.setDisplayName(BP.getInstance().getMessages().getMessage(
                            "skull_itemstack_name",
                            "%playername%", ev.getEntity().getName())
                    );
                    head.setItemMeta(meta);

                    killer.getWorld().dropItemNaturally(ev.getPlayer().getLocation(), head);
                }
            }


        }
    }
}
