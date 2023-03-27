package com.akrog.tolometgui.model.backend;

public abstract class BackendNotification {
    private int vmin;
    private int vmax;

    public int getVmin() {
        return vmin;
    }

    public void setVmin(int vmin) {
        this.vmin = vmin;
    }

    public int getVmax() {
        return vmax;
    }

    public void setVmax(int vmax) {
        this.vmax = vmax;
    }
}
