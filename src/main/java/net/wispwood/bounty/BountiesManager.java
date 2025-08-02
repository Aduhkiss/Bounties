package net.wispwood.bounty;

import net.wispwood.bounty.common.Hit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Handles all Bounty/Hit related logic for the server.
 *
 * This class handles placing, claiming, and keeping track of bounties.
 * It interfaces with the databases/memory and provides helper methods for commands and events.
 *
 * @Author Atticus Zambrana
 * @since 1.0
 */
public class BountiesManager {
    private static List<Hit> _PlacedHits = new ArrayList<>();

    public static void addBounty(Player caller, OfflinePlayer target, double amount) {
        _PlacedHits.add(new Hit(caller, target, amount));
    }

    public static void removeBounty(OfflinePlayer target) {
        _PlacedHits.remove(getHit((Player) target));
    }

    public static Hit getHit(Player target) {
        UUID targetId = target.getUniqueId();
        for (Hit h : _PlacedHits) {
            if (h.getTarget().getUniqueId().equals(targetId)) {
                return h;
            }
        }
        return null;
    }

    public static boolean hasHit(Player target) {
        return getHit(target) != null;
    }
}
