package com.akrog.tolomet.providers;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.utils.Utils;
import com.akrog.tolomet.io.Downloader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class AemetProvider implements WindProvider {

	public AemetProvider() {
		sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	@Override
	public String getInfoUrl(Station sta) {
		return "http://www.aemet.es/es/eltiempo/observacion/ultimosdatos?l=" + sta.getCode() + "&datos=det&w=0";
	}

	@Override
	public String getUserUrl(Station sta) {
		return getInfoUrl(sta);
	}

	private String download(String url) {
		try {
			if( key == null )
				try(BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/keys/aemet.txt")))) {
					key = br.readLine();
				}
			dw = new Downloader();
			dw.setUrl(url);
			dw.setHeader("api_key", key);
			String data = dw.download();
			JSONObject resp = new JSONObject(data);

			dw = new Downloader();
			dw.setUrl(resp.getString("datos"));
			data = dw.download(null, "latin1");
			return data;
		} catch( Exception e ) {
			e.printStackTrace();
		} finally {
			dw = null;
		}
		return null;
	}

	@Override
	public List<Station> downloadStations() {
		try {
			String data = download("https://opendata.aemet.es/opendata/api/observacion/convencional/todas");
			JSONArray array = new JSONArray(data);
			List<Station> stations = new ArrayList<>(array.length());
			for( int i = 0; i < array.length(); i++ ) {
				JSONObject json = array.getJSONObject(i);
				Station station = new Station();
				station.setName(Utils.reCapitalize(json.getString("ubi")));
				station.setLatitude(json.getDouble("lat"));
				station.setLongitude(json.getDouble("lon"));
				station.setCode(json.getString("idema"));
				station.setProviderType(WindProviderType.Aemet);
				stations.add(station);
			}
			return stations;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void refresh(Station station) {
		try {
			String data = download("https://opendata.aemet.es/opendata/api/observacion/convencional/datos/estacion/" + station.getCode());
			JSONArray array = new JSONArray(data);
			for( int i = 0; i < array.length(); i++ ) {
				JSONObject json = array.getJSONObject(i);
				long date = sdf.parse(json.getString("fint")).getTime();
				Number num;

				num = json.optDouble("dv");
				if( num != null )
					station.getMeteo().getWindDirection().put(date, num);
				num = json.optDouble("vv");
				if( num != null )
					station.getMeteo().getWindSpeedMed().put(date, num.floatValue()*3.6F);
				num = json.optDouble("vmax");
				if( num != null )
					station.getMeteo().getWindSpeedMax().put(date, num.floatValue()*3.6F);

				num = json.optDouble("ta");
				if( num != null )
					station.getMeteo().getAirTemperature().put(date, num);
				num = json.optDouble("hr");
				if( num != null )
					station.getMeteo().getAirHumidity().put(date, num);
				num = json.optDouble("pres");
				if( num != null )
					station.getMeteo().getAirPressure().put(date, num);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean travel(Station station, long date) {
		return false;
	}

	@Override
	public void cancel() {
		if( dw != null )
			dw.cancel();
	}

	@Override
	public int getRefresh(String code) {
		return 60;
	}

	private Downloader dw;
	private String key;
	private final SimpleDateFormat sdf;
}