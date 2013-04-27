package com.akrog.tolomet.data.providers;

import java.util.Calendar;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.data.Downloader;
import com.akrog.tolomet.data.Station;
import com.akrog.tolomet.data.WindProvider;
import com.akrog.tolomet.view.MyCharts;

public class MeteoNavarraProvider implements WindProvider {
	public MeteoNavarraProvider( Tolomet tolomet ) {
		this.separator = '.';//(new DecimalFormatSymbols()).getDecimalSeparator();
		this.tolomet = tolomet;
	}	
	
	@SuppressLint("DefaultLocale")
	public void download(Station station, Calendar past, Calendar now) {
		String time1 = String.format("%d/%d/%d", now.get(Calendar.DAY_OF_MONTH), now.get(Calendar.MONTH)+1, now.get(Calendar.YEAR) );
		String time2 = String.format("%d/%d/%d", now.get(Calendar.DAY_OF_MONTH)+1, now.get(Calendar.MONTH)+1, now.get(Calendar.YEAR) );
		this.downloader = new Downloader(this.tolomet);
		this.downloader.setUrl("http://meteo.navarra.es/download/estacion_datos.cfm");
		this.downloader.addParam("IDEstacion",station.code.substring(2));
		this.downloader.addParam("p_10","7");
		this.downloader.addParam("p_10","2");
		this.downloader.addParam("p_10","9");
		this.downloader.addParam("p_10","6");
		this.downloader.addParam("fecha_desde",time1);
		this.downloader.addParam("fecha_hasta",time2);
		this.downloader.addParam("dl","csv");
		this.downloader.execute();
	}
	
	public void cancelDownload() {
		if( this.downloader != null && this.downloader.getStatus() != AsyncTask.Status.FINISHED )
			this.downloader.cancel(true);
	}

	public void updateStation(Station station, String data) {		
		String[] cells = data.split(",");
		Number date, num;
		if( cells.length < 23 )
			return;	
		station.clear();
		for( int i = 16; i < cells.length; i+=7 ) {
			if( cells[i].equals("\"\"") || cells[i+1].equals("\"- -\"") )
				continue;
			date = toEpoch(getContent(cells[i]));
			num = Integer.parseInt(getContent(cells[i+1]));
			station.listDirection.add(date);
			station.listDirection.add(num);
			try {	// We can go on without humidity data
				num = MyCharts.convertHumidity(Integer.parseInt(getContent(cells[i+2])));
				station.listHumidity.add(date);
				station.listHumidity.add(num);
			} catch( Exception e ) {}
			num = Float.parseFloat(getContent(cells[i+6]));
			station.listSpeedMed.add(date);
			station.listSpeedMed.add(num);
			num = Float.parseFloat(getContent(cells[i+4]));
			station.listSpeedMax.add(date);
			station.listSpeedMax.add(num);
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
	private Tolomet tolomet;
	private Downloader downloader;
}
