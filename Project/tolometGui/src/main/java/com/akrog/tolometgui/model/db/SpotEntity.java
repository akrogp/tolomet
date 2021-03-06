package com.akrog.tolometgui.model.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Spot")
class SpotEntity {
    @PrimaryKey
    @NonNull
    public String id;
    public double latitude;
    public double longitude;
    public String name;
    public String desc;
    public String type;
    public String updated;
    public String provider;
}
