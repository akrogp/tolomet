package com.akrog.tolometgui2.model.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Travel")
public class TravelEntity {
    @PrimaryKey
    @NonNull
    public String station;
    public String date;
}
