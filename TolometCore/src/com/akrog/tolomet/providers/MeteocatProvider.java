package com.akrog.tolomet.providers;

import java.util.Calendar;
import java.util.TimeZone;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

public class MeteocatProvider implements WindProvider {
	@Override
	public void refresh(Station station) {
		downloader = new Downloader();
		downloader.setMethod("POST");
		downloader.useLineBreak(false);
		downloader.setUrl("http://www.meteo.cat/xema/AppJava/Detall24Estacio.do");
		downloader.addParam("idEstacio", station.getCode());
		downloader.addParam("team", "ObservacioTeledeteccio");
		downloader.addParam("inputSource", "DadesActualsEstacio");
		updateStation(station,downloader.download());
	}
	
	@Override
	public void cancel() {
		downloader.cancel();	
	}

	protected void updateStation(Station station, String data) {
		if( data == null )
			return;
		
		long date; 
		Number val;
		String fields[] = data.split("<td");
		if( fields.length < 11 )			
			return;
		station.clear();
		for( int i = 1; i < fields.length; i += 10 ) {
			date = toEpoch(getContent(fields[i],2));
			val = Integer.parseInt(getContent(fields[i+6],4));
			station.getMeteo().getWindDirection().put(date, val);
			val = Float.parseFloat(getContent(fields[i+6],2).replaceAll(" -", ""))*3.6F;
			station.getMeteo().getWindSpeedMed().put(date, val);
			val = Float.parseFloat(getContent(fields[i+7],2))*3.6F;
			station.getMeteo().getWindSpeedMax().put(date, val);
			try {
				val = (float)Integer.parseInt(getContent(fields[i+4],2));
				station.getMeteo().getAirHumidity().put(date, val);
			} catch( Exception e ) {};
			try {
				val = Float.parseFloat(getContent(fields[i+1],2));
				station.getMeteo().getAirTemperature().put(date, val);
			} catch( Exception e ) {};
			try {
				val = Float.parseFloat(getContent(fields[i+8],2));
				station.getMeteo().getAirPressure().put(date, val);
			} catch( Exception e ) {};
		}
		station.getMeteo().sort();
	}

	@Override
	public int getRefresh( String code ) {
		return 30;
	}		
	
	private long toEpoch( String str ) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		String[] tmp = str.split(" ");
		String[] date = tmp[0].split("/");
		String[] time = tmp[1].split("-")[1].replace(")", "").split(":");
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(date[1])-1);
		cal.set(Calendar.YEAR, Integer.parseInt(date[2]));
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
		cal.set(Calendar.MINUTE, Integer.parseInt(time[1]));
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0 );
	    return cal.getTimeInMillis();
	}
	
	private String getContent( String td, int pos ) {
		String string = td.split(">")[pos].replaceAll("<.*", "").trim();
		return string;
	}
	
	@Override
	public String getInfoUrl(String code) {
		return "http://www.meteo.cat/xema/AppJava/SeleccioPerComarca.do";
	}
	
	private Downloader downloader; 
}
