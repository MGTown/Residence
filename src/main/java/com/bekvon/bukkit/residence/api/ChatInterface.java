package com.bekvon.bukkit.residence.api;

import com.bekvon.bukkit.residence.chat.ChatChannel;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;

public interface ChatInterface {
    boolean setChannel(String player, String resName);

    boolean setChannel(String player, ClaimedResidence res);

    boolean removeFromChannel(String player);

    ChatChannel getChannel(String channel);

    ChatChannel getPlayerChannel(String player);

}
