package com.akrog.tolomet.providers;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class MeteoNavarraProvider implements WindProvider {
    public MeteoNavarraProvider() {
        df = new SimpleDateFormat("dd/MM/yyyyH:mm");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

	@Override
	public void refresh(Station station) {
		travel(station, System.currentTimeMillis());
	}

	@Override
	public boolean travel(Station station, long date) {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		now.setTimeInMillis(date);
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
		return true;
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
		int i;
		for( i = 0; i < cells.length; i++ )
			if( cells[i].contains("Fecha") )
				break;
		for( i = i+16; i < cells.length; i+=8 ) {
			if( END_PATTERN.matcher(cells[i]).find() )
				break;
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

	@Override
	public String getUserUrl(String code) {
		return "http://meteo.navarra.es/estaciones/estacion.cfm?IDEstacion="+code.substring(2);
	}

	@Override
	public List<Station> downloadStations() {
		return null;
	}

	private long toEpoch( String str ) {
        try {
            return df.parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
	
	private String getContent( String cell ) {
		return cell.replaceAll("\"","").replace('.',this.separator);
	}

	private static final Pattern END_PATTERN = Pattern.compile("[a-zA-Z]");
	private final char separator = '.';
	private Downloader downloader;
    private final DateFormat df;
}
