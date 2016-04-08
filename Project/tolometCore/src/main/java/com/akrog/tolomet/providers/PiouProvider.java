package com.akrog.tolomet.providers;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public String getInfoUrl(String code) {
        return "http://pioupiou.fr/fr/"+code;
    }

    @Override
    public String getUserUrl(String code) {
        return getInfoUrl(code);
    }

    @Override
    public void configureDownload(Downloader downloader, Station station) {
        downloader.setUrl("http://api.pioupiou.fr/v1/archive/" + station.getCode());
        String start = station.getStamp() == null ? "last-day" : df.format(new Date(station.getStamp()));
        downloader.addParam("start",start);
        downloader.addParam("stop","now");
        downloader.addParam("format","json");
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
