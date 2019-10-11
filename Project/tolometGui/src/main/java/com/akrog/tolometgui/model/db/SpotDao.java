package com.akrog.tolometgui.model.db;

import com.akrog.tolomet.Spot;
import com.akrog.tolomet.SpotType;
import com.akrog.tolomet.providers.SpotProviderType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public abstract class SpotDao {
    @Query("SELECT * FROM Spot WHERE latitude BETWEEN :lat1 AND :lat2 AND longitude BETWEEN :lon1 AND :lon2")
    protected abstract List<SpotEntity> findGeoEntities(double lat1, double lon1, double lat2, double lon2);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void updateEntities(Collection<SpotEntity> entities);

    @Query("DELETE FROM Spot WHERE provider = :provider")
    protected abstract void deleteProvider(String provider);

    public List<Spot> findGeoSpots(double lat1, double lon1, double lat2, double lon2) {
        double tmp;
        if (lat2 < lat1) {
            tmp = lat1; lat1 = lat2; lat2 = tmp;
        }
        if (lon2 < lon1) {
            tmp = lon1; lon1 = lon2; lon2 = tmp;
        }
        List<SpotEntity> entities = findGeoEntities(lat1, lon1, lat2, lon2);
        return entities2spots(entities);
    }

    public void updateSpots(SpotProviderType type, List<Spot> spots) {
        deleteProvider(type.name());
        List<SpotEntity> entities = spots2entities(spots);
        updateEntities(entities);
    }

    private List<Spot> entities2spots(List<SpotEntity> entities) {
        List<Spot> spots = new ArrayList<>(entities.size());
        for( SpotEntity entity : entities )
            spots.add(entity2spot(entity));
        return spots;
    }

    private List<SpotEntity> spots2entities(List<Spot> spots) {
        Date now = new Date();
        List<SpotEntity> entities = new ArrayList<>(spots.size());
        for( Spot spot : spots ) {
            if( spot.getUpdated() == null )
                spot.setUpdated(now);
            entities.add(spot2entity(spot));
        }
        return entities;
    }

    private Spot entity2spot(SpotEntity entity) {
        Spot spot = new Spot();
        spot.setId(entity.id);
        spot.setName(entity.name);
        spot.setDesc(entity.desc);
        spot.setProvider(SpotProviderType.valueOf(entity.provider));
        spot.setType(SpotType.valueOf(entity.type));
        spot.setLatitude(entity.latitude);
        spot.setLongitude(entity.longitude);
        return spot;
    }

    private SpotEntity spot2entity(Spot spot) {
        SpotEntity entity = new SpotEntity();
        entity.id = spot.getId();
        entity.name = spot.getName();
        entity.desc = spot.getDesc();
        entity.provider = spot.getProvider().name();
        entity.type = spot.getType().name();
        entity.latitude = spot.getLatitude();
        entity.longitude = spot.getLongitude();
        if( spot.getUpdated() == null )
            spot.setUpdated(new Date());
        entity.updated = DbTolomet.DATE_FORMAT.format(spot.getUpdated());
        return entity;
    }
}
