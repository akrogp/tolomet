package com.akrog.tolomet.providers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

public class EuskalmetProvider implements WindProvider {
	
	public EuskalmetProvider() {
		loadCols();
	}
	
	@Override
	public String getInfoUrl(String code) {
		return "http://www.euskalmet.euskadi.net/s07-5853x/es/meteorologia/estacion.apl?e=5&campo="+code;
	}
	
	@Override
	public void refresh(Station station) {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		Calendar past = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		if( station.isEmpty() ) {
			past.set(Calendar.HOUR_OF_DAY,0);
			past.set(Calendar.MINUTE,0);
		} else
			past.setTimeInMillis(station.getStamp());
		
		downloader = new Downloader();
		String time1 = String.format("%02d:%02d", past.get(Calendar.HOUR_OF_DAY), past.get(Calendar.MINUTE) );
		String time2 = String.format("%02d:%02d", now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE) );
		//downloader.setUrl("http://www.euskalmet.euskadi.net/s07-5853x/es/meteorologia/lectur_fr.apl");
		downloader.setUrl("http://www.euskalmet.euskadi.net/s07-5853x/es/meteorologia/lectur_imp.apl");
		downloader.addParam("e", "5");
		downloader.addParam("anyo", now.get(Calendar.YEAR));
		downloader.addParam("mes", now.get(Calendar.MONTH)+1);
		downloader.addParam("dia", now.get(Calendar.DAY_OF_MONTH));
		downloader.addParam("hora", "%s %s", time1, time2);
		downloader.addParam("CodigoEstacion", station.getCode());
		downloader.addParam("pagina", "1");
		downloader.addParam("R01HNoPortal", "true");
		updateStation(station, downloader.download());
	}

	@Override
	public void cancel() {
		downloader.cancel();
	}

	@Override
	public int getRefresh(String code) {
		return 10;
	}	
	
	private void updateStation(Station station, String data) {
		if( data == null )
			return;
		
		int col = this.humidityCol.containsKey(station.getCode()) ? this.humidityCol.get(station.getCode()) : -1;
	    String[] lines = data.split("<tr ?>");
	    long date;
	    Number val;		        
	    for( int i = 4; i < lines.length; i++ ) {
	        String[] cells = lines[i].split("<td");
	        if( getContent(cells[1]).equals("Med") )
	        	break;
	        if( getContent(cells[2]).equals("-") )
	        	continue;
	        date = toEpoch(getContent(cells[1]));
	        val = Integer.parseInt(getContent(cells[3]));
	        station.getMeteo().getWindDirection().put(date, val);
	        if( col >= 0 )
	        	try {	// We can go on without humidity data		        	
		        	val = (float)Integer.parseInt(getContent(cells[col]));
		        	station.getMeteo().getAirHumidity().put(date, val);
	        	} catch( Exception e ) {}
	        val = Float.parseFloat(getContent(cells[2]));
	        station.getMeteo().getWindSpeedMed().put(date, val);
	        val = Float.parseFloat(getContent(cells[4]));
	        station.getMeteo().getWindSpeedMax().put(date, val);
	        val = Float.parseFloat(getContent(cells[7]));
	        station.getMeteo().getAirTemperature().put(date, val);
	        val = Float.parseFloat(getContent(cells[10]));
	        station.getMeteo().getAirPressure().put(date, val);
	    }
	}
	
	private long toEpoch( String str ) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		String[] fields = str.split(":");
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(fields[0]) );
	    cal.set(Calendar.MINUTE, Integer.parseInt(fields[1]) );
	    return cal.getTimeInMillis();
	}
	
	private String getContent( String cell ) {
		cell = cell.replaceAll("\n","").replaceAll("\r","").replaceAll("\t","").replaceAll(" ","");
		int i = cell.indexOf('>')+1;
		if( cell.charAt(i) == '<' )
			i = cell.indexOf('>', i)+1;
		int i2 = cell.indexOf('<', i);
		return cell.substring(i, i2).replace(',', this.separator);
	}
	
	private void loadCols() {
		this.humidityCol = new HashMap<String, Integer>();
		
		InputStream inputStream = getClass().getResourceAsStream("/res/euskalmet.csv");
		InputStreamReader in = new InputStreamReader(inputStream);
		BufferedReader rd = new BufferedReader(in);
		String line;
		String[] fields;
		try {
			while( (line=rd.readLine()) != null ) {
				fields = line.split(",");
				this.humidityCol.put(fields[0], Integer.parseInt(fields[1]));
			}
			rd.close();
		} catch( Exception e ) {			
		}
    }	
	
	private Map<String,Integer> humidityCol;
	private final char separator = '.';	
	private Downloader downloader;
}
