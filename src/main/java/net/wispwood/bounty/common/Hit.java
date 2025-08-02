package net.wispwood.bounty.common;

import org.bukkit.OfflinePlayer;

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
