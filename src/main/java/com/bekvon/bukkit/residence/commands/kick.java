package com.bekvon.bukkit.residence.commands;

import com.bekvon.bukkit.residence.LocaleManager;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.*;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.permissions.PermissionManager.ResPerm;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import net.Zrips.CMILib.FileHandler.ConfigReader;
import net.Zrips.CMILib.Locale.LC;
import net.Zrips.CMILib.Version.Teleporters.CMITeleporter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class kick implements cmd {

    @Override
    @CommandAnnotation(priority = 2200)
    public Boolean perform(Residence plugin, CommandSender sender, String[] args, boolean resadmin) {
        if (!(sender instanceof Player player))
            return false;

        if (args.length != 1)
            return false;

        Player targetplayer = Bukkit.getPlayer(args[0]);
        if (targetplayer == null || !player.canSee(targetplayer)) {
            plugin.msg(player, lm.General_NotOnline);
            return true;
        }

        ResidencePlayer rPlayer = plugin.getPlayerManager().getResidencePlayer(player);

        PermissionGroup group = rPlayer.getGroup();

        if (!group.hasKickAccess() && !resadmin) {
            plugin.msg(player, lm.General_NoPermission);
            return true;
        }

        ClaimedResidence res = plugin.getResidenceManager().getByLoc(targetplayer.getLocation());

        if (res == null || !res.isOwner(player) && !resadmin && !res.getPermissions().playerHas(player, Flags.admin, false)) {
            plugin.msg(player, lm.Residence_PlayerNotIn);
            return true;
        }

        if (!res.isOwner(player) && !res.getPermissions().playerHas(player, Flags.admin, false))
            return false;

        if (res.getRaid().isRaidInitialized()) {
            plugin.msg(sender, lm.Raid_cantDo);
            return true;
        }

        if (!res.getPlayersInResidence().contains(targetplayer))
            return false;

        if (ResPerm.command_kick_bypass.hasPermission(targetplayer)) {
            plugin.msg(sender, lm.Residence_CantKick);
            return true;
        }

        Location loc = plugin.getConfigManager().getKickLocation();
        targetplayer.closeInventory();

        if (loc == null)
            loc = res.getOutsideFreeLoc(targetplayer.getLocation(), targetplayer, true);

        if (loc == null) {
            LC.info_IncorrectLocation.getLocale();
            return true;
        }

        CMITeleporter.teleportAsync(targetplayer, loc).thenApply(success -> {
            if (success)
                plugin.msg(targetplayer, lm.Residence_Kicked);
            else
                plugin.msg(targetplayer, lm.General_TeleportCanceled);
            return null;
        });

        return true;
    }

    @Override
    public void getLocale() {
        ConfigReader c = Residence.getInstance().getLocaleManager().getLocaleConfig();
        c.get("Description", "Kicks player from residence.");
        c.get("Info", Arrays.asList("&eUsage: &6/res kick <player>", "You must be the owner or an admin to do this.", "Player should be online."));
        LocaleManager.addTabCompleteMain(this, "[playername]");
    }
}
