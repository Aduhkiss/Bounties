package net.wispwood.bounty.common;

import org.bukkit.OfflinePlayer;

/**
 * Blueprint for how bounties work on the back-end. This is used to make it easier to convert
 * between storing hits in ram, as yaml, or in a database
 * @author Atticus Zambrana
 * @since 1.0
 */

public class Hit {
    private OfflinePlayer caller;
    private OfflinePlayer target;
    private double amount;

    public Hit(OfflinePlayer caller, OfflinePlayer target, double amount) {
        this.caller = caller;
        this.target = target;
        this.amount = amount;
    }

    public OfflinePlayer getCaller() {
        return caller;
    }

    public OfflinePlayer getTarget() {
        return target;
    }

    public double getAmount() {
        return amount;
    }
}
