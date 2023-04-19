package com.akrog.tolometgui.model.backend;

public class ConfigUpdate extends BackendNotification {
    private long stamp;
    private String description;
    private String key;
    private String type;
    private String value;

    public long getStamp() {
        return stamp;
    }

    public void setStamp(long stamp) {
        this.stamp = stamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
