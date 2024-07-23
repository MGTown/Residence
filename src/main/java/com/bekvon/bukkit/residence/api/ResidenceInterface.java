package com.bekvon.bukkit.residence.api;

import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public interface ResidenceInterface {
    ClaimedResidence getByLoc(Location loc);

    ClaimedResidence getByName(String name);

    String getSubzoneNameByRes(ClaimedResidence res);

    void addShop(ClaimedResidence res);

    void addShop(String res);

    void removeShop(ClaimedResidence res);

    void removeShop(String res);

    List<ClaimedResidence> getShops();

    boolean addResidence(String name, Location loc1, Location loc2);

    boolean addResidence(String name, String owner, Location loc1, Location loc2);

    boolean addResidence(Player player, String name, Location loc1, Location loc2, boolean resadmin);

    boolean addResidence(Player player, String name, boolean resadmin);
}
