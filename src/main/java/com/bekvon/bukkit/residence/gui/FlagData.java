package com.bekvon.bukkit.residence.gui;

import net.Zrips.CMILib.Items.CMIMaterial;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class FlagData {

    private final HashMap<String, ItemStack> items = new HashMap<>();

    public void addFlagButton(String flag, ItemStack item) {
        this.items.put(flag, item == null ? CMIMaterial.STONE.newItemStack() : item);
    }

    public void removeFlagButton(String flag) {
        this.items.remove(flag);
    }

    public boolean contains(String flag) {
        return items.containsKey(flag);
    }

    public ItemStack getItem(String flag) {
        return items.get(flag);
    }

}
