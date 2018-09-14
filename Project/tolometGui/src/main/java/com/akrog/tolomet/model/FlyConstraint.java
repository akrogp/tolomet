package com.akrog.tolomet.model;

/**
 * Created by gorka on 18/05/16.
 */
public class FlyConstraint {
    private String station;
    private int minDir;
    private int maxDir;
    private int minWind;
    private int maxWind;
    private int maxHum;

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public int getMinDir() {
        return minDir;
    }

    public void setMinDir(int minDir) {
        this.minDir = minDir;
    }

    public int getMaxDir() {
        return maxDir;
    }

    public void setMaxDir(int maxDir) {
        this.maxDir = maxDir;
    }

    public int getMinWind() {
        return minWind;
    }

    public void setMinWind(int minWind) {
        this.minWind = minWind;
    }

    public int getMaxWind() {
        return maxWind;
    }

    public void setMaxWind(int maxWind) {
        this.maxWind = maxWind;
    }

    public int getMaxHum() {
        return maxHum;
    }

    public void setMaxHum(int maxHum) {
        this.maxHum = maxHum;
    }

    public boolean isValid() {
        return station != null && !station.isEmpty() && (minDir != maxDir || minWind != maxWind) && maxHum >= 0 && maxHum <= 100;
    }
}
