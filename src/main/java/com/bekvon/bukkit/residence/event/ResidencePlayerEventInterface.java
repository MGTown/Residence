package com.bekvon.bukkit.residence.event;

import org.bukkit.entity.Player;

public interface ResidencePlayerEventInterface {
    boolean isAdmin();

    boolean isPlayer();

    Player getPlayer();
}
