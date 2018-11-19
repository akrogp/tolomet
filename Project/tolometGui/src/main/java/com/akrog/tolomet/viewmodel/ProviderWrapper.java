package com.akrog.tolomet.viewmodel;

import com.akrog.tolomet.providers.WindProviderType;

public class ProviderWrapper {
    private final WindProviderType type;
    private int iconId;

    public ProviderWrapper(WindProviderType type) {
        this.type = type;
    }

    public WindProviderType getType() {
        return type;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getIconId() {
        return iconId;
    }
}
