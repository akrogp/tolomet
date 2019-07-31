package com.akrog.tolomet.providers;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

import org.json.JSONObject;

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
        JSONObject json = new JSONObject(data);
        long stamp = json.getLong("epoch")*1000;
        station.getMeteo().getAirTemperature().put(stamp, json.getDouble("temp"));
        station.getMeteo().getAirHumidity().put(stamp, json.getDouble("hum"));
        station.getMeteo().getAirPressure().put(stamp, json.getDouble("bar"));
        station.getMeteo().getWindDirection().put(stamp, json.getDouble("wdir"));
        station.getMeteo().getWindSpeedMed().put(stamp, json.getDouble("wspdavg")*3.6);
        station.getMeteo().getWindSpeedMax().put(stamp, json.getDouble("wspdhi")*3.6);
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
