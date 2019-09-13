package com.akrog.tolometgui.model.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "Travel", primaryKeys = {"station", "date"})
public class TravelEntity {
    @NonNull
    public String station;
    @NonNull
    public String date;
}
