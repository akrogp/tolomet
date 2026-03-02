package com.akrog.tolomet.providers;

import com.akrog.tolomet.Meteo;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by gorka on 1/09/17.
 */

public class WeatherUndergroundProvider extends BaseProvider {
    public WeatherUndergroundProvider() {
        super(REFRESH);
    }

    @Override
    public String getInfoUrl(Station sta) {
        return getUserUrl(sta);
    }

    @Override
    public String getUserUrl(Station sta) {
        return String.format("https://www.wunderground.com/personal-weather-station/dashboard?ID=%s", sta.getCode());
    }

    @Override
    public void configureDownload(Downloader downloader, Station station) {
        downloader.setUrl("https://api.weather.com/v2/pws/observations/all/1day");
        downloader.addParam("apiKey", "e1f10a1e78da46f5b10a1e78da96f525");
        downloader.addParam("stationId", station.getCode());
        downloader.addParam("numericPrecision", "decimal");
        downloader.addParam("format", "json");
        downloader.addParam("units", "m");
    }

    @Override
    public boolean configureDownload(Downloader downloader, Station station, long date) {
        return false;
    }

    @Override
    public void updateStation(Station station, String data) throws Exception {
        Meteo meteo = station.getMeteo();
        JSONObject json = new JSONObject(data);
        JSONArray array = json.getJSONArray("observations");
        for( int i = 0; i < array.length(); i++ ) {
            JSONObject tmp = array.getJSONObject(i);
            long stamp = tmp.getLong("epoch")*1000;
            JSONObject metric = tmp.getJSONObject("metric");
            Double value = metric.optDouble("tempAvg");
            if( value != null )
                meteo.getAirTemperature().put(stamp, value);
            value = metric.optDouble("pressureMax");
            if( value != null )
                meteo.getAirPressure().put(stamp, value);
            value = tmp.optDouble("humidityAvg");
            if( value != null )
                meteo.getAirHumidity().put(stamp, value);
            value = metric.optDouble("windspeedAvg");
            if( value != null )
                meteo.getWindSpeedMed().put(stamp, value);
            value = metric.optDouble("windgustAvg");
            if( value != null )
                meteo.getWindSpeedMax().put(stamp, value);
            value = tmp.optDouble("winddirAvg");
            if( value != null )
                meteo.getWindDirection().put(stamp, value);
        }
    }

    private static final int REFRESH = 15;
}
