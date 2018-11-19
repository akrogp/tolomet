package com.akrog.tolomet.viewmodel;

import com.akrog.tolomet.providers.WindProviderType;

public class ProviderWrapper {
    private final WindProviderType type;
    private int iconId;
    private int stations;
    private String date;
    private boolean checked;

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

    public void setStations(int stations) {
        this.stations = stations;
    }

    public int getStations() {
        return stations;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }
}
