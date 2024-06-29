package com.akrog.tolometgui.model.db;

import android.database.sqlite.SQLiteDatabase;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.akrog.tolomet.providers.SpotProviderType;
import com.akrog.tolomet.providers.WindProviderType;
import com.akrog.tolometgui.Tolomet;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Database(version = DbTolomet.VERSION, entities = {StationEntity.class, SpotEntity.class})
public abstract class DbTolomet extends RoomDatabase {
    public static final int VERSION = 37;
    public static final String NAME = "Tolomet.db";
    public static final String ASSET = "databases/Tolomet.db";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static DbTolomet instance;

    public abstract StationDao stationDao();

    public abstract SpotDao spotDao();

    public abstract StatsDao statsDao();

    synchronized public static DbTolomet getInstance() {
        if( instance == null ) {
            //updVersion();
            instance = Room
                .databaseBuilder(Tolomet.getAppContext(), DbTolomet.class, NAME)
                .createFromAsset(ASSET)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        }
        return instance;
    }

    private static void updVersion() {
        File file = Tolomet.getAppContext().getDatabasePath(NAME);
        try(SQLiteDatabase db = SQLiteDatabase.openDatabase(file.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE)) {
            db.setVersion(VERSION);
            db.getVersion();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class ProviderInfo {
        private int count;
        private Date date;
        private String provider;
        private WindProviderType windProviderType;
        private SpotProviderType spotProviderType;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public WindProviderType getWindProviderType() {
            return windProviderType;
        }

        public void setWindProviderType(WindProviderType windProviderType) {
            this.windProviderType = windProviderType;
        }

        public SpotProviderType getSpotProviderType() {
            return spotProviderType;
        }

        public void setSpotProviderType(SpotProviderType spotProviderType) {
            this.spotProviderType = spotProviderType;
        }

        public String getProvider() {
            return provider;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }
    }
}
