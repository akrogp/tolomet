package com.akrog.tolometgui.model.db;

import com.akrog.tolomet.providers.SpotProviderType;
import com.akrog.tolomet.providers.WindProviderType;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public abstract class StatsDao {
    @Query("SELECT provider, updated, COUNT(*) AS cnt FROM Station GROUP BY provider")
    protected abstract List<InfoEntity> getStationCounts();

    @Query("SELECT provider, updated, COUNT(*) AS cnt FROM Spot GROUP BY provider")
    protected abstract List<InfoEntity> getSpotCounts();

    @Transaction
    public List<DbTolomet.ProviderInfo> getProviderCounts() {
        List<InfoEntity> entities = getStationCounts();
        addMissingStations(entities);
        int off = entities.size();
        List<DbTolomet.ProviderInfo> counts = new ArrayList<>();
        counts.addAll(entities2info(entities));
        entities = getSpotCounts();
        addMissingSpots(entities);
        counts.addAll(entities2info(entities));
        int i = 0;
        for( ; i < off; i++ )
            counts.get(i).setWindProviderType(WindProviderType.valueOf(counts.get(i).getProvider()));
        for( ; i < counts.size(); i++ )
            counts.get(i).setSpotProviderType(SpotProviderType.valueOf(counts.get(i).getProvider()));
        return counts;
    }

    private void addMissingStations(List<InfoEntity> entities) {
        for(WindProviderType type : WindProviderType.values())
            if(!findProvider(type.name(), entities)) {
                InfoEntity e = new InfoEntity();
                e.provider = type.name();
                entities.add(e);
            }
    }

    private void addMissingSpots(List<InfoEntity> entities) {
        for(SpotProviderType type : SpotProviderType.values())
            if(!findProvider(type.name(), entities)) {
                InfoEntity e = new InfoEntity();
                e.provider = type.name();
                entities.add(e);
            }
    }

    private boolean findProvider(String name, List<InfoEntity> entities) {
        for(InfoEntity e : entities)
            if(e.provider.equals(name))
                return true;
        return false;
    }

    private static List<DbTolomet.ProviderInfo> entities2info(List<InfoEntity> entities) {
        List<DbTolomet.ProviderInfo> result = new ArrayList<>(entities.size());
        for( InfoEntity entity : entities ) {
            DbTolomet.ProviderInfo info = new DbTolomet.ProviderInfo();
            info.setProvider(entity.provider);
            try {
                info.setDate(entity.updated == null || entity.updated.isEmpty() ? null : DbTolomet.DATE_FORMAT.parse(entity.updated));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            info.setCount(entity.cnt);
            result.add(info);
        }
        return result;
    }

    protected static class InfoEntity {
        public String provider;
        public String updated;
        public int cnt;
    }
}
