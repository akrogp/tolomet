package com.akrog.tolometgui2.model.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.akrog.tolomet.Station;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Dao
public abstract class TravelDao {
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertTravel(TravelEntity travel);

    @Query("SELECT COUNT(*) FROM Travel WHERE station = :station AND date = :date")
    public abstract int findTravel(String station, String date);

    public boolean hasTravelled(Station station, long dayStamp) {
        return findTravel(station.getId(), df.format(new Date(dayStamp))) > 0;
    }

    public void saveTravel(Station station, long dayStamp) {
        TravelEntity ent = new TravelEntity();
        ent.station = station.getId();
        ent.date = df.format(new Date(dayStamp));
        insertTravel(ent);
    }
}
