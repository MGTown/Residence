package com.bekvon.bukkit.residence.gui;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.FlagPermissions.FlagCombo;
import com.bekvon.bukkit.residence.protection.FlagPermissions.FlagState;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import net.Zrips.CMILib.Enchants.CMIEnchantEnum;
import net.Zrips.CMILib.GUI.CMIGuiButton;
import net.Zrips.CMILib.GUI.GUIManager.GUIClickType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.Map.Entry;

public class setFlagInfo {

    private final ClaimedResidence residence;
    private final Player player;
    private String targetPlayer = null;
    private final LinkedHashMap<Flags, List<String>> description = new LinkedHashMap<>();
    private final List<CMIGuiButton> buttons = new ArrayList<>();
    private boolean admin = false;

    public setFlagInfo(ClaimedResidence residence, Player player, boolean admin) {
        this.residence = residence;
        this.player = player;
        this.admin = admin;
        fillFlagDescriptions();
    }

    public setFlagInfo(ClaimedResidence residence, Player player, String targetPlayer, boolean admin) {
        this.residence = residence;
        this.player = player;
        this.targetPlayer = targetPlayer;
        this.admin = admin;
        fillFlagDescriptions();
    }

    public void setAdmin(boolean state) {
        this.admin = state;
    }

    public boolean isAdmin() {
        return this.admin;
    }

    public ClaimedResidence getResidence() {
        return this.residence;
    }

    public Player getPlayer() {
        return this.player;
    }

    private void fillFlagDescriptions() {
        for (Flags flag : Flags.values()) {
            List<String> lore = new ArrayList<>();
            int i = 0;
            StringBuilder sentence = new StringBuilder();

            for (String oneWord : flag.getDesc().split(" ")) {
                sentence.append(oneWord).append(" ");
                if (i > 4) {
                    lore.add(ChatColor.YELLOW + sentence.toString());
                    sentence = new StringBuilder();
                    i = 0;
                }
                i++;
            }
            lore.add(ChatColor.YELLOW + sentence.toString());
            description.put(flag, lore);
        }
    }

    public void recalculate() {
        if (targetPlayer == null)
            recalculateResidence();
        else
            recalculatePlayer();
    }

    private void recalculateResidence() {
        buttons.clear();

        List<String> flags = residence.getPermissions().getPosibleFlags(player, true, this.admin);

        Map<String, Boolean> resFlags = new HashMap<>();
        Map<String, Object> TempPermMap = new LinkedHashMap<>();

        Map<String, Boolean> globalFlags = Residence.getInstance().getPermissionManager().getAllFlags().getFlags();

        for (Entry<String, Boolean> one : residence.getPermissions().getFlags().entrySet()) {
            if (flags.contains(one.getKey())) {
                resFlags.put(one.getKey(), one.getValue());
            }
        }

        for (Entry<String, Boolean> one : globalFlags.entrySet()) {
            String fname = one.getKey();

            Flags flag = Flags.getFlag(fname);

            if (flag != null && !flag.isGlobalyEnabled())
                continue;

            if (!flags.contains(one.getKey())) {
                continue;
            }

            if (resFlags.containsKey(one.getKey()))
                TempPermMap.put(one.getKey(), resFlags.get(one.getKey()) ? FlagState.TRUE : FlagState.FALSE);
            else
                TempPermMap.put(one.getKey(), FlagState.NEITHER);
        }

        if (targetPlayer == null)
            TempPermMap.remove("admin");

        TempPermMap = Residence.getInstance().getSortingManager().sortByKeyASC(TempPermMap);

//	FlagData flagData = Residence.getInstance().getFlagUtilManager().getFlagData();

        LinkedHashMap<String, Object> permMap = new LinkedHashMap<>();
        permMap.putAll(TempPermMap);

        String cmdPrefix = admin ? "resadmin" : "res";

        int i = 0;
        for (Entry<String, Object> one : permMap.entrySet()) {
            i = i > 44 ? 0 : i;

            CMIGuiButton button = new CMIGuiButton(i, updateLook(one.getKey())) {
                @Override
                public void click(GUIClickType type) {
                    String command = "true";
                    switch (type) {
                        case Left:
                            break;
                        case Right:
                            command = "false";
                            break;
                        case RightShift:
                        case LeftShift:
                        case MiddleMouse:
                            command = "remove";
                            break;
                        default:
                            break;
                    }

                    Bukkit.dispatchCommand(player, cmdPrefix + " set " + residence.getName() + " " + one.getKey() + " " + command);
                    if (Residence.getInstance().getConfigManager().isConsoleLogsShowFlagChanges())
                        Residence.getInstance().consoleMessage(player.getName() + " issued server command: /" + cmdPrefix + " set " + residence.getName() + " " + one.getKey() + " " + command);
                    updateLooks();
                }

                @Override
                public void updateLooks() {
                    this.setItem(updateLook(one.getKey()));
                    hideItemFlags();
                    this.update();
                }
            };
            button.hideItemFlags();

            buttons.add(button);
            i++;
        }

    }

