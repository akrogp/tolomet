package com.akrog.tolomet.providers;

import com.akrog.tolomet.Measurement;
import com.akrog.tolomet.Meteo;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;
import com.akrog.tolomet.utils.DateUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class NorometProvider extends BaseProvider {
    private static final int REFRESH = 5;
    private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("CET");

    public NorometProvider() {
        super(REFRESH);
    }

    @Override
    public void configureDownload(Downloader downloader, Station station) {
        downloader.setUrl("https://noromet.org/privado/PostGetFiles/GetGraphicFiveMin.php");
        downloader.addParam("id_estacion", station.getCode());
    }

    @Override
    public boolean configureDownload(Downloader downloader, Station station, long date) {
        return false;
    }

    @Override
    public List<Station> downloadStations() {
        try {
            downloader = new Downloader();
            downloader.setUrl("https://noromet.org/privado/datosInicioV2.json");
            JSONArray array = new JSONArray(downloader.download());
            List<Station> result = new ArrayList<>(array.length());
            for( int i = 0; i < array.length(); i++ ) {
                JSONObject json = array.getJSONObject(i);
                Station station = new Station();
                station.setProviderType(WindProviderType.Noromet);
                station.setCode(json.getString("id_estacion"));
                station.setName(json.getString("name"));
                try {
                    JSONArray latlon = json.getJSONArray("latLng");
                    station.setLatitude(Double.parseDouble(latlon.getString(0)));
                    station.setLongitude(Double.parseDouble(latlon.getString(1)));
                    result.add(station);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            downloader = null;
        }
        return null;
    }

    @Override
    public void updateStation(Station station, String data) throws Exception {
        JSONObject json = new JSONObject(data);
        JSONArray hour = json.getJSONArray("hour");
        Calendar cal = Calendar.getInstance(TIME_ZONE);
        DateUtils.resetDay(cal);
        int h0 = Integer.parseInt(hour.getString(0).split(":")[0]);
        int h2 = Integer.parseInt(hour.getString(hour.length()-1).split(":")[0]);
        if( h0 > h2 )
            cal.add(Calendar.DAY_OF_MONTH, -1);
        for( int i = 0; i < hour.length(); i++ ) {
            String[] fields = hour.getString(i).split(":");
            int h1 = Integer.parseInt(fields[0]);
            if( h1 < h0 ) {
                cal.add(Calendar.DAY_OF_MONTH, 1);
                h0 = h1;
            }
            cal.set(Calendar.HOUR_OF_DAY, h1);
            cal.set(Calendar.MINUTE, Integer.parseInt(fields[1]));
            long stamp = cal.getTimeInMillis();
            Meteo meteo = station.getMeteo();
            save(meteo.getAirTemperature(), stamp, "temp", json, i);
            save(meteo.getAirHumidity(), stamp, "hum", json, i);
            save(meteo.getAirPressure(), stamp, "bar", json, i);
            save(meteo.getWindDirection(), stamp, "wind_dir", json, i);
            save(meteo.getWindSpeedMed(), stamp, "wind", json, i);
        }
    }

    private void save(Measurement meas, long stamp, String key, JSONObject json, int i) {
        JSONArray array = json.optJSONArray(key);
        if( array == null )
            return;
        Double value = array.optDouble(i);
        if( value == null || value.isNaN() )
            return;
        meas.put(stamp, value);
    }

    @Override
    public String getInfoUrl(Station sta) {
        return "https://noromet.org/index2.html?id_estacion=" + sta.getCode();
    }

    @Override
    public String getUserUrl(Station sta) {
        return getInfoUrl(sta);
    }
}
