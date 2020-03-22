package com.akrog.tolomet.providers;

import com.akrog.tolomet.Meteo;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by gorka on 23/04/18.
 */

public class WeatherDisplayProvider extends BaseProvider {
    private static final int REFRESH = 60;
    private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("CET");
    private static final float KMH_KNOT = 1.852F;
    private static Map<String, String> mapIndex;
    private static Map<String, String> mapTxt;
    private static int[] ISTAMP = {459, 460, 461, 462, 463, 464, 465, 466, 467, 468, 469, 470, 471, 472, 473, 474, 475, 476, 477, 478, 578, 579, 580, 581};
    private static int[] ITEMP = {21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 566, 567, 568, 569};
    private static int[] IPRES = {439, 440, 441, 442, 443, 444, 445, 446, 447, 448, 449, 450, 451, 452, 453, 454, 455, 456, 457, 458, 574, 575, 576, 577};
    private static int[] IHUM = {611, 612, 613, 614, 615, 616, 617, 618, 619, 620, 621, 622, 623, 624, 625, 626, 627, 628, 629, 630, 631, 632, 633, 634};
    private static int[] IDIR = {536, 537, 538, 539, 540, 541, 542, 543, 544, 545, 546, 547, 548, 549, 550, 551, 552, 553, 554, 555, 590, 591, 592, 593};
    private static int[] ISPEED = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 562, 563, 564, 565};

    private static Map<String, String> getMapIndex() {
        if( mapIndex == null ) {
            mapIndex = new HashMap<>();
            mapIndex.put("FIOCHI", "https://tiempo.fiochi.com/wd_data/index.php");
            mapIndex.put("RCNL", "https://www.rcnlaredo.es/~meteorcnl/meteo/wdisplay2/index.html");
        }
        return mapIndex;
    }

    private static Map<String, String> getMapTxt() {
        if( mapTxt == null ) {
            mapTxt = new HashMap<>();
            mapTxt.put("FIOCHI", "https://tiempo.fiochi.com/clientrawextra.txt");
            mapTxt.put("RCNL", "https://www.rcnlaredo.es/~meteorcnl/meteo/wdisplay2/clientrawextra.txt");
        }
        return mapTxt;
    }

    public WeatherDisplayProvider() {
        super(REFRESH);
    }

    @Override
    public String getInfoUrl(String code) {
        return getUserUrl(code);
    }

    @Override
    public String getUserUrl(String code) {
        return getMapIndex().get(code);
    }

    @Override
    public void configureDownload(Downloader downloader, Station station) {
        downloader.setUrl(getMapTxt().get(station.getCode()));
    }

    @Override
    public boolean configureDownload(Downloader downloader, Station station, long date) {
        return false;
    }

    @Override
    public void updateStation(Station station, String data) throws Exception {
        String[] fields = data.split(" ");
        if( fields.length < 635 )
            return;

        Calendar cal = Calendar.getInstance(TIME_ZONE);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        int currentHour = cal.get(Calendar.HOUR_OF_DAY);
        int currentMin = cal.get(Calendar.MINUTE);

        Meteo meteo = station.getMeteo();
        for( int i = 0; i < 24; i++ ) try {
            String[] time = fields[ISTAMP[i]].split(":");
            int prevHour = Integer.parseInt(time[0]);
            int prevMin = Integer.parseInt(time[1]);
            if( prevHour > currentHour || (prevHour == currentHour && prevMin > currentMin) )
                continue;
            cal.set(Calendar.HOUR_OF_DAY, prevHour);
            cal.set(Calendar.MINUTE, prevMin);
            long stamp = cal.getTimeInMillis();
            meteo.getWindDirection().put(stamp, Float.parseFloat(fields[IDIR[i]]));
            meteo.getWindSpeedMed().put(stamp, Float.parseFloat(fields[ISPEED[i]])*KMH_KNOT);
            meteo.getAirTemperature().put(stamp, Float.parseFloat(fields[ITEMP[i]]));
            meteo.getAirHumidity().put(stamp, Float.parseFloat(fields[IHUM[i]]));
            meteo.getAirPressure().put(stamp, Float.parseFloat(fields[IPRES[i]]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
