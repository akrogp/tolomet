package com.akrog.tolomet.providers;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by gorka on 8/04/16.
 */
public class PiouProvider extends BaseProvider {
    public PiouProvider() {
        super(REFRESH);
        df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public String getInfoUrl(Station sta) {
        return "http://pioupiou.fr/fr/" + sta.getCode();
    }

    @Override
    public String getUserUrl(Station sta) {
        return getInfoUrl(sta);
    }

    @Override
    public void configureDownload(Downloader downloader, Station station) {
        String start = station.getStamp() == null ? "last-day" : df.format(new Date(station.getStamp()));
        configureDownload(downloader, station, start, "now");
    }

    @Override
    public boolean configureDownload(Downloader downloader, Station station, long date) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        String start = df.format(new Date(cal.getTimeInMillis()));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        String stop = df.format(new Date(cal.getTimeInMillis()));
        configureDownload(downloader, station, start, stop);
        return true;
    }

    public void configureDownload(Downloader downloader, Station station, String start, String stop) {
        downloader.setUrl("http://api.pioupiou.fr/v1/archive/" + station.getCode());
        downloader.addParam("start",start);
        downloader.addParam("stop",stop);
        downloader.addParam("format","json");
    }

    @Override
    public List<Station> downloadStations() {
        try {
            downloader = new Downloader();
            downloader.setUrl("http://api.pioupiou.fr/v1/live-with-meta/all");
            String data = downloader.download();
            JSONObject json = new JSONObject(data);
            JSONArray array = json.getJSONArray("data");
            List<Station> result = new ArrayList<>();
            for( int i = 0; i < array.length(); i++ ) {
                JSONObject item = array.getJSONObject(i);
                String id = item.getString("id");
                if( id.equals("null") )
                    continue;
                JSONObject status = item.getJSONObject("status");
                if( status.getString("state").equalsIgnoreCase("off") )
                    continue;
                Calendar cal = Calendar.getInstance();
                if( !status.getString("date").startsWith(cal.get(Calendar.YEAR)+"") )
                    continue;
                JSONObject meta = item.getJSONObject("meta");
                JSONObject location = item.getJSONObject("location");
                Station station = new Station();
                station.setCode(id);
                station.setName(meta.getString("name"));
                station.setLatitude(location.getDouble("latitude"));
                station.setLongitude(location.getDouble("longitude"));
                station.setProviderType(WindProviderType.PiouPiou);
                if( station.isFilled() )
                    result.add(station);
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
            JSONObject json = new JSONObject(data);
            JSONArray array = json.getJSONArray("data");
            for( int i = 0; i < data.length(); i++ ) {
                JSONArray item = array.getJSONArray(i);
                long stamp = df.parse(item.getString(0)).getTime();
                try {
                    station.getMeteo().getAirPressure().put(stamp,(float)item.getInt(7));
                } catch (Exception e) {};
                try {
                    station.getMeteo().getWindDirection().put(stamp, (float) item.getInt(6));
                } catch (Exception e) {};
                try {
                    station.getMeteo().getWindSpeedMed().put(stamp, (float) item.getDouble(4));
                } catch (Exception e) {};
                try {
                    station.getMeteo().getWindSpeedMax().put(stamp,(float)item.getDouble(5));
                } catch (Exception e) {};
            }
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static final int REFRESH = 4;
    private final DateFormat df;
}
