package com.akrog.tolometgui2.model.db;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.akrog.tolomet.Measurement;
import com.akrog.tolomet.Meteo;
import com.akrog.tolomet.Station;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Dao
public abstract class MeteoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertMeasurements(Collection<MeteoEntity> measurements);

    @Query("SELECT * FROM Meteo WHERE station = :station")
    public abstract LiveData<List<MeteoEntity>> loadMeasurements(String station);

    public void saveStation(Station station) {
        Map<Long, MeteoEntity> map = new HashMap<>(station.getMeteo().getWindDirection().size());
        Meteo meteo = station.getMeteo();
        merge(map, meteo.getWindDirection(), (ent, val) -> ent.dir = val.intValue());
        merge(map, meteo.getWindSpeedMed(), (ent, val) -> ent.med = val.floatValue());
        merge(map, meteo.getWindSpeedMax(), (ent, val) -> ent.max = val.floatValue());
        merge(map, meteo.getAirTemperature(), (ent, val) -> ent.temp = val.floatValue());
        merge(map, meteo.getAirHumidity(), (ent, val) -> ent.hum = val.floatValue());
        merge(map, meteo.getAirPressure(), (ent, val) -> ent.pres = val.floatValue());
        for( MeteoEntity ent : map.values() )
            ent.station = station.getId();
        insertMeasurements(map.values());
    }

    public LiveData<Station> loadStation(Station station) {
        if( station == null )
            return new MutableLiveData<>();
        return Transformations.map(loadMeasurements(station.getId()), entities -> {
            Meteo meteo = station.getMeteo();
            for( MeteoEntity e : entities ) {
                meteo.getWindDirection().put(e.stamp, e.dir);
                meteo.getWindSpeedMed().put(e.stamp, e.med);
                meteo.getWindSpeedMax().put(e.stamp, e.max);
                meteo.getAirTemperature().put(e.stamp, e.temp);
                meteo.getAirHumidity().put(e.stamp, e.hum);
                meteo.getAirPressure().put(e.stamp, e.pres);
            }
            return station;
        });
    }

    private void merge(Map<Long, MeteoEntity> map, Measurement meas, Merger merger) {
        for(Map.Entry<Long, Number> entry : meas.getEntrySet()) {
            MeteoEntity ent = map.get(entry.getKey());
            if( ent == null ) {
                ent = new MeteoEntity();
                ent.stamp = entry.getKey();
                map.put(ent.stamp, ent);
            }
            merger.merge(ent, entry.getValue());
        }
    }

    private interface Merger {
        void merge(MeteoEntity ent, Number val);
    }
}
