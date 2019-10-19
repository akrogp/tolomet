package com.akrog.tolometgui.model.db;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.providers.WindProviderType;
import com.akrog.tolometgui.model.AppSettings;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public abstract class StationDao {
    @Query("SELECT * FROM Station WHERE id = :id")
    protected abstract StationEntity findEntity(String id);

    @Query("SELECT * FROM Station WHERE latitude BETWEEN :lat1 AND :lat2 AND longitude BETWEEN :lon1 AND :lon2")
    protected abstract List<StationEntity> findGeoEntities(double lat1, double lon1, double lat2, double lon2);

    @Query("SELECT * FROM Station WHERE name LIKE :text")
    protected abstract List<StationEntity> searchEntities(String text);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void updateEntities(Collection<StationEntity> entities);

    @Query("DELETE FROM Station WHERE provider = :provider")
    protected abstract void deleteProvider(String provider);

    public Station findStation(String id) {
        StationEntity entity = findEntity(id);
        return entity2station(entity);
    }

    public List<Station> findGeoStations(double lat1, double lon1, double lat2, double lon2) {
        double tmp;
        if( lat2 < lat1 ) {
            tmp = lat1; lat1 = lat2; lat2 = tmp;
        }
        if( lon2 < lon1 ) {
            tmp = lon1; lon1 = lon2; lon2 = tmp;
        }
        List<StationEntity> entities = findGeoEntities(lat1, lon1, lat2, lon2);
        return entities2stations(entities);
    }

    public List<Station> findCloseStations(double lat, double lon, double degrees) {
        return findGeoStations(lat-degrees, lon-degrees, lat+degrees, lon+degrees);
    }

    public List<Station> searchStations(String text) {
        if( text == null || text.isEmpty() )
            return new ArrayList<>();
        List<StationEntity> entities = searchEntities("%"+text+"%");
        return entities2stations(entities);
    }

    public void updateStations(WindProviderType type, List<Station> stations) {
        deleteProvider(type.name());
        List<StationEntity> entities = stations2entities(stations);
        updateEntities(entities);
    }

    private static Station entity2station(StationEntity entity) {
        if( entity == null )
            return null;
        Set<String> favs = AppSettings.getInstance().getFavorites();
        Station station = new Station();
        station.setCode(entity.code);
        station.setName(entity.name);
        station.setProviderType(WindProviderType.valueOf(entity.provider));
        station.setLatitude(entity.latitude);
        station.setLongitude(entity.longitude);
        station.setFavorite(favs.contains(station.getId()));
        try {
            station.setUpdated(entity.updated == null || entity.updated.isEmpty() ? null : DbTolomet.DATE_FORMAT.parse(entity.updated));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return station;
    }

    private static StationEntity station2entity(Station station) {
        StationEntity entity = new StationEntity();
        entity.id = station.getId();
        entity.code = station.getCode();
        entity.name = station.getName();
        entity.provider = station.getProviderType().name();
        entity.latitude = station.getLatitude();
        entity.longitude = station.getLongitude();
        if( station.getUpdated() == null )
            station.setUpdated(new Date());
        entity.updated = DbTolomet.DATE_FORMAT.format(station.getUpdated());
        return entity;
    }

    private static List<Station> entities2stations(List<StationEntity> entities) {
        List<Station> stations = new ArrayList<>(entities.size());
        for( StationEntity entity : entities )
            stations.add(entity2station(entity));
        return stations;
    }

    private static List<StationEntity> stations2entities(List<Station> stations) {
        Date now = new Date();
        List<StationEntity> entities = new ArrayList<>(stations.size());
        for( Station station : stations ) {
            if( station.getUpdated() == null )
                station.setUpdated(now);
            entities.add(station2entity(station));
        }
        return entities;
    }
}
