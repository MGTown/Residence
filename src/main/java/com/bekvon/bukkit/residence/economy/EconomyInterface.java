package com.bekvon.bukkit.residence.economy;

import org.bukkit.entity.Player;

public interface EconomyInterface {
    double getBalance(Player player);

    double getBalance(String playerName);

    boolean canAfford(String playerName, double amount);

    boolean canAfford(Player player, double amount);

    boolean add(String playerName, double amount);

    boolean subtract(String playerName, double amount);

    boolean transfer(String playerFrom, String playerTo, double amount);

    String getName();

    String format(double amount);

}
