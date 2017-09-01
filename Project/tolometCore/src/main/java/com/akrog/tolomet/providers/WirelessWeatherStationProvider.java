package com.akrog.tolomet.providers;

import com.akrog.tolomet.Measurement;
import com.akrog.tolomet.Meteo;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by gorka on 1/09/17.
 */

public class WirelessWeatherStationProvider extends BaseProvider {
    public WirelessWeatherStationProvider() {
        super( REFRESH );
    }

    @Override
    public String getInfoUrl(String code) {
        return "http://cvlarcones.net/index.php/homepage/estacion";
    }

    @Override
    public String getUserUrl(String code) {
        return "http://cvlarcones.net/station/miprueba2.php";
    }

    @Override
    public void configureDownload(Downloader downloader, Station station) {
        downloader.setUrl("http://cvlarcones.net/station/index.php?page=1h");
    }

    @Override
    public boolean configureDownload(Downloader downloader, Station station, long date) {
        return false;
    }

    @Override
    public void updateStation(Station station, String data) throws Exception {
        Meteo meteo = station.getMeteo();
        String[] rows = data.split("<tr>");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        for( int i = 3; i < rows.length; i++ ) {
            String[] cells = rows[i].split("<td>");
            String[] date = parseCell(cells, 1).split("/");
            if( date.length == 3 ) {
                cal.set(Calendar.YEAR, Integer.parseInt(date[2]));
                cal.set(Calendar.MONTH, Integer.parseInt(date[1])-1);
                cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[0]));
            }
            date = parseCell(cells, 2).split(":");
            if( date.length != 2 )
                continue;
            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(date[0]));
            cal.set(Calendar.MINUTE, Integer.parseInt(date[1]));
            long stamp = cal.getTimeInMillis();
            putValue(meteo.getAirTemperature(), stamp, cells, 3);
            putValue(meteo.getAirHumidity(), stamp, cells, 5);
            parseDirection(meteo.getWindDirection(), stamp, cells, 6);
            putValue(meteo.getWindSpeedMed(), stamp, cells, 7);
            putValue(meteo.getWindSpeedMax(), stamp, cells, 8);
            putValue(meteo.getAirPressure(), stamp, cells, 10);
        }
    }

    private void parseDirection(Measurement meas, long stamp, String[] cells, int i) {
        if( mapDir == null )
            createMapDir();
        meas.put(stamp,mapDir.get(parseCell(cells,i)));
    }

    private void createMapDir() {
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/res/directions_es.csv")));
            String[] dirs = rd.readLine().split(",");
            rd.close();
            mapDir = new HashMap<>();
            float degrees = 0;
            for( String dir : dirs ) {
                mapDir.put(dir, degrees);
                degrees += 22.5;
            }
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }

    private void putValue(Measurement meas, long stamp, String[] cells, int i) {
        Float value = parseFloat(cells, i);
        if( value != null )
            meas.put(stamp, value);
    }

    private String parseCell( String[] cells, int i ) {
        return cells[i].replaceAll("<.*>","").replaceAll(" .*","").trim();
    }

    private Float parseFloat( String[] cells, int i ) {
        try {
            return Float.parseFloat(parseCell(cells, i).replace(',','.').replace("%",""));
        } catch (Exception e) {
            return null;
        }
    }

    private static final int REFRESH = 5;
    private Map<String,Float> mapDir;
}
