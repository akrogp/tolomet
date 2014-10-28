package com.akrog.tolomet.providers;

import java.util.Calendar;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

public class AemetProvider implements WindProvider {
	
	@Override
	public String getInfoUrl(String code) {
		return "http://www.aemet.es/es/eltiempo/observacion/ultimosdatos?l="+code+"&datos=det";
	}

	@Override
	public void refresh(Station station) {
		downloader = new Downloader();
		downloader.useLineBreak(false);
		downloader.setUrl("http://www.aemet.es/es/eltiempo/observacion/ultimosdatos.csv");
		downloader.addParam("l",station.getCode());
		downloader.addParam("datos","det");
		downloader.addParam("x","h24");
		updateStation(station,downloader.download());
	}

	@Override
	public void cancel() {
		downloader.cancel();		
	}

	@Override
	public int getRefresh(String code) {
		return 60;
	}
		
	private void updateStation(Station station, String data) {
		if( data == null )
			return;
		
		String[] cells = data.split("\"");
		String dir;
		long date; 
		Number num, last = 0;
		if( cells.length < 42 )
			return;
		
		station.clear();
		for( int i = cells.length-19; i >= 23; i-=20 ) {
			if( cells[i].length() == 0 || cells[i+6].length() == 0 )
				continue;
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
			else	// Calma
				num = last;
			last = num;
			station.getMeteo().getWindDirection().put(date, num);
			try {	// We can go on without humidity data
				num = (float)(int)(Float.parseFloat(cells[i+18])+0.5F);
				station.getMeteo().getAirHumidity().put(date, num);
			} catch( Exception e ) {}
			num = Float.parseFloat(cells[i+4]);
			station.getMeteo().getWindSpeedMed().put(date, num);
			num = Float.parseFloat(cells[i+8]);
			station.getMeteo().getWindSpeedMax().put(date, num);
		}				
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
	
	private Downloader downloader;
}