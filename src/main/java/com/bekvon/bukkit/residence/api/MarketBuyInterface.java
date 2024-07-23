package com.bekvon.bukkit.residence.api;

import org.bukkit.entity.Player;

import java.util.Map;

public interface MarketBuyInterface {

    Map<String, Integer> getBuyableResidences();

    boolean putForSale(String areaname, int amount);

    void buyPlot(String areaname, Player player, boolean resadmin);

    void removeFromSale(String areaname);

    boolean isForSale(String areaname);

    int getSaleAmount(String name);
}
