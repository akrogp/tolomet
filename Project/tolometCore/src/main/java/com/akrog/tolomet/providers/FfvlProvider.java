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
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by gorka on 18/07/16.
 */
public class FfvlProvider extends BaseProvider {
    public FfvlProvider() {
        super(REFRESH);
    }

    @Override
    public String getInfoUrl(Station sta) {
        return "http://www.balisemeteo.com/balise.php?idBalise=" + sta.getCode();
    }

    @Override
    public String getUserUrl(Station sta) {
        return "http://www.balisemeteo.com/balise_histo.php?interval=1&marks=true&idBalise=" + sta.getCode();
    }

    @Override
    public void configureDownload(Downloader downloader, Station station) {
        downloader.setUrl("https://data.ffvl.fr/php/historique_relevesmeteo.php");
        downloader.addParam("idbalise", station.getCode());
        Long stamp = station.getStamp();
        Calendar cal = Calendar.getInstance();
        int hours;
        if( stamp == null )
            hours = cal.get(Calendar.HOUR_OF_DAY)+1;
        else
            hours = (int)((cal.getTimeInMillis()-stamp)/1000/60/60+1);
        downloader.addParam("heures",hours);
    }

    @Override
    public boolean configureDownload(Downloader downloader, Station station, long date) {
        return false;
    }

    @Override
    public List<Station> downloadStations() {
        try {
            downloader = new Downloader();
            downloader.setUrl("https://data.ffvl.fr/json/balises.json");
            JSONArray array = new JSONArray(downloader.download());
            List<Station> result = new ArrayList<>(array.length());
            for( int i = 0; i < array.length(); i++ ) {
                JSONObject json = array.getJSONObject(i);
                Station station = new Station();
                station.setProviderType(WindProviderType.Ffvl);
                station.setCode(json.getString("idBalise"));
                if( !json.has("nom") || json.getString("nom").equalsIgnoreCase("null") )
                    station.setName("FFVL#" + station.getCode());
                else
                    station.setName(json.getString("nom"));
                try {
                    station.setLatitude(Double.parseDouble(json.getString("latitude")));
                    station.setLongitude(Double.parseDouble(json.getString("longitude")));
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
    public void updateStation(Station station, String data) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            df.setTimeZone(TIME_ZONE);
            JSONArray array = new JSONArray(data);
            Meteo meteo = station.getMeteo();
            Calendar cal = Calendar.getInstance();
            DateUtils.resetDay(cal);
            for( int i = 0; i < array.length(); i++ ) {
                JSONObject json = array.getJSONObject(i);
                long stamp = df.parse(json.getString("date")).getTime();
                if( stamp < cal.getTimeInMillis() )
                    continue;
                save(meteo.getWindDirection(), stamp, "directVentMoy", json);
                save(meteo.getWindSpeedMed(), stamp, "vitesseVentMoy", json);
                save(meteo.getWindSpeedMax(), stamp, "vitesseVentMax", json);
                save(meteo.getAirTemperature(), stamp, "temperature", json);
                save(meteo.getAirPressure(), stamp,"pression", json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void save(Measurement meas, long stamp, String key, JSONObject json) {
        String value = json.optString(key);
        if( value == null || value.equalsIgnoreCase("null") )
            return;
        meas.put(stamp, Double.parseDouble(value));
    }

    private static final int REFRESH = 5;
    private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("CET");
}
