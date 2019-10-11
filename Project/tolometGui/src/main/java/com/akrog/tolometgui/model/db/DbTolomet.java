package com.akrog.tolometgui.model.db;

import com.akrog.tolomet.Utils;
import com.akrog.tolomet.providers.SpotProviderType;
import com.akrog.tolomet.providers.WindProviderType;
import com.akrog.tolometgui.Tolomet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(version = DbTolomet.VERSION, entities = {StationEntity.class, SpotEntity.class})
public abstract class DbTolomet extends RoomDatabase {
    public static final int VERSION = 14;
    public static final String NAME = "Tolomet.db";
    public static final String ASSET = "databases/Tolomet.db";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static DbTolomet instance;

    public abstract StationDao stationDao();

    public abstract SpotDao spotDao();

    public abstract StatsDao statsDao();

    synchronized public static DbTolomet getInstance() {
        if( instance == null )
            instance = Room
                .databaseBuilder(Tolomet.getAppContext(), DbTolomet.class, NAME)
                .createFromAsset(ASSET)
                .addMigrations(buildMigrations())
                //.fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        return instance;
    }

    private static Migration[] buildMigrations() {
        Migration[] migrations = new Migration[VERSION-1];
        for( int i = 1; i < VERSION; i++ )
            migrations[i-1] = new OverrideMigration(i, VERSION);
        return migrations;
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

    private static class OverrideMigration extends Migration {
        OverrideMigration(int startVersion, int endVersion) {
            super(startVersion, endVersion);
        }

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            try(
                InputStream is = Tolomet.getAppContext().getAssets().open(DbTolomet.ASSET);
                OutputStream os = new FileOutputStream(database.getPath());
            ) {
                Utils.copy(is, os);
                database.setVersion(endVersion);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
