package com.akrog.tolomet.providers;

import com.akrog.tolomet.Header;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

import java.util.Calendar;
import java.util.TimeZone;

public class MetarProvider extends BaseProvider {
	public MetarProvider() {
		super(5);
	}

	@Override
	public String getInfoUrl(String code) {
		return "https://www.aviationweather.gov/adds/metars?std_trans=translated&chk_metars=on&station_ids="+code;
	}

	@Override
	public String getUserUrl(String code) {
		return getInfoUrl(code);
	}

	@Override
	public boolean configureDownload(Downloader downloader, Station station, long date ) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		long from = cal.getTimeInMillis();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		long to = cal.getTimeInMillis();
		configureDownload(downloader, station, from, to);
		return true;
	}

	@Override
	public void configureDownload(Downloader downloader, Station station) {
		Long stamp = station.getStamp();
		if( stamp == null ) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			stamp = cal.getTimeInMillis();
		}
		configureDownload(downloader, station, stamp, System.currentTimeMillis());
	}

	public void configureDownload(Downloader downloader, Station station, long from, long to) {
		downloader.setUrl("https://www.aviationweather.gov/adds/dataserver_current/httpparam");
		downloader.addParam("dataSource","metars");
		downloader.addParam("requestType","retrieve");
		downloader.addParam("format","csv");
		downloader.addParam("startTime",from/1000);
		downloader.addParam("endTime",to/1000);
		downloader.addParam("stationString",station.getCode());
	}

	@Override
	public void updateStation(Station station, String data) {
		String[] lines = data.split("\\n");
		if( lines.length < 7 )
			return;
		Header index = getHeaders(lines[5]);
		
		String[] fields;
		String field;
		long date; 
		for(int i = 6; i < lines.length; i++ ) {
			fields = lines[i].split(",");
			if( (field=getField(index.getDate(), fields)) == null )
				continue;
			date = parseDate(field);
			if( (field=getField(index.getDir(), fields)) != null )
				station.getMeteo().getWindDirection().put(date, Double.parseDouble(field));
			if( (field=getField(index.getMed(), fields)) != null )
				station.getMeteo().getWindSpeedMed().put(date, Double.parseDouble(field)*1.852);
			if( (field=getField(index.getMax(), fields)) != null )
				station.getMeteo().getWindSpeedMax().put(date, Double.parseDouble(field)*1.852);
			if( (field=getField(index.getTemp(), fields)) != null )
				station.getMeteo().getAirTemperature().put(date, Double.parseDouble(field));
			if( (field=getField(index.getPres(), fields)) != null )
				station.getMeteo().getAirPressure().put(date, Double.parseDouble(field));
		}		
	}
	
	private long parseDate(String field) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		String[] fields = field.split("T");
		String[] date = fields[0].split("-");
		String[] time = fields[1].split(":");
		cal.set(Calendar.YEAR, Integer.parseInt(date[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(date[1])-1);
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[2]));
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
		cal.set(Calendar.MINUTE, Integer.parseInt(time[1]));
		return cal.getTimeInMillis();
	}

	private String getField( int i, String[] fields ) {
		if( i >= fields.length )
			return null;
		if( fields[i].isEmpty() )
			return null;
		return fields[i];
	}

	private Header getHeaders(String string) {
		String[] cells = string.split(",");
		Header index = new Header();
		index.findDate("observation_time", cells);
		index.findDir("wind_dir_degrees", cells);
		index.findMed("wind_speed_kt", cells);
		index.findMax("wind_gust_kt", cells);
		index.findTemp("temp_c", cells);
		index.findPres("sea_level_pressure_mb", cells);
		return index;
	}	
}
