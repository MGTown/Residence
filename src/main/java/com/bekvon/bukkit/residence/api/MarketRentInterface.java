package com.bekvon.bukkit.residence.api;

import com.bekvon.bukkit.residence.economy.rent.RentedLand;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public interface MarketRentInterface {
    Set<ClaimedResidence> getRentableResidences();

    Set<ClaimedResidence> getCurrentlyRentedResidences();

    RentedLand getRentedLand(String landName);

    List<String> getRentedLands(String playerName);

    void setForRent(Player player, String landName, int amount, int days, boolean AllowRenewing, boolean resadmin);

    void setForRent(Player player, String landName, int amount, int days, boolean AllowRenewing, boolean StayInMarket, boolean resadmin);

    void setForRent(Player player, String landName, int amount, int days, boolean AllowRenewing, boolean StayInMarket, boolean AllowAutoPay, boolean resadmin);

    void rent(Player player, String landName, boolean repeat, boolean resadmin);

    void removeFromForRent(Player player, String landName, boolean resadmin);

    void unrent(Player player, String landName, boolean resadmin);

    void removeFromRent(String landName);

    void removeRentable(String landName);

    boolean isForRent(String landName);

    boolean isRented(String landName);

    String getRentingPlayer(String landName);

    int getCostOfRent(String landName);

    boolean getRentableRepeatable(String landName);

    boolean getRentedAutoRepeats(String landName);

    int getRentDays(String landName);

    void checkCurrentRents();

    void setRentRepeatable(Player player, String landName, boolean value, boolean resadmin);

    void setRentedRepeatable(Player player, String landName, boolean value, boolean resadmin);

    int getRentCount(String player);

    int getRentableCount(String player);
}
