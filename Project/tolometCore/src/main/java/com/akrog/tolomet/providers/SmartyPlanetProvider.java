package com.akrog.tolomet.providers;

import com.akrog.tolomet.Measurement;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;
import com.akrog.tolomet.utils.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class SmartyPlanetProvider extends BaseProvider {
    private static final int REFRESH = 10;
    private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("CET");
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public SmartyPlanetProvider() {
        super(REFRESH);
    }

    @Override
    public void configureDownload(Downloader downloader, Station station) {
        // https://castejondesos.smartyplanet.com/smartis/smarti/ajax/2023-03-21 00:00/2023-03-21 23:59/{"canals":[{"smarti":1811,"canal":1810},{"smarti":1807,"canal":1806},{"smarti":1807,"canal":1815}]}/dia/0/0
        StringBuilder url = new StringBuilder("https://castejondesos.smartyplanet.com/smartis/smarti/ajax/");
        Calendar cal = Calendar.getInstance(TIME_ZONE);
        Long start = station.getStamp();
        if( start == null ) {
            DateUtils.resetDay(cal);
            start = cal.getTimeInMillis();
        }
        try {
            url.append(URLEncoder.encode(SDF.format(new Date(start)), "UTF-8"));
            url.append('/');
            DateUtils.endDay(cal);
            url.append(URLEncoder.encode(SDF.format(cal.getTime()), "UTF-8"));
            url.append('/');
            url.append(URLEncoder.encode("{\"canals\":[{\"smarti\":1811,\"canal\":1810},{\"smarti\":1807,\"canal\":1806},{\"smarti\":1807,\"canal\":1815}]}", "UTF-8"));
            url.append("/dia/0/0");
            downloader.setUrl(url.toString().replaceAll("\\+", "%20"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean configureDownload(Downloader downloader, Station station, long date) {
        return false;
    }

    @Override
    public void updateStation(Station station, String data) throws Exception {
        JSONObject json = new JSONObject(data);
        JSONArray canals = json.getJSONArray("canals");
        for(int i = 0; i < canals.length(); i++ ) {
            JSONObject canal = canals.getJSONObject(i);
            String tipus = canal.getString("tipus_clau");
            if( tipus.equals("winddirection") )
                parseCanal(canal, station.getMeteo().getWindDirection());
            else if( tipus.equals("racha_windspeed") )
                parseCanal(canal, station.getMeteo().getWindSpeedMax());
            else if( tipus.equals("windspeed") )
                parseCanal(canal, station.getMeteo().getWindSpeedMed());
            else if( tipus.equals("temperatura") )
                parseCanal(canal, station.getMeteo().getAirTemperature());
            else if( tipus.equals("humidity") )
                parseCanal(canal, station.getMeteo().getAirHumidity());
        }
    }

    private void parseCanal(JSONObject canal, Measurement meas) throws JSONException {
        JSONArray valors = canal.getJSONArray("valors");
        for( int i = 0; i < valors.length(); i++ ) {
            JSONObject valor = valors.getJSONObject(i);
            long stamp = valor.getLong("at") * 1000;
            double number =  Double.parseDouble(valor.getString("value"));
            meas.put(stamp, number);
        }
    }

    @Override
    public String getInfoUrl(Station sta) {
        return String.format("https://castejondesos.smartyplanet.com/es/estacions/estacio/%s/smartis/", sta.getCode());
    }

    @Override
    public String getUserUrl(Station sta) {
        return String.format("https://castejondesos.smartyplanet.com/es/estacions/estacio/%s/fitxa/1811", sta.getCode());
    }
}
