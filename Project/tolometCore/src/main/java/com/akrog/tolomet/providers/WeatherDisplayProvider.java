package com.akrog.tolomet.providers;

import com.akrog.tolomet.Meteo;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by gorka on 23/04/18.
 */

public class WeatherDisplayProvider extends BaseProvider {
    private static final int REFRESH = 1;
    private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("CET");
    private static final float KMH_KNOT = 1.852F;

    public WeatherDisplayProvider() {
        super(REFRESH);
    }

    @Override
    public String getInfoUrl(String code) {
        return getUserUrl(code);
    }

    @Override
    public String getUserUrl(String code) {
        return "http://tiempo.fiochi.com/wd_data/index.php";
    }

    @Override
    public void configureDownload(Downloader downloader, Station station) {
        downloader.setUrl("http://tiempo.fiochi.com/clientrawhour.txt");
    }

    @Override
    public boolean configureDownload(Downloader downloader, Station station, long date) {
        return false;
    }

    @Override
    public void updateStation(Station station, String data) throws Exception {
        Calendar calendar = getCalendar();
        if( calendar == null )
            return;
        String[] fields = data.split(" ");
        if( fields.length < 674 )
            return;
        Meteo meteo = station.getMeteo();
        long stamp = calendar.getTimeInMillis()-60*60*1000;
        for( int i = 0; i < 60; i++, stamp += 60*1000 ) {
            meteo.getWindDirection().put(stamp, Float.parseFloat(fields[121+i]));
            meteo.getWindSpeedMed().put(stamp, Float.parseFloat(fields[1+i])*KMH_KNOT);
            meteo.getWindSpeedMax().put(stamp, Float.parseFloat(fields[61+i])*KMH_KNOT);
            meteo.getAirTemperature().put(stamp, Float.parseFloat(fields[181+i]));
            meteo.getAirHumidity().put(stamp, Float.parseFloat(fields[241+i]));
            meteo.getAirPressure().put(stamp, Float.parseFloat(fields[301+i]));
        }
    }

    private Calendar getCalendar() throws Exception {
        Downloader downloader = new Downloader();
        downloader.setUrl("http://tiempo.fiochi.com/clientraw.txt");
        String info = downloader.download();
        String[] fields = info.split(" ");
        if( fields.length < 175 )
            return null;
        Calendar cal = Calendar.getInstance(TIME_ZONE);
        cal.set(Calendar.YEAR, Integer.parseInt(fields[141]));
        cal.set(Calendar.MONTH, Integer.parseInt(fields[36])-1);
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(fields[35]));
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(fields[29]));
        cal.set(Calendar.MINUTE, Integer.parseInt(fields[30]));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }
}
