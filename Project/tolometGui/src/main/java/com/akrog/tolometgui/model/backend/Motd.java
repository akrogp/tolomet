package com.akrog.tolometgui.model.backend;

public class Motd extends BackendNotification {
    private long stamp;
    private String msg;

    public long getStamp() {
        return stamp;
    }

    public void setStamp(long stamp) {
        this.stamp = stamp;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
