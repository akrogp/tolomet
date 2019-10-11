package com.akrog.tolomet;

import com.akrog.tolomet.providers.SpotProviderType;

import java.util.Date;

public class Spot {
    private String id;
    private double latitude, longitude;
    private String name, desc;
    private SpotType type;
    private Date updated;
    private SpotProviderType provider;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public SpotType getType() {
        return type;
    }

    public void setType(SpotType type) {
        this.type = type;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public SpotProviderType getProvider() {
        return provider;
    }

    public void setProvider(SpotProviderType provider) {
        this.provider = provider;
    }
}
