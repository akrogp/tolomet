package com.akrog.tolometgui2.model.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Travel")
public class TravelEntity {
    @PrimaryKey
    public String station;
    public Date date;
}
