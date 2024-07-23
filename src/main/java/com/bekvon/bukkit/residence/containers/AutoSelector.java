package com.bekvon.bukkit.residence.containers;

import com.bekvon.bukkit.residence.permissions.PermissionGroup;

public class AutoSelector {
    private final PermissionGroup group;
    private final long time;

    public AutoSelector(PermissionGroup group, long time) {
        this.group = group;
        this.time = time;
    }

    public long getTime() {
        return this.time;
    }

    public PermissionGroup getGroup() {
        return this.group;
    }
}