    private void recalculatePlayer() {
        Map<String, Boolean> globalFlags = new HashMap<>();
        for (Flags oneFlag : Flags.values()) {
            globalFlags.put(oneFlag.toString(), oneFlag.isEnabled());
        }

        List<String> flags = residence.getPermissions().getPosibleFlags(player, false, this.admin);

        Map<String, Boolean> resFlags = new HashMap<>();

        for (Entry<String, Boolean> one : residence.getPermissions().getFlags().entrySet()) {
            if (flags.contains(one.getKey()))
                resFlags.put(one.getKey(), one.getValue());
        }

        if (targetPlayer != null) {

            Set<String> PosibleResPFlags = FlagPermissions.getAllPosibleFlags();
            Map<String, Boolean> temp = new HashMap<>();
            for (String one : PosibleResPFlags) {
                if (globalFlags.containsKey(one))
                    temp.put(one, globalFlags.get(one));
            }
            globalFlags = temp;

            Map<String, Boolean> pFlags = residence.getPermissions().getPlayerFlags(targetPlayer);

            if (pFlags != null)
                resFlags.putAll(pFlags);
        }

        LinkedHashMap<String, Object> TempPermMap = new LinkedHashMap<>();

        for (Entry<String, Boolean> one : globalFlags.entrySet()) {
            if (!flags.contains(one.getKey()))
                continue;

            if (resFlags.containsKey(one.getKey()))
                TempPermMap.put(one.getKey(), resFlags.get(one.getKey()) ? FlagState.TRUE : FlagState.FALSE);
            else
                TempPermMap.put(one.getKey(), FlagState.NEITHER);
        }

        TempPermMap = (LinkedHashMap<String, Object>) Residence.getInstance().getSortingManager().sortByKeyASC(TempPermMap);

        LinkedHashMap<String, Object> permMap = new LinkedHashMap<>();
        permMap.putAll(TempPermMap);

        String targetPlayerName = targetPlayer == null ? "" : " " + targetPlayer;
        String cmdPrefix = admin ? "resadmin" : "res";

        int i = 0;
        for (Entry<String, Object> one : permMap.entrySet()) {

            i = i > 44 ? 0 : i;

            CMIGuiButton button = new CMIGuiButton(i, updateLook(one.getKey())) {
                @Override
                public void click(GUIClickType type) {
                    String command = "true";
                    switch (type) {
                        case Left:
                            break;
                        case Right:
                            command = "false";
                            break;
                        case RightShift:
                        case LeftShift:
                        case MiddleMouse:
                            command = "remove";
                            break;
                        default:
                            break;
                    }

                    Bukkit.dispatchCommand(player, cmdPrefix + " pset " + residence.getName() + targetPlayerName + " " + one.getKey() + " " + command);
                    if (Residence.getInstance().getConfigManager().isConsoleLogsShowFlagChanges())
                        Residence.getInstance().consoleMessage(player.getName() + " issued server command: /" + cmdPrefix + " pset " + residence.getName() + targetPlayerName + " " + one
                                .getKey() + " " + command);
                    updateLooks();
                }

                @Override
                public void updateLooks() {
                    this.setItem(updateLook(one.getKey()));
                    hideItemFlags();
                    this.update();
                }

            };
            i++;

            button.hideItemFlags();
            buttons.add(button);
        }

    }

    private ItemStack updateLook(String flagName) {

        Boolean have = null;
        ResidencePermissions fp = this.residence.getPermissions();

        if (this.targetPlayer != null) {
            if (fp.playerHas(targetPlayer, flagName, FlagCombo.OnlyTrue))
                have = true;
            else if (fp.playerHas(targetPlayer, flagName, FlagCombo.OnlyFalse))
                have = false;
        } else {
            if (fp.has(flagName, FlagCombo.OnlyTrue))
                have = true;
            else if (fp.has(flagName, FlagCombo.OnlyFalse))
                have = false;
        }

        FlagState state = FlagState.NEITHER;
        if (have == null) {

        } else if (have)
            state = FlagState.TRUE;
        else
            state = FlagState.FALSE;

        ItemStack miscInfo = Residence.getInstance().getConfigManager().getGuiBottonStates(state).clone();

        FlagData flagData = Residence.getInstance().getFlagUtilManager().getFlagData();

        if (flagData.contains(flagName))
            miscInfo = flagData.getItem(flagName).clone();

        if (state == FlagState.TRUE) {
            ItemMeta im = miscInfo.getItemMeta();
            if (im != null) {
                im.addEnchant(CMIEnchantEnum.LUCK_OF_THE_SEA.getEnchantment(), 1, true);
                miscInfo.setItemMeta(im);
            }
        } else
            miscInfo.removeEnchantment(CMIEnchantEnum.LUCK_OF_THE_SEA.getEnchantment());

        Flags flag = Flags.getFlag(flagName);
        if (flag != null)
            flagName = flag.getName();
        if (flagName == null)
            flagName = "Unknown";

        ItemMeta MiscInfoMeta = miscInfo.getItemMeta();

        // Can it be null?
        if (MiscInfoMeta == null)
            return miscInfo;
        MiscInfoMeta.setDisplayName(ChatColor.GREEN + flagName);
        List<String> lore = new ArrayList<>();
        String variable = switch (state) {
            case FALSE -> Residence.getInstance().msg(lm.General_False);
            case TRUE -> Residence.getInstance().msg(lm.General_True);
            case NEITHER -> Residence.getInstance().msg(lm.General_Removed);
            default -> "";
        };
        lore.add(Residence.getInstance().msg(lm.General_FlagState, variable));

        if (description.containsKey(flag))
            lore.addAll(description.get(flag));
        lore.addAll(Residence.getInstance().msgL(lm.Gui_Actions));
        MiscInfoMeta.setLore(lore);
        miscInfo.setItemMeta(MiscInfoMeta);

        return miscInfo;
    }

    public List<CMIGuiButton> getButtons() {
        return buttons;
    }
}
