package com.akrog.tolomet.data;

import java.util.Calendar;
import java.util.TimeZone;

import android.annotation.SuppressLint;

import com.akrog.tolomet.Tolomet;

public class MeteoNavarraProvider implements WindProvider {
	public MeteoNavarraProvider() {
		mSeparator = '.';//(new DecimalFormatSymbols()).getDecimalSeparator();
	}
	
	@SuppressLint("DefaultLocale")
	public String getUrl(Station station, Calendar past, Calendar now) {
		String time1 = String.format("%d/%d/%d", now.get(Calendar.DAY_OF_MONTH), now.get(Calendar.MONTH)+1, now.get(Calendar.YEAR) );
		String time2 = String.format("%d/%d/%d", now.get(Calendar.DAY_OF_MONTH)+1, now.get(Calendar.MONTH)+1, now.get(Calendar.YEAR) );
		return String.format(
			"%s?IDEstacion=%s&p_10=7&p_10=2&p_10=9&p_10=6&fecha_desde=%s&fecha_hasta=%s&dl=csv",
			new Object[]{
			"http://meteo.navarra.es/download/estacion_datos.cfm",
			station.Code.substring(2), time1, time2
			} );
	}

	public void updateStation(Station station, String data) {		
		String[] cells = data.split(",");
		Number date, num;
		if( cells.length < 23 )
			return;	
		station.clear();
		for( int i = 16; i < cells.length; i+=7 ) {
			if( cells[i].equals("\"\"") || cells[i+1].equals("\"- -\"") )
				break;
			date = toEpoch(getContent(cells[i]));
			num = Integer.parseInt(getContent(cells[i+1]));
			station.ListDirection.add(date);
			station.ListDirection.add(num);
			try {	// We can go on without humidity data
				num = Tolomet.convertHumidity(Integer.parseInt(getContent(cells[i+2])));
				station.ListHumidity.add(date);
				station.ListHumidity.add(num);
			} catch( Exception e ) {}
			num = Float.parseFloat(getContent(cells[i+6]));
			station.ListSpeedMed.add(date);
			station.ListSpeedMed.add(num);
			num = Float.parseFloat(getContent(cells[i+4]));
			station.ListSpeedMax.add(date);
			station.ListSpeedMax.add(num);
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
		return cell.replaceAll("\"","").replace('.',mSeparator);
	}
	
	private char mSeparator;	
}
