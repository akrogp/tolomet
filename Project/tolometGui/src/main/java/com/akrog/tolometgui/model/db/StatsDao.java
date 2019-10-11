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
        int off = entities.size();
        List<DbTolomet.ProviderInfo> counts = new ArrayList<>();
        counts.addAll(entities2info(entities));
        entities = getSpotCounts();
        counts.addAll(entities2info(entities));
        int i = 0;
        for( ; i < off; i++ )
            counts.get(i).setWindProviderType(WindProviderType.valueOf(counts.get(i).getProvider()));
        for( ; i < counts.size(); i++ )
            counts.get(i).setSpotProviderType(SpotProviderType.valueOf(counts.get(i).getProvider()));

        /*DbTolomet.ProviderInfo elliot = new DbTolomet.ProviderInfo();
        elliot.setSpotProviderType(SpotProviderType.ElliottParagliding);
        counts.add(elliot);*/

        return counts;
    }

    private static List<DbTolomet.ProviderInfo> entities2info(List<InfoEntity> entities) {
        List<DbTolomet.ProviderInfo> result = new ArrayList<>(entities.size());
        for( InfoEntity entity : entities ) {
            DbTolomet.ProviderInfo info = new DbTolomet.ProviderInfo();
            info.setProvider(entity.provider);
            try {
                info.setDate(entity.updated == null ? null : DbTolomet.DATE_FORMAT.parse(entity.updated));
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
