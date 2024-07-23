package com.bekvon.bukkit.residence.api;

import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;

import java.util.ArrayList;
import java.util.UUID;

public interface ResidencePlayerInterface {
    ArrayList<String> getResidenceList(String player);

    ArrayList<String> getResidenceList(String player, boolean showhidden);

    PermissionGroup getGroup(String player);

    int getMaxResidences(String player);

    int getMaxSubzones(String player);

    int getMaxRents(String player);

    ResidencePlayer getResidencePlayer(String player);

    int getMaxSubzoneDepth(String player);

    ArrayList<String> getResidenceList(UUID uuid);
}
