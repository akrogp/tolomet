package com.akrog.tolomet.providers;

import com.akrog.tolomet.Measurement;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;
import com.akrog.tolomet.io.Downloader.FakeBrowser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

public class EuskalmetProviderNew implements WindProvider {
	
	@Override
	public String getInfoUrl(String code) {
		return "https://www.euskalmet.euskadi.eus/observacion/datos-de-estaciones";
	}

	@Override
	public String getUserUrl(String code) {
		return "https://www.euskalmet.euskadi.eus/observacion/datos-de-estaciones";
	}

	@Override
	public List<Station> downloadStations() {
		Downloader dw = new Downloader();
		dw.setUrl("https://www.euskalmet.euskadi.eus/vamet/stations/stationList/stationList.json");
		String data = dw.download(null, "ISO-8859-1");
		try {
			JSONArray array = new JSONArray(data);
			List<Station> result = new ArrayList<>(array.length());
			for( int i = 0; i < array.length(); i++ ) {
				JSONObject json = array.getJSONObject(i);
				Station station = new Station();
				station.setRegion(183);
				station.setProviderType(WindProviderType.Euskalmet);
				station.setCode(json.getString("id"));
				station.setName(json.getString("name"));
				station.setLatitude(json.getDouble("y"));
				station.setLongitude(json.getDouble("x"));
				result.add(station);
			}
			return result;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	@Override
	public void refresh(Station station) {
		travel(station, System.currentTimeMillis());
	}

	@Override
	public boolean travel(Station station, long date) {
        Calendar cal = Calendar.getInstance(TIMEZONE);
        cal.setTimeInMillis(date);

        downloader = new Downloader();
        downloader.setBrowser(FakeBrowser.WGET);
        downloader.setUrl(String.format(
			"https://www.euskalmet.euskadi.eus/vamet/stations/readings/%s/%04d/%02d/%02d/readingsData.json",
				station.getCode(),
				cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH)+1,
				cal.get(Calendar.DAY_OF_MONTH)
		));
        updateStation(station, downloader.download(), date);
		return true;
	}

	@Override
	public void cancel() {
		if( downloader != null )
			downloader.cancel();
	}

	@Override
	public int getRefresh(String code) {
		return 10;
	}	
	
	private void updateStation(Station station, String data, Long hist) {
		if( data == null )
			return;

		try {
			JSONObject json = new JSONObject(data);
			Iterator it = json.keys();
			while( it.hasNext() ) {
				JSONObject sensor = json.getJSONObject(String.valueOf(it.next()));
				String type = sensor.getString("type");
				String name = sensor.getString("name");
				if( type.equals("measuresForWind") ) {
					if( name.equals("mean_speed") )
						updateMeasurement(station.getMeteo().getWindSpeedMed(), sensor, hist, 3.6);
					else if( name.equals("mean_direction") )
						updateIntMeasurement(station.getMeteo().getWindDirection(), sensor, hist);
					else if( name.equals("max_speed") )
						updateMeasurement(station.getMeteo().getWindSpeedMax(), sensor, hist, 3.6);
				} else if( type.equals("measuresForAir") ) {
					if( name.equals("temperature") )
						updateMeasurement(station.getMeteo().getAirTemperature(), sensor, hist);
					else if( name.equals("humidity") )
						updateIntMeasurement(station.getMeteo().getAirHumidity(), sensor, hist);
					else if( name.equals("pressure") )
						updateMeasurement(station.getMeteo().getAirPressure(), sensor, hist);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void updateMeasurement(Measurement meas, JSONObject json, Long hist) throws JSONException {
		updateMeasurement(meas, json, hist, 1.0);
	}

	private void updateIntMeasurement(Measurement meas, JSONObject json, Long hist) throws JSONException {
		updateMeasurement(meas, json, hist, -1.0);
	}

	private void updateMeasurement(Measurement meas, JSONObject json, Long hist, double factor) throws JSONException {
		JSONObject data = json.getJSONObject("data");
		data = data.getJSONObject(String.valueOf(data.keys().next()));
		String time;
		long date;
		Number val;
		Iterator it = data.keys();
		while( it.hasNext() ) {
			time = String.valueOf(it.next());
			date = toEpoch(time, hist);
			if( factor < 0 ) {
				val = Math.round(data.getDouble(time))*1.0;
			} else
				val = data.getDouble(time)*factor;
			meas.put(date, val);
		}
	}

	private long toEpoch( String str, Long date ) {
		Calendar cal = Calendar.getInstance(TIMEZONE);
		if( date != null )
			cal.setTimeInMillis(date);
		String[] fields = str.split(":");
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(fields[0]) );
	    cal.set(Calendar.MINUTE, Integer.parseInt(fields[1]) );
	    return cal.getTimeInMillis();
	}

	private Downloader downloader;
	private static final TimeZone TIMEZONE = TimeZone.getTimeZone("CET");
}
