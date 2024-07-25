package com.bekvon.bukkit.residence.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import net.Zrips.CMILib.FileHandler.ConfigReader;
import com.bekvon.bukkit.residence.LocaleManager;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.cmd;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;

public class setowner implements cmd {

    @Override
    @CommandAnnotation(simple = false, priority = 5500)
    public Boolean perform(Residence plugin, CommandSender sender, String[] args, boolean resadmin) {

        if (args.length < 2)
            return false;

        if (!resadmin) {
            plugin.msg(sender, lm.General_NoPermission);
            return true;
        }

        boolean keepFlags = args.length > 2 && args[2].equalsIgnoreCase("-keepflags");

        ClaimedResidence area = plugin.getResidenceManager().getByName(args[0]);

        if (area == null) {
            plugin.msg(sender, lm.Invalid_Residence);
            return null;
        }

        if (!plugin.isPlayerExist(sender, args[1], true)) {
            return null;
        }

        area.getPermissions().setOwner(args[1], !keepFlags);
        if (plugin.getRentManager().isForRent(area))
            plugin.getRentManager().removeRentable(area);
        if (plugin.getTransactionManager().isForSale(area))
            plugin.getTransactionManager().removeFromSale(area);

        if (!keepFlags)
            area.getPermissions().applyDefaultFlags();

        plugin.getSignUtil().updateSignResName(area);

        if (area.getParent() == null) {
            plugin.msg(sender, lm.Residence_OwnerChange, args[0], args[1]);
        } else {
            plugin.msg(sender, lm.Subzone_OwnerChange, args[0].split("\\.")[args[0].split("\\.").length - 1], args[1]);
        }

        return true;
    }

    @Override
    public void getLocale() {
        ConfigReader c = Residence.getInstance().getLocaleManager().getLocaleConfig();
        c.get("Description", "Change owner of a residence.");
        c.get("Info", List.of("&eUsage: &6/resadmin setowner [residence] [player] (-keepflags)"));
        LocaleManager.addTabCompleteMain(this, "[cresidence]", "[playername]", "-keepflags");
    }

}
