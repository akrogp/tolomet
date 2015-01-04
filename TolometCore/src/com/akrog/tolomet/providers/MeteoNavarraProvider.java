package com.akrog.tolomet.providers;

import java.util.Calendar;
import java.util.TimeZone;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

public class MeteoNavarraProvider implements WindProvider {
	@Override
	public void refresh(Station station) {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		String time1 = String.format("%d/%d/%d", now.get(Calendar.DAY_OF_MONTH), now.get(Calendar.MONTH)+1, now.get(Calendar.YEAR) );
		String time2 = String.format("%d/%d/%d", now.get(Calendar.DAY_OF_MONTH)+1, now.get(Calendar.MONTH)+1, now.get(Calendar.YEAR) );
		downloader = new Downloader();
		downloader.useLineBreak(false);
		downloader.setUrl("http://meteo.navarra.es/download/estacion_datos.cfm");
		downloader.addParam("IDEstacion",station.getCode().substring(2));
		downloader.addParam("p_10","7");
		downloader.addParam("p_10","2");
		downloader.addParam("p_10","9");
		downloader.addParam("p_10","6");
		downloader.addParam("p_10","1");
		downloader.addParam("fecha_desde",time1);
		downloader.addParam("fecha_hasta",time2);
		downloader.addParam("dl","csv");
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
		String[] cells = data.split(",");
		long date;
		Number num;
		if( cells.length < 26 )
			return;	
		station.clear();
		for( int i = 18; i < cells.length; i+=8 ) {
			if( cells[i].equals("\"\"") || cells[i+1].equals("\"- -\"") )
				continue;
			date = toEpoch(getContent(cells[i]));
			num = Integer.parseInt(getContent(cells[i+1]));
			station.getMeteo().getWindDirection().put(date, num);			
			num = Float.parseFloat(getContent(cells[i+6]));
			station.getMeteo().getWindSpeedMed().put(date, num);
			num = Float.parseFloat(getContent(cells[i+4]));
			station.getMeteo().getWindSpeedMax().put(date, num);
			try {
				num = (float)Integer.parseInt(getContent(cells[i+2]));
				station.getMeteo().getAirHumidity().put(date, num);
			} catch( Exception e ) {}
			try {
				num = Float.parseFloat(getContent(cells[i+7]));
				station.getMeteo().getAirTemperature().put(date, num);
			} catch( Exception e ) {}
		}		
	}

	@Override
	public int getRefresh( String code ) {
		return 10;
	}
	
	@Override
	public String getInfoUrl(String code) {
		return "http://meteo.navarra.es/estaciones/estacion_detalle.cfm?idestacion="+code.substring(2);
	}	

	private long toEpoch( String str ) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		String[] fields = str.substring(10).split(":");
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(fields[0]) );
	    cal.set(Calendar.MINUTE, Integer.parseInt(fields[1]) );
	    return cal.getTimeInMillis();
	}
	
	private String getContent( String cell ) {
		return cell.replaceAll("\"","").replace('.',this.separator);
	}
	
	private final char separator = '.';
	private Downloader downloader;
}
