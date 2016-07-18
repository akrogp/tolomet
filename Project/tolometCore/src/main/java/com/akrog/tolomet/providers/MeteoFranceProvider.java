package com.akrog.tolomet.providers;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

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
        return null;
    }

    @Override
    public String getUserUrl(String code) {
        return null;
    }

    @Override
    public void configureDownload(Downloader downloader, Station station) {

    }

    @Override
    public void updateStation(Station station, String data) {

    }

    private static final int REFRESH = 60;
    private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("CET");
}
