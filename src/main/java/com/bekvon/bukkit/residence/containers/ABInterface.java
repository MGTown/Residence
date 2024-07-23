package com.bekvon.bukkit.residence.containers;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface ABInterface {

    void send(CommandSender sender, String msg);

    void send(Player player, String msg);

    void sendTitle(Player player, Object title, Object subtitle);

    void sendTitle(Player receivingPacket, Object title);
}
