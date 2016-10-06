package com.akrog.tolomet.providers;

import com.akrog.tolomet.Meteo;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrentVantageProvider implements WindProvider {
	@Override
	public void refresh(Station station) {
		downloader = new Downloader();
		downloader.setUrl(URL);
		String data = downloader.download("Lluvia");
		if( data == null )
			return;
		
		String[] fields = data.split("<td ");
		Meteo meteo = station.getMeteo();
		
		long date = parseDate(parseField(fields[3].replaceAll(".*;","")));
		Number val = Float.parseFloat(parseNumber(fields[6]));
		meteo.getAirTemperature().put(date, val);
		
		val = Float.parseFloat(parseNumber(fields[8]));
		meteo.getAirHumidity().put(date, val);
		
		String wind = parseField(fields[12]);
		val = parseSpeed(wind);
		meteo.getWindSpeedMed().put(date, val);
		
		val = parseDirection(wind);
		meteo.getWindDirection().put(date, val);
		
		val = Float.parseFloat(parseNumber(fields[14]));
		meteo.getAirPressure().put(date, val);
	}

	@Override
	public boolean travel(Station station, long date) {
		return false;
	}

	private Number parseDirection(String wind) {
		Matcher matcher = patternDir.matcher(wind);
		if( !matcher.find() )
			return null;		
		return Integer.parseInt(matcher.group(1));
	}

	private Number parseSpeed(String wind) {
		return Float.parseFloat(wind.replaceAll(" .*", ""))*1.852F;
	}

	private long parseDate(String field) {
		field = field.replaceAll("&nbsp;","");
		String[] sub = field.split(" / ");
		String[] date = sub[0].split("/");
		String[] hour = sub[1].split(":");
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
		cal.set(Calendar.YEAR, Integer.parseInt(date[2])+2000);
		cal.set(Calendar.MONTH, Integer.parseInt(date[1])-1);
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[0]));
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour[0]));
		cal.set(Calendar.MINUTE, Integer.parseInt(hour[1]));;
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	private String parseField( String field ) {
		Matcher matcher = patternField.matcher(field.replaceAll("\\n","").replaceAll("> +<", ""));
		if( !matcher.find() )
			return null;
		return matcher.group(1);
	}
	
	private String parseNumber( String field ) {
		return parseField(field).replaceAll("[^0-9\\.\\-\\+]", "");
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

	@Override
	public String getInfoUrl(String code) {
		return URL;
	}

	@Override
	public String getUserUrl(String code) {
		return URL;
	}

	private Downloader downloader;
	private final Pattern patternField = Pattern.compile(".*>([^<>]+)<.*");
	private final Pattern patternDir = Pattern.compile(".*\\((.+).\\).*");
	private final static String URL = "http://www.rcnlaredo.es/~meteorcnl/meteo/Current_Vantage_Pro.html";
}
