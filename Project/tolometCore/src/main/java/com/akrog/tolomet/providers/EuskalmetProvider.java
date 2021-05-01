package com.akrog.tolomet.providers;

import com.akrog.tolomet.Header;
import com.akrog.tolomet.Measurement;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.utils.Utils;
import com.akrog.tolomet.io.Downloader;
import com.akrog.tolomet.io.Downloader.FakeBrowser;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EuskalmetProvider implements WindProvider {
	
	@Override
	public String getInfoUrl(String code) {
		return "https://www.euskalmet.euskadi.eus/s07-5853x/es/meteorologia/estacion.apl?e=5&campo="+code;
	}

	@Override
	public String getUserUrl(String code) {
		//return "https://www.euskalmet.euskadi.eus/s07-5853x/es/meteorologia/lectur.apl?e=5&campo="+code;
		return "https://www.euskalmet.euskadi.eus/s07-5853x/es/meteorologia/datos/mapaesta.apl?e=5&campo="+code;
	}

	@Override
	public List<Station> downloadStations() {
		Downloader dw = new Downloader();
		dw.setUrl("https://opendata.euskadi.eus/contenidos/ds_meteorologicos/estaciones_meteorologicas/opendata/estaciones.json");
		String data = dw.download();
		data = data.replaceAll("\n","");
		Matcher object = PATTERN_OBJECT.matcher(data);
		List<Station> result = new ArrayList<>();
		while( object.find() ) {
			Matcher field = PATTERN_FIELD.matcher(object.group(1));
			Station station = new Station();
			station.setRegion(183);
			station.setProviderType(WindProviderType.Euskalmet);
			while( field.find() ) {
				String key = field.group(1);
				String value = field.group(2);
				if (key.equals("documentName"))
					station.setName(value);
				else if( key.equals("dataXML") ) {
					Matcher code = PATTERN_CODE.matcher(value);
					if( !code.find() || !downloadCoords(station, value))
						return null;
					station.setCode(code.group(1).toUpperCase());
				}
			}
			Utils.utm2ll(station);
			result.add(station);
		}
		return result;
	}

	private boolean downloadCoords(Station station, String url) {
		Downloader dw = new Downloader();
		dw.setUrl(url);
		int fields = 2;
		try {
			String xml = dw.download().replaceAll("&", "");
			XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(new StringReader(xml));
			parser.nextTag();
			parser.require(XmlPullParser.START_TAG, null, "stationData");
			while(fields > 0 && parser.next() != XmlPullParser.END_DOCUMENT) {
				if (parser.getEventType() != XmlPullParser.START_TAG)
            		continue;
				String name = parser.getName();
				if( name.equals("latitudeUTM") ) {
					station.setLatitude(readDouble(parser));
					fields--;
				} else if( name.equals("longitudeUTM")) {
					station.setLongitude(readDouble(parser));
					fields--;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return fields == 0;
	}

	private Double readDouble(XmlPullParser parser) throws IOException, XmlPullParserException {
		if (parser.next() == XmlPullParser.TEXT)
			return Double.parseDouble(parser.getText());
		return null;
	}

	@Override
	public void refresh(Station station) {
		travel(station, System.currentTimeMillis());
	}

	@Override
	public boolean travel(Station station, long date) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
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
						updateMeasurement(station.getMeteo().getWindDirection(), sensor, hist);
					else if( name.equals("max_speed") )
						updateMeasurement(station.getMeteo().getWindSpeedMax(), sensor, hist, 3.6);
				} else if( type.equals("measuresForAir") ) {
					if( name.equals("temperature") )
						updateMeasurement(station.getMeteo().getAirTemperature(), sensor, hist);
					else if( name.equals("humidity") )
						updateMeasurement(station.getMeteo().getAirHumidity(), sensor, hist);
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
			val = data.getDouble(time)*factor;
			meas.put(date, val);
		}
	}

	private long toEpoch( String str, Long date ) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("CET"));
		if( date != null )
			cal.setTimeInMillis(date);
		String[] fields = str.split(":");
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(fields[0]) );
	    cal.set(Calendar.MINUTE, Integer.parseInt(fields[1]) );
	    return cal.getTimeInMillis();
	}

	private Downloader downloader;
	private static final Pattern PATTERN_FIELD = Pattern.compile("\"([^\"]*)\" ?: ?\"([^\"}]*)\"");
	private static final Pattern PATTERN_OBJECT = Pattern.compile("\\{([^\\}]*)\\}");
	private static final Pattern PATTERN_CODE = Pattern.compile("station_([^\\/]*)\\/");
}
