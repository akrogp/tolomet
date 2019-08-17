package com.akrog.tolometgui2.model.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.akrog.tolometgui2.Tolomet;

@Database(version = DbMeteo.VERSION, entities = {MeteoEntity.class, TravelEntity.class})
public abstract class DbMeteo extends RoomDatabase {
    public static final int VERSION = 1;
    public static final String NAME = "Meteo2.db";
    private static DbMeteo instance;

    public abstract MeteoDao meteoDao();

    synchronized public static DbMeteo getInstance() {
        if( instance == null )
            instance = Room.databaseBuilder(Tolomet.getAppContext(), DbMeteo.class, NAME).build();
        return instance;
    }
}
