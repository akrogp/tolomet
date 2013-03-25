package com.akrog.tolomet.data;

import java.util.Calendar;

import android.annotation.SuppressLint;

import com.akrog.tolomet.Tolomet;

public class AemetProvider implements WindProvider {	
	@SuppressLint("DefaultLocale")
	public String getUrl(Station station, Calendar past, Calendar now) {
		return String.format(
			"http://www.aemet.es/es/eltiempo/observacion/ultimosdatos.csv?l=%s&datos=det&x=h24",
			new Object[]{
					station.Code
			} );
	}

	public void updateStation(Station station, String data) {			
		String[] cells = data.split("\"");
		String dir;
		Number date, num;
		if( cells.length < 42 )
			return;	
		station.clear();
		for( int i = cells.length-19; i >= 23; i-=20 ) {
			if( cells[i].equals("\"\"") )
				break;
			date = toEpoch(cells[i]);
			dir = cells[i+6];
			if( dir.equalsIgnoreCase("Norte") )									
				num = 0;
			else if( dir.equalsIgnoreCase("Noreste") )
				num = 45;
			else if( dir.equalsIgnoreCase("Este") )
				num = 90;
			else if( dir.equalsIgnoreCase("Sudeste") )
				num = 135;
			else if( dir.equalsIgnoreCase("Sur") )
				num = 180;
			else if( dir.equalsIgnoreCase("Sudoeste") )
				num = 225;
			else if( dir.equalsIgnoreCase("Oeste") )
				num = 270;
			else if( dir.equalsIgnoreCase("Noroeste") )
				num = 315;
			else
				num = 0;
			station.ListDirection.add(date);				
			station.ListDirection.add(num);
			try {	// We can go on without humidity data
				num = Tolomet.convertHumidity(Integer.parseInt(cells[i+18]));
				station.ListHumidity.add(date);
				station.ListHumidity.add(num);
			} catch( Exception e ) {}
			num = Float.parseFloat(cells[i+4]);
			station.ListSpeedMed.add(date);
			station.ListSpeedMed.add(num);
			num = Float.parseFloat(cells[i+8]);
			station.ListSpeedMax.add(date);
			station.ListSpeedMax.add(num);
		}				
	}

	public int getRefresh() {
		return 60;
	}	

	private long toEpoch( String str ) {
		Calendar cal = Calendar.getInstance();		
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(str.substring(0,2)));
		cal.set(Calendar.MONTH, Integer.parseInt(str.substring(3,5))-1);
		cal.set(Calendar.YEAR, Integer.parseInt(str.substring(6,10)));
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(str.substring(11,13)));
		cal.set(Calendar.MINUTE, Integer.parseInt(str.substring(14)));
		cal.set(Calendar.SECOND, 0);
	    return cal.getTimeInMillis();
	}

	public String getInfoUrl(String code) {
		return "http://www.aemet.es/es/eltiempo/observacion/ultimosdatos?l="+code+"&datos=det";
	}	
}