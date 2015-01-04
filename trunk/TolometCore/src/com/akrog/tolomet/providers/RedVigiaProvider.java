package com.akrog.tolomet.providers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Calendar;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

public class RedVigiaProvider implements WindProvider {	
	@Override
	public void refresh(com.akrog.tolomet.Station station) {
		downloader = new Downloader();
		downloader.setUrl("http://www.redvigia.es/Historico.aspx");
		downloader.addParam("codigoBoya", station.getCode());
		downloader.addParam("numeroDatos", "5");
		downloader.addParam("tipo", "1");
		downloader.addParam("variable", "1");
		updateStation(station, downloader.download());
	}
	
	@Override
	public void cancel() {
		if( downloader != null )
			downloader.cancel();
	}

	protected void updateStation(Station station, String data) {
		if( data == null )
			return;
		try {
			BufferedReader rd = new BufferedReader(new StringReader(data));
			String line;
			String[] fields;
			long date;
			Number val;
			while( (line=rd.readLine()) != null ) {
				if( line.contains("Velocidad") )
					break;
			}
			if( line == null )
				return;
			//this.station.clear();
			while( rd.readLine() != null ) {
				line = rd.readLine();	// Skip first line
				if( line.contains("table") )
					break;
				fields = line.split("</td><td>");
				if( fields.length != 11 )
					continue;
				date = toEpoch(getContent(fields[1],false));
				val = Integer.parseInt(getContent(fields[4],true).replaceAll("\\..*", ""));
		        station.getMeteo().getWindDirection().put(date, val);
		        val = Float.parseFloat(getContent(fields[2],true))*3.6F;
		        station.getMeteo().getWindSpeedMed().put(date, val);
		        val = Float.parseFloat(getContent(fields[3],true))*3.6F;
		        station.getMeteo().getWindSpeedMax().put(date, val);
		        try {
			        val = Float.parseFloat(getContent(fields[5],true));
		        	station.getMeteo().getAirHumidity().put(date, val);
		        } catch( Exception e ) {}
		        try {
			        val = Float.parseFloat(getContent(fields[7],true));
		        	station.getMeteo().getAirPressure().put(date, val);
		        } catch( Exception e ) {}
		        try {
			        val = Float.parseFloat(getContent(fields[10],true));
		        	station.getMeteo().getAirTemperature().put(date, val);
		        } catch( Exception e ) {}	        	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}				
	}		

	@Override
	public int getRefresh( String code ) {
		return 60;
	}		
	
	private long toEpoch( String str ) {
		Calendar cal = Calendar.getInstance();
		String[] tmp = str.split(" ");
		String[] date = tmp[0].split("/");
		String[] time = tmp[1].split(":");
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(date[1])-1);
		cal.set(Calendar.YEAR, Integer.parseInt(date[2]));
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
		cal.set(Calendar.MINUTE, Integer.parseInt(time[1]));
		cal.set(Calendar.SECOND, Integer.parseInt(time[2]));
		cal.set(Calendar.MILLISECOND, 0 );
	    return cal.getTimeInMillis();
	}
	
	private String getContent( String str, boolean first ) {
		str = str.replaceAll("<font.*\">", "");
		str = str.replaceAll("</font>", "");
		if( first )
			str = str.split(" ")[0]; 
		return str.replace(',', this.separator); 	
	}	
	
	@Override
	public String getInfoUrl(String code) {
		return "http://www.redvigia.es/DetalleBoya.aspx?codigoBoya="+code;
	}
	
	private final char separator = '.';
	private Downloader downloader;
}
