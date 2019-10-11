package com.akrog.tolometgui.model.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Station")
class StationEntity {
    @PrimaryKey
    @NonNull
    public String id;
    @NonNull
    public String code;
    @NonNull
    public String name;
    @NonNull
    public String provider;
    @NonNull
    public Double latitude;
    @NonNull
    public Double longitude;
    public String updated;
}
