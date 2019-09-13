package com.akrog.tolometgui.model.db;

import com.akrog.tolometgui.Tolomet;

import java.util.Calendar;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(version = DbMeteo.VERSION, entities = {MeteoEntity.class, TravelEntity.class})
public abstract class DbMeteo extends RoomDatabase {
    public static final int VERSION = 2;
    public static final String NAME = "Meteo2.db";
    private static DbMeteo instance;

    public abstract MeteoDao meteoDao();

    public abstract TravelDao travelDao();

    synchronized public static DbMeteo getInstance() {
        if( instance == null )
            instance = Room
                .databaseBuilder(Tolomet.getAppContext(), DbMeteo.class, NAME)
                .fallbackToDestructiveMigration()
                .build();
        return instance;
    }

    public void trim() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        long stamp = cal.getTimeInMillis();
        meteoDao().trim(stamp);
        travelDao().trim(cal.getTime());
    }
}
