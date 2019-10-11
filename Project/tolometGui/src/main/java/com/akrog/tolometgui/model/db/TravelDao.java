package com.akrog.tolometgui.model.db;

import com.akrog.tolomet.Station;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public abstract class TravelDao {
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void insertTravel(TravelEntity travel);

    @Query("SELECT COUNT(*) FROM Travel WHERE station = :station AND date = :date")
    protected abstract int findTravel(String station, String date);

    @Query("DELETE FROM Travel WHERE date < :date")
    protected abstract void trim(String date);

    public boolean hasTravelled(Station station, long dayStamp) {
        return findTravel(station.getId(), df.format(new Date(dayStamp))) > 0;
    }

    public void saveTravel(Station station, long dayStamp) {
        TravelEntity ent = new TravelEntity();
        ent.station = station.getId();
        ent.date = df.format(new Date(dayStamp));
        insertTravel(ent);
    }

    public void trim(Date date) {
        trim(df.format(date));
    }
}
