package com.akrog.tolomet.providers;

import com.akrog.tolomet.Header;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.Utils;
import com.akrog.tolomet.io.Downloader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class AemetProvider implements WindProvider {
	
	@Override
	public String getInfoUrl(String code) {
		return "http://www.aemet.es/es/eltiempo/observacion/ultimosdatos?l="+code+"&datos=det&w=0";
	}

	@Override
	public String getUserUrl(String code) {
		return getInfoUrl(code);
	}

	@Override
	public List<Station> downloadStations() {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/keys/aemet.txt")))) {
			Downloader dw = new Downloader();
			dw.setUrl("https://opendata.aemet.es/opendata/api/observacion/convencional/todas");
			dw.setHeader("api_key", br.readLine());
			String data = dw.download();
			JSONObject resp = new JSONObject(data);

			dw = new Downloader();
			dw.setUrl(resp.getString("datos"));
			data = dw.download(null, "latin1");
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
		downloader = new Downloader();
		downloader.setUrl("http://www.aemet.es/es/eltiempo/observacion/ultimosdatos.csv");
		downloader.addParam("l",station.getCode());
		downloader.addParam("datos","det");
		downloader.addParam("x","h24");
		downloader.addParam("w","0");
		updateStation(station,downloader.download());
	}

	@Override
	public boolean travel(Station station, long date) {
		return false;
	}

	@Override
	public void cancel() {
		if( downloader != null )
			downloader.cancel();	
	}

	@Override
	public int getRefresh(String code) {
		return 60;
	}
		
	private void updateStation(Station station, String data) {
		if( data == null )
			return;
		
		String[] lines = data.replaceAll("\"","").split("\\n");
		if( lines.length < 5 )
			return;
		Header index = getHeaders(lines[3]);
		
		long date; 
		Number num, last = 0;
		String cell;
		station.clear();
		for( int i = lines.length-1; i >= 4; i-- ) {
			String[] cells = lines[i].split(",");
			if( cells.length <= index.getDate() || (cell=cells[index.getDate()]).isEmpty() )
				continue;
			date = toEpoch(station.getRegion() == 178, cells[index.getDate()]);
			if( index.getDir() < cells.length && !(cell=cells[index.getDir()]).isEmpty() ) {
				num = parseDir(cells[index.getDir()]);
				if( num == null )
					num = last;
				last = num;
				station.getMeteo().getWindDirection().put(date, num);
			}
			if( index.getMed() < cells.length && !(cell=cells[index.getMed()]).isEmpty() ) {
				num = Float.parseFloat(cell);
				station.getMeteo().getWindSpeedMed().put(date, num);
			}
			if( index.getMax() < cells.length && !(cell=cells[index.getMax()]).isEmpty() ) {
				num = Float.parseFloat(cell);
				station.getMeteo().getWindSpeedMax().put(date, num);
			}
			if( index.getHum() > 0 && index.getHum() < cells.length && !(cell=cells[index.getHum()]).isEmpty() ) {
				num = (float)(int)(Float.parseFloat(cell)+0.5F);
				station.getMeteo().getAirHumidity().put(date, num);
			}
			if( index.getTemp() > 0 && index.getTemp() < cells.length && !(cell=cells[index.getTemp()]).isEmpty() ) {
				num = Float.parseFloat(cell);
				station.getMeteo().getAirTemperature().put(date, num);
			}
			if( index.getPres() > 0 && index.getPres() < cells.length && !(cell=cells[index.getPres()]).isEmpty() ) {
				num = Float.parseFloat(cell);
				station.getMeteo().getAirPressure().put(date, num);
			}
		}
	}
	
	private Header getHeaders(String string) {
		String[] cells = string.split(",");
		Header index = new Header();
		index.findDate("Fecha", cells);	// 0
		index.findDir("Direc", cells);	// 3
		index.findMed("Velo", cells);	// 2
		index.findMax("Racha", cells);	// 4
		index.findTemp("Temp", cells);	// 1
		index.findHum("Hum", cells);	// 9
		index.findPres("Pres", cells);	// 7
		return index;
	}

	private Number parseDir( String dir ) {
		if( dir.equalsIgnoreCase("Norte") )									
			return 0;
		else if( dir.equalsIgnoreCase("Noreste") )
			return 45;
		else if( dir.equalsIgnoreCase("Este") )
			return 90;
		else if( dir.equalsIgnoreCase("Sudeste") )
			return 135;
		else if( dir.equalsIgnoreCase("Sur") )
			return 180;
		else if( dir.equalsIgnoreCase("Sudoeste") )
			return 225;
		else if( dir.equalsIgnoreCase("Oeste") )
			return 270;
		else if( dir.equalsIgnoreCase("Noroeste") )
			return 315;
		return null; // Calma
	}

	private long toEpoch( boolean canary, String str ) {
		Calendar cal = canary ? Calendar.getInstance(TimeZone.getTimeZone("Atlantic/Canary")) : Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));		
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(str.substring(0,2)));
		cal.set(Calendar.MONTH, Integer.parseInt(str.substring(3,5))-1);
		cal.set(Calendar.YEAR, Integer.parseInt(str.substring(6,10)));
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(str.substring(11,13)));
		cal.set(Calendar.MINUTE, Integer.parseInt(str.substring(14)));
		cal.set(Calendar.SECOND, 0);
	    return cal.getTimeInMillis();
	}	
	
	private Downloader downloader;
}