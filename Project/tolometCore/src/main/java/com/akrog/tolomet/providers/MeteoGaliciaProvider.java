package com.akrog.tolomet.providers;

import com.akrog.tolomet.Meteo;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MeteoGaliciaProvider implements WindProvider {

	@Override
	public String getInfoUrl(String code) {
		return "https://www.meteogalicia.gal/observacion/estacions/estacions.action?idEst="+code;
	}

	@Override
	public String getUserUrl(String code) {
		return String.format("https://www.meteogalicia.gal/observacion/meteovisor/indexChartDezHoxe.action?idEstacion=%s&nome=%s&dataSeleccionada=%s",
			code, code, SDF_USER.format(new Date()));
	}

	@Override
	public List<Station> downloadStations() {
		try {
			dw = new Downloader();
			dw.setUrl("https://servizos.meteogalicia.gal/rss/observacion/listaEstacionsMeteo.action");
			String data = dw.download();
			JSONObject obj = new JSONObject(data);
			JSONArray stations = obj.getJSONArray("listaEstacionsMeteo");
			List<Station> result = new ArrayList<>(stations.length());
			for( int i = 0; i < stations.length(); i++ ) {
				JSONObject json = stations.getJSONObject(i);
				Station station = new Station();
				station.setCode(json.getInt("idEstacion")+"");
				station.setName(json.getString("estacion"));
				station.setLatitude(json.getDouble("lat"));
				station.setLongitude(json.getDouble("lon"));
				station.setProviderType(WindProviderType.MeteoGalicia);
				result.add(station);
			}
			return result;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		} finally {
			dw = null;
		}
	}
		
	@Override
	public void refresh(Station station) {
		Long stamp = station.getStamp();
		Calendar prev = null;
		if( stamp != null ) {
			prev = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			prev.setTimeInMillis(stamp);
		}
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.add(Calendar.HOUR_OF_DAY, 1);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		String dataFin = SDF_REST.format(cal.getTime());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		String dataIni = SDF_REST.format(prev != null && prev.after(cal) ? prev.getTime() : cal.getTime());
		try {
			download(station, dataIni, dataFin);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void download(Station station, String dataIni, String dataFin) throws Exception {
		dw = new Downloader();
		dw.setUrl("https://servizos.meteogalicia.es/rss/observacion/datos10min.action");
		dw.addParam("cod", "ToloMet6");
		dw.addParam("idEstacion", station.getCode());
		dw.addParam("dataIni", dataIni);
		dw.addParam("dataFin", dataFin);
		String data = dw.download();
		JSONObject obj = new JSONObject(data);
		JSONArray array = obj.getJSONArray("list10Min");
		Meteo meteo = station.getMeteo();
		for( int i = 0; i < array.length(); i++ ) {
			JSONObject json = array.getJSONObject(i);
			long stamp = SDF_JSON.parse(json.getString("instanteLecturaUTC")).getTime();
			JSONArray meas = json.getJSONArray("listaEstacions").getJSONObject(0).getJSONArray("listaMedidas");
			for( int j = 0; j < meas.length(); j++ ) {
				JSONObject m = meas.getJSONObject(j);
				String param = m.getString("nomeParametro");
				double value = m.getDouble("valor");
				if( param.startsWith("Dirección do vento") )
					meteo.getWindDirection().put(stamp, value);
				else if( param.startsWith("Humidade") )
					meteo.getAirHumidity().put(stamp, value);
				else if( param.startsWith("Temperatura") )
					meteo.getAirTemperature().put(stamp, value);
				else if( param.startsWith("Velocidade") )
					meteo.getWindSpeedMed().put(stamp, value*3.6);
				else if( param.startsWith("Refacho") )
					meteo.getWindSpeedMax().put(stamp, value*3.6);
			}
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
		return 10;
	}

	private static SimpleDateFormat SDF_USER = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat SDF_REST = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private static SimpleDateFormat SDF_JSON = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	static {
		SDF_REST.setTimeZone(TimeZone.getTimeZone("UTC"));
		SDF_JSON.setTimeZone(TimeZone.getTimeZone("UTC"));
	}
	private Downloader dw;
}
