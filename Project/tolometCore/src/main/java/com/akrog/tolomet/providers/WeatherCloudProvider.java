package com.akrog.tolomet.providers;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

public class WeatherCloudProvider extends BaseProvider {
    public WeatherCloudProvider() {
        super(REFRESH);
    }

    @Override
    public void configureDownload(Downloader downloader, Station station) {
        downloader.setUrl("https://app.weathercloud.net/device/values");
        downloader.setHeader("X-Requested-With", "XMLHttpRequest");
        downloader.addParam("code", station.getCode());
    }

    @Override
    public boolean configureDownload(Downloader downloader, Station station, long date) {
        return false;
    }

    @Override
    public void updateStation(Station station, String data) throws Exception {

    }

    @Override
    public String getInfoUrl(String code) {
        return String.format("https://app.weathercloud.net/d%s#profile", code);
    }

    @Override
    public String getUserUrl(String code) {
        return String.format("https://app.weathercloud.net/d%s#evolution", code);
    }

    private static final int REFRESH = 1;
}
