package com.akrog.tolomet.providers;

import java.util.Calendar;
import java.util.TimeZone;

import android.annotation.SuppressLint;

import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.data.Downloader;
import com.akrog.tolomet.data.Station;

public class MeteoNavarraProvider extends AbstractProvider {
	public MeteoNavarraProvider( Tolomet tolomet ) {
		super(tolomet);
		this.separator = '.';//(new DecimalFormatSymbols()).getDecimalSeparator();
	}	
	
	@SuppressLint("DefaultLocale")
	public void download(Station station, Calendar past, Calendar now) {
		this.station = station;
		String time1 = String.format("%d/%d/%d", now.get(Calendar.DAY_OF_MONTH), now.get(Calendar.MONTH)+1, now.get(Calendar.YEAR) );
		String time2 = String.format("%d/%d/%d", now.get(Calendar.DAY_OF_MONTH)+1, now.get(Calendar.MONTH)+1, now.get(Calendar.YEAR) );
		this.downloader = new Downloader(this.tolomet, this);
		this.downloader.setUrl("http://meteo.navarra.es/download/estacion_datos.cfm");
		this.downloader.addParam("IDEstacion",station.code.substring(2));
		this.downloader.addParam("p_10","7");
		this.downloader.addParam("p_10","2");
		this.downloader.addParam("p_10","9");
		this.downloader.addParam("p_10","6");
		this.downloader.addParam("p_10","1");
		this.downloader.addParam("fecha_desde",time1);
		this.downloader.addParam("fecha_hasta",time2);
		this.downloader.addParam("dl","csv");
		this.downloader.execute();
	}
	
	@Override
	protected void updateStation(String data) {		
		String[] cells = data.split(",");
		Number date, num;
		if( cells.length < 26 )
			return;	
		this.station.clear();
		for( int i = 18; i < cells.length; i+=8 ) {
			if( cells[i].equals("\"\"") || cells[i+1].equals("\"- -\"") )
				continue;
			date = toEpoch(getContent(cells[i]));
			num = Integer.parseInt(getContent(cells[i+1]));
			this.station.listDirection.add(date);
			this.station.listDirection.add(num);
			try {	// We can go on without humidity data
				num = (float)Integer.parseInt(getContent(cells[i+2]));
				this.station.listHumidity.add(date);
				this.station.listHumidity.add(num);
			} catch( Exception e ) {}
			num = Float.parseFloat(getContent(cells[i+6]));
			this.station.listSpeedMed.add(date);
			this.station.listSpeedMed.add(num);
			num = Float.parseFloat(getContent(cells[i+4]));
			this.station.listSpeedMax.add(date);
			this.station.listSpeedMax.add(num);
			num = Float.parseFloat(getContent(cells[i+7]));
			this.station.listTemperature.add(date);
			this.station.listTemperature.add(num);
		}		
	}

	public int getRefresh() {
		return 10;
	}
	
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
	
	private char separator;
}
