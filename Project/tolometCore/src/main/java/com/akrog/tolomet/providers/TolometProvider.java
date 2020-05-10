package com.akrog.tolomet.providers;

import com.akrog.tolomet.Meteo;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;
import com.akrog.tolomet.utils.DateUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

public abstract class TolometProvider extends BaseProvider {
    public TolometProvider(int defRefresh) {
        super(defRefresh);
    }

    protected abstract String getApiUrl();

    @Override
    public boolean configureDownload(Downloader downloader, Station station, long date) {
        downloader.setUrl(getApiUrl());
        downloader.addParam("id", station.getCode());
        Calendar from = Calendar.getInstance();
        from.setTimeInMillis(date);
        DateUtils.resetDay(from);
        downloader.addParam("from", from.getTimeInMillis()/1000);
        downloader.addParam("to", date/1000);
        return true;
    }

    @Override
    public void configureDownload(Downloader downloader, Station station) {
        downloader.setUrl(getApiUrl());
        downloader.addParam("id", station.getCode());
        Calendar cal = Calendar.getInstance();
        DateUtils.resetDay(cal);
        Long stamp = station.getStamp();
        long from = stamp == null ? cal.getTimeInMillis() : Math.max(cal.getTimeInMillis(),stamp);
        downloader.addParam("from", from/1000);
        downloader.addParam("to", System.currentTimeMillis()/1000);
    }

    @Override
    public void updateStation(Station station, String data) throws Exception {
        JSONArray array = new JSONArray(data);
        Meteo meteo = station.getMeteo();
        for( int i = 0; i < array.length(); i++ ) {
            JSONObject json = array.getJSONObject(i);
            try {
                long stamp = json.getLong("stamp")*1000;
                if( json.has("dir") )
                    meteo.getWindDirection().put(stamp, json.getDouble("dir"));
                if( json.has("med") )
                    meteo.getWindSpeedMed().put(stamp, json.getDouble("med"));
                if( json.has("max") )
                    meteo.getWindSpeedMax().put(stamp, json.getDouble("max"));
                if( json.has("temp") )
                    meteo.getAirTemperature().put(stamp, json.getDouble("temp"));
                if( json.has("hum") )
                    meteo.getAirHumidity().put(stamp, json.getDouble("hum"));
                if( json.has("pres") )
                    meteo.getAirPressure().put(stamp, json.getDouble("pres"));
            } catch (Exception e) {
            }
        }
    }
}
