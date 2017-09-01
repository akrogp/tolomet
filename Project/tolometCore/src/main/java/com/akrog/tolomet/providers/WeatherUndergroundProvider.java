package com.akrog.tolomet.providers;

import com.akrog.tolomet.Measurement;
import com.akrog.tolomet.Meteo;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by gorka on 1/09/17.
 */

public class WeatherUndergroundProvider extends BaseProvider {
    public WeatherUndergroundProvider() {
        super(REFRESH);
    }

    @Override
    public String getInfoUrl(String code) {
        return getUserUrl(code);
    }

    @Override
    public String getUserUrl(String code) {
        return String.format("https://www.wunderground.com/personal-weather-station/dashboard?ID=%s", code);
    }

    @Override
    public void configureDownload(Downloader downloader, Station station) {
        //configureDownload(downloader, station, System.currentTimeMillis());
        downloader.setUrl("http://api.wunderground.com/weatherstation/WXDailyHistory.asp");
        downloader.addParam("ID", station.getCode());
        downloader.addParam("format", "TXT");
    }

    @Override
    public boolean configureDownload(Downloader downloader, Station station, long date) {
        /*DateFormat df = new SimpleDateFormat("yyyyMMdd");
        df.setTimeZone(TIME_ZONE);
        downloader.setUrl(String.format(
                "http://api.wunderground.com/api/%s/history_%s/q/pws:%s.json",
                API_KEY, df.format(new Date(date)), station.getCode()
        ));
        return true;*/
        return false;
    }

    @Override
    public void updateStation(Station station, String data) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TIME_ZONE);
        Meteo meteo = station.getMeteo();
        String[] rows = data.split("\\n");
        String[] header = (rows[0].startsWith("Time") ? rows[0] : rows[1]).replaceAll("<.*>","").split(",");
        int iStamp = findIndex(header,"DateUTC");
        int iHum = findIndex(header,"Humidity");
        int iPres = findIndex(header,"PressurehPa");
        int iTemp = findIndex(header,"TemperatureC");
        int iDir = findIndex(header,"WindDirectionDegrees");
        int iMed = findIndex(header,"WindSpeedKMH");
        int iMax = findIndex(header,"WindSpeedGustKMH");
        for( String row : rows) {
            if( row.length() < 20 || !Character.isDigit(row.charAt(0)) )
                continue;
            String[] fields = row.split(",");
            long stamp = df.parse(fields[iStamp]).getTime();

            putValue(meteo.getAirHumidity(), stamp, fields, iHum, 0);
            putValue(meteo.getAirPressure(), stamp, fields, iPres, 0);
            putValue(meteo.getAirTemperature(), stamp, fields, iTemp, -100);
            putValue(meteo.getWindDirection(), stamp, fields, iDir, 0);
            putValue(meteo.getWindSpeedMed(), stamp, fields, iMed, 0);
            putValue(meteo.getWindSpeedMax(), stamp, fields, iMax, 0);
        }
    }

    private void putValue(Measurement meas, long stamp, String[] fields, int i, float min) {
        if( i < 0 )
            return;
        Float value = parseFloat(fields, i, min);
        if( value != null )
            meas.put(stamp, value);
    }

    private int findIndex(String[] header, String name) {
        for( int i = 0; i < header.length; i++ )
            if( header[i].equalsIgnoreCase(name) )
                return i;
        return -1;
    }

    private Float parseFloat(String[] fields, int i, float min) {
        try {
            Float value = Float.parseFloat(fields[i]);
            return value >= min ? value : null;
        } catch (Exception e) {
            return null;
        }
    }

    private static final int REFRESH = 5;
    //private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("Europe/Madrid");
    private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("UTC");
}
