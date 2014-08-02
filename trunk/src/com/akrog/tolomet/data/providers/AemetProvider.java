package com.akrog.tolomet.data.providers;

import java.util.Calendar;

import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.data.Downloader;
import com.akrog.tolomet.data.Station;

public class AemetProvider extends AbstractProvider {
	public AemetProvider( Tolomet tolomet ) {
		super(tolomet);
	}
	
	public void download(Station station, Calendar past, Calendar now) {
		this.station = station;
		this.downloader = new Downloader(this.tolomet, this);
		this.downloader.setUrl("http://www.aemet.es/es/eltiempo/observacion/ultimosdatos.csv");
		this.downloader.addParam("l",station.code);
		this.downloader.addParam("datos","det");
		this.downloader.addParam("x","h24");
		this.downloader.execute();
	}
	
	@Override	
	protected void updateStation(String data) {			
		String[] cells = data.split("\"");
		String dir;
		Number date, num, last = 0;
		if( cells.length < 42 )
			return;	
		this.station.clear();
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
			this.station.listDirection.add(date);				
			this.station.listDirection.add(num);
			try {	// We can go on without humidity data
				num = (float)(int)(Float.parseFloat(cells[i+18])+0.5F);				
				this.station.listHumidity.add(date);
				this.station.listHumidity.add(num);
			} catch( Exception e ) {}
			num = Float.parseFloat(cells[i+4]);
			this.station.listSpeedMed.add(date);
			this.station.listSpeedMed.add(num);
			num = Float.parseFloat(cells[i+8]);
			this.station.listSpeedMax.add(date);
			this.station.listSpeedMax.add(num);
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