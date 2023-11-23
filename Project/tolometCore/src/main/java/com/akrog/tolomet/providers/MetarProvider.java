package com.akrog.tolomet.providers;

import com.akrog.tolomet.Measurement;
import com.akrog.tolomet.Meteo;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;
import com.akrog.tolomet.utils.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class MetarProvider extends BaseProvider {
	public MetarProvider() {
		super(30);
	}

	@Override
	public String getInfoUrl(String code) {
		return String.format("https://aviationweather.gov/data/metar/?id=%s&hours=0&decoded=yes&include_taf=yes", code);
	}

	@Override
	public String getUserUrl(String code) {
		return getInfoUrl(code);
	}

	@Override
	public List<Station> downloadStations() {
		downloader = new Downloader();
		downloader.setGzipped(true);
		downloader.setUrl("https://aviationweather.gov/data/cache/stations.cache.json.gz");
		String data = downloader.download();
		downloader = null;

		List<Station> stations = new ArrayList<>();
		try {
			JSONArray array = new JSONArray(data);
			for( int i = 0; i < array.length(); i++ ) {
				JSONObject json = array.getJSONObject(i);
				Station station = new Station();
				station.setProviderType(WindProviderType.Metar);
				station.setCode(json.getString("icaoId"));
				station.setName(json.getString("site"));
				station.setCountry(json.getString("country"));
				station.setLatitude(json.getDouble("lat"));
				station.setLongitude(json.getDouble("lon"));
				stations.add(station);
			}
			return stations;
		} catch( Exception e ) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean configureDownload(Downloader downloader, Station station, long date ) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date);
		DateUtils.endDay(cal);
		configureDownload(downloader, station, cal, 24);
		return true;
	}

	@Override
	public void configureDownload(Downloader downloader, Station station) {
		Long stamp = station.getStamp();
		int from = 0;
		if( stamp != null ) {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(stamp);
			from = cal.get(Calendar.HOUR_OF_DAY);
		}
		Calendar cal = Calendar.getInstance();
		int to = cal.get(Calendar.HOUR_OF_DAY);
		configureDownload(downloader, station, null, to-from+1);
	}

	private void configureDownload(Downloader downloader, Station station, Calendar cal, int hours) {
		downloader.setUrl("https://aviationweather.gov/cgi-bin/data/metar.php");
		downloader.addParam("ids", station.getCode());
		downloader.addParam("format", "json");
		downloader.addParam("taf", false);
		downloader.addParam("hours", hours);
		if( cal != null ) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssX");
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			downloader.addParam("date", sdf.format(cal.getTime()));
		}
	}

	@Override
	public void updateStation(Station station, String data) {
		try {
			JSONArray array = new JSONArray(data);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			for (int i = 0; i < array.length(); i++) {
				Meteo meteo = station.getMeteo();
				JSONObject json = array.getJSONObject(i);
				long stamp = json.getLong("obsTime") * 1000;
				parse(json, "wdir", 1, stamp, meteo.getWindDirection());
				parse(json, "wspd", 1.852, stamp, meteo.getWindSpeedMed());
				parse(json, "wgst", 1.852, stamp, meteo.getWindSpeedMax());
				parse(json, "temp", 1, stamp, meteo.getAirTemperature());
				parse(json, "altim", 1, stamp, meteo.getAirPressure());
			}
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}

	private void parse(JSONObject json, String key, double factor, long stamp, Measurement meas) throws JSONException {
		if( json.isNull(key) || json.get(key) instanceof String)
			return;
		double value = json.getDouble(key);
		meas.put(stamp, value * factor);
	}
}
