package com.akrog.tolometgui2.model.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "Meteo", primaryKeys = {"station", "stamp"})
public class MeteoEntity {
    @NonNull
    public String station;
    @NonNull
    public long stamp;
    public Integer dir;
    public Float med;
    public Float max;
    public Float hum;
    public Float temp;
    public Float pres;
}
