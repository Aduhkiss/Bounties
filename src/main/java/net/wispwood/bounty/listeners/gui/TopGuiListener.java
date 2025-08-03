package net.wispwood.bounty.listeners.gui;

import net.wispwood.bounty.BP;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TopGuiListener implements Listener {
    @EventHandler
    public void onInvClick(InventoryClickEvent ev) {
        if(ev.getCurrentItem() == null) { return; }
        if(!(ev.getView().getTitle().equals(BP.getInstance().getMessages().getMessage("guiname_bountytop")))) { return; }

        ev.setCancelled(true);
        return;
    }
}
