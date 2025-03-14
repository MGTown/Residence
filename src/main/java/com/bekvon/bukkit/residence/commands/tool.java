package com.bekvon.bukkit.residence.commands;

import com.bekvon.bukkit.residence.LocaleManager;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.cmd;
import com.bekvon.bukkit.residence.containers.lm;
import net.Zrips.CMILib.FileHandler.ConfigReader;
import org.bukkit.command.CommandSender;

import java.util.List;

public class tool implements cmd {

    @Override
    @CommandAnnotation(simple = true, priority = 1600)
    public Boolean perform(Residence plugin, CommandSender sender, String[] args, boolean resadmin) {
        plugin.msg(sender, lm.General_Separator);
        plugin.msg(sender, lm.Select_Tool, plugin.getConfigManager().getSelectionTool().getName());
        plugin.msg(sender, lm.General_InfoTool, plugin.getConfigManager().getInfoTool().getName());
        plugin.msg(sender, lm.General_Separator);
        return true;
    }

    @Override
    public void getLocale() {
        ConfigReader c = Residence.getInstance().getLocaleManager().getLocaleConfig();
        c.get("Description", "Shows residence selection and info tool names");
        c.get("Info", List.of("&eUsage: &6/res tool"));
        LocaleManager.addTabCompleteMain(this);
    }
}
