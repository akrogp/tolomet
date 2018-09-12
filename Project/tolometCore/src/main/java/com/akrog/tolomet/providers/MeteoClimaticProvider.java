package com.akrog.tolomet.providers;

import com.akrog.tolomet.Meteo;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by gorka on 28/11/16.
 */

public class MeteoClimaticProvider extends BaseProvider {
    public MeteoClimaticProvider() {
        super(REFRESH);
    }

    @Override
    public void configureDownload(Downloader downloader, Station station) {
        downloader.setUrl(String.format("https://www.meteoclimatic.net/feed/rss/%s",station.getCode()));
    }

    @Override
    public boolean configureDownload(Downloader downloader, Station station, long date) {
        return false;
    }

    @Override
    public void updateStation(Station station, String data) throws Exception {
        int begin = data.indexOf("Actualizado:");
        if( begin < 0 ) return;
        int end = data.indexOf("</li>",begin);
        if( end < 0 ) return;
        long stamp = df.parse(data.substring(begin+12,end)).getTime();

        begin = data.indexOf(String.format("[[<%s;", station.getCode()));
        if( begin < 0 ) return;
        end = data.indexOf("]]",begin);
        if( end < 0 ) return;
        String[] fields = data.substring(begin,end).replaceAll(",",".").split("\\)?;\\(");
        String[] subfields;
        Meteo meteo = station.getMeteo();
        double value;

        subfields = fields[1].split(";");
        meteo.getAirTemperature().put(stamp,Double.parseDouble(subfields[0]));

        subfields = fields[2].split(";");
        if( (value=Double.parseDouble(subfields[0])) < 0.0 )
            return;
        meteo.getAirHumidity().put(stamp,value);

        subfields = fields[3].split(";");
        if( (value=Double.parseDouble(subfields[0])) < 0.0 )
            return;
        meteo.getAirPressure().put(stamp,value);

        subfields = fields[4].split(";");
        if( (value = Double.parseDouble(subfields[0])) < 0.0 )
            return;
        meteo.getWindSpeedMed().put(stamp,value);
        //meteo.getWindSpeedMax().put(stamp,Double.parseDouble(subfields[1]));
        meteo.getWindDirection().put(stamp,Double.parseDouble(subfields[2]));
    }

    @Override
    public String getInfoUrl(String code) {
        return String.format("https://www.meteoclimatic.net/perfil/%s#toggle",code);
    }

    @Override
    public String getUserUrl(String code) {
        return String.format("https://www.meteoclimatic.net/perfil/%s#content",code);
    }

    private static final int REFRESH = 15;
    private static final DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm z");
}
