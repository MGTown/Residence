package com.bekvon.bukkit.residence.permissions;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public interface PermissionsInterface {
    String getPlayerGroup(Player player);

    String getPlayerGroup(String player, String world);

    boolean hasPermission(OfflinePlayer player, String permission, String world);
}
