package com.akrog.tolomet.data.providers;

import java.util.Calendar;

import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.data.Downloader;
import com.akrog.tolomet.data.Station;
import com.akrog.tolomet.data.WindProvider;
import com.akrog.tolomet.view.MyCharts;

public class AemetProvider implements WindProvider {
	public AemetProvider( Tolomet tolomet ) {
		this.tolomet = tolomet;
	}
	
	public void download(Station station, Calendar past, Calendar now) {
		Downloader d = new Downloader(this.tolomet);
		d.setUrl("http://www.aemet.es/es/eltiempo/observacion/ultimosdatos.csv");
		d.addParam("l",station.code);
		d.addParam("datos","det");
		d.addParam("x","h24");
		d.execute();
	}

	public void updateStation(Station station, String data) {			
		String[] cells = data.split("\"");
		String dir;
		Number date, num, last = 0;
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
			station.listDirection.add(date);				
			station.listDirection.add(num);
			try {	// We can go on without humidity data
				num = MyCharts.convertHumidity((int)(Float.parseFloat(cells[i+18])+0.5F));				
				station.listHumidity.add(date);
				station.listHumidity.add(num);
			} catch( Exception e ) {}
			num = Float.parseFloat(cells[i+4]);
			station.listSpeedMed.add(date);
			station.listSpeedMed.add(num);
			num = Float.parseFloat(cells[i+8]);
			station.listSpeedMax.add(date);
			station.listSpeedMax.add(num);
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
	
	private Tolomet tolomet;	
}