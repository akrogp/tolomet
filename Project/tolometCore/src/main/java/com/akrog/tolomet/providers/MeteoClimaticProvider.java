package com.akrog.tolomet.providers;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

/**
 * Created by gorka on 28/11/16.
 */

public class MeteoClimaticProvider extends BaseProvider {
    public MeteoClimaticProvider() {
        super(15);
    }

    @Override
    public void configureDownload(Downloader downloader, Station station) {
    }

    @Override
    public boolean configureDownload(Downloader downloader, Station station, long date) {
        return false;
    }

    @Override
    public void updateStation(Station station, String data) {

    }

    @Override
    public String getInfoUrl(String code) {
        return null;
    }

    @Override
    public String getUserUrl(String code) {
        return null;
    }
}
