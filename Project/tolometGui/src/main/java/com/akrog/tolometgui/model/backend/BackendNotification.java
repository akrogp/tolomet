package com.akrog.tolometgui.model.backend;

public abstract class BackendNotification {
    private int from;

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }
}
