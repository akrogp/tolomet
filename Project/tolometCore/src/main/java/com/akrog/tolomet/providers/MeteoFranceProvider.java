package com.akrog.tolomet.providers;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by gorka on 18/07/16.
 */
public class MeteoFranceProvider extends BaseProvider {
    public MeteoFranceProvider() {
        super(REFRESH);
    }

    @Override
    public String getInfoUrl(String code) {
        return getUserUrl(code);
    }

    @Override
    public String getUserUrl(String code) {
        return String.format("http://www.meteofrance.com/previsions-meteo-france/station-observations?a=%s&b=",code);
    }

    @Override
    public void configureDownload(Downloader downloader, Station station) {
        downloader.setUrl("http://www.vigimeteo.com/data/obsd2i.txt");
    }

    @Override
    public void updateStation(Station station, String data) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        df.setTimeZone(TIME_ZONE);
        String[] lines = data.split("\n");
        Float value;
        long stamp;
        for( String line : lines ) {
            try {
                String[] fields = line.split(";");
                if (!fields[0].equals(station.getCode()))
                    continue;
                stamp = df.parse(fields[1]).getTime();
                if( (value=parseField(fields[2])) != null )
                    station.getMeteo().getAirTemperature().put(stamp,value/10.0);
                if( (value=parseField(fields[6])) != null )
                    station.getMeteo().getAirHumidity().put(stamp,value);
                if( (value=parseField(fields[3])) != null )
                    station.getMeteo().getAirPressure().put(stamp,value/10.0);
                if( (value=parseField(fields[7])) != null )
                    station.getMeteo().getWindDirection().put(stamp,value);
                if( (value=parseField(fields[8])) != null )
                    station.getMeteo().getWindSpeedMed().put(stamp,value/10.0*3.6);
                if( (value=parseField(fields[9])) != null )
                    station.getMeteo().getWindSpeedMax().put(stamp,value/10.0*3.6);
            } catch( Exception e ) {
                e.printStackTrace();
            }
        }
    }

    private Float parseField(String field) {
        if( field == null || field.isEmpty() )
            return null;
        Float value = Float.parseFloat(field);
        if( value < 0 )
            return null;
        return value;
    }

    private static final int REFRESH = 60;
    private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("UTC");
}
