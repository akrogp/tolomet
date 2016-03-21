package com.akrog.tolomet.providers;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class PradesProvider extends BaseProvider {

	public PradesProvider() {
		super(5);
	}
	
	@Override
	public void configureDownload(Downloader downloader, Station station) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		downloader.setUrl(String.format(
			"http://www.meteoprades.cat/export/tolomet_%04d%02d%02d_%s.txt",
			cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH),
			station.getCode() ));
	}

	@Override
	public void updateStation(Station station, String data) {
		String[] lines = data.split("\\n");
		if( lines.length < 1 )
			return;
		
		String[] fields;
		long date;
		Number num;
		station.clear();
		for( int i = 0; i < lines.length; i++ ) {
			fields = lines[i].split(",");
			date = parseDate(fields[0]);
			if( fields.length > 1 && (num=parseDir(fields[1])) != null )
				station.getMeteo().getWindDirection().put(date, num);
			if( (num=getField(2, fields)) != null )
				station.getMeteo().getWindSpeedMed().put(date, num);
			if( (num=getField(3, fields)) != null )
				station.getMeteo().getWindSpeedMax().put(date, num);
			if( (num=getField(4, fields)) != null )
				station.getMeteo().getAirHumidity().put(date, num);
			if( (num=getField(5, fields)) != null )
				station.getMeteo().getAirTemperature().put(date, num);
			if( (num=getField(6, fields)) != null )
				station.getMeteo().getAirPressure().put(date, num);
		}		
	}

	private long parseDate(String str) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		String[] hour = str.split(":");
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour[0]));
		cal.set(Calendar.MINUTE, Integer.parseInt(hour[1]));
		return cal.getTimeInMillis();
	}
	
	private Number parseDir( String dir ) {
		if( dir.equalsIgnoreCase("N") )									
			return 0;
		else if( dir.equalsIgnoreCase("NNE") )
			return 22.5;
		else if( dir.equalsIgnoreCase("NE") )
			return 45;
		else if( dir.equalsIgnoreCase("ENE") )
			return 67.5;
		else if( dir.equalsIgnoreCase("E") )
			return 90;
		else if( dir.equalsIgnoreCase("NNW") )
			return 337.5;
		else if( dir.equalsIgnoreCase("NW") )
			return 315;
		else if( dir.equalsIgnoreCase("WNW") )
			return 292.5;
		else if( dir.equalsIgnoreCase("W") )
			return 270;
		else if( dir.equalsIgnoreCase("S") )
			return 180;
		else if( dir.equalsIgnoreCase("SSE") )
			return 157.5;
		else if( dir.equalsIgnoreCase("SE") )
			return 135;
		else if( dir.equalsIgnoreCase("ESE") )
			return 112.5;
		else if( dir.equalsIgnoreCase("SSW") )
			return 202.5;
		else if( dir.equalsIgnoreCase("SW") )
			return 225;
		else if( dir.equalsIgnoreCase("WSW") )
			return 247.5;
		return null;
	}
	
	private Double getField( int i, String[] fields ) {
		if( i >= fields.length )
			return null;
		if( fields[i].isEmpty() )
			return null;
		return Double.parseDouble(fields[i]);
	}

	@Override
	public String getInfoUrl(String code) {
		if( mapInfo == null ) {
			mapInfo = new HashMap<String, String>();
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/res/prades.csv")));
				String line;
				String[] fields;
				while( (line=br.readLine()) != null ) {
					fields = line.split(",");
					mapInfo.put(fields[0], fields[1]);
				}
				br.close();
			} catch( Exception e ) {				
			}
		}
		return mapInfo.get(code);
	}

	@Override
	public String getUserUrl(String code) {
		return getInfoUrl(code);
	}

	private Map<String, String> mapInfo;
}
