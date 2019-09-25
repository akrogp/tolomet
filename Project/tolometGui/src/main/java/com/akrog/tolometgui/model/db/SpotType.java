package com.akrog.tolometgui.model.db;

import com.akrog.tolometgui.R;

public enum SpotType {
    TAKEOFF(R.drawable.ic_wind),
    LANDING(R.drawable.ic_wind);

    SpotType(int iconId) {
        this.iconId = iconId;
    }

    public int getIconId() {
        return iconId;
    }

    private final int iconId;
}
