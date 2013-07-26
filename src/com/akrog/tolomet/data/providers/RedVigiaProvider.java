package com.akrog.tolomet.data.providers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Calendar;

import android.annotation.SuppressLint;

import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.data.Downloader;
import com.akrog.tolomet.data.Station;
import com.akrog.tolomet.view.MyCharts;

public class RedVigiaProvider extends AbstractProvider {	
	public RedVigiaProvider( Tolomet tolomet ) {
		super(tolomet);
		this.separator = '.';
	}
	
	@SuppressLint("DefaultLocale")
	public void download(Station station, Calendar past, Calendar now) {
		this.station = station;
		this.downloader = new Downloader(this.tolomet, this);
		this.downloader.useLineBreak(true);
		this.downloader.setUrl("http://www.redvigia.es/Historico.aspx");
		this.downloader.addParam("codigoBoya", station.code);
		this.downloader.addParam("numeroDatos", "5");
		this.downloader.addParam("tipo", "1");
		this.downloader.addParam("variable", "1");
		this.downloader.execute();
	}

	@Override
	protected void updateStation(String data) {		
		try {
			BufferedReader rd = new BufferedReader(new StringReader(data));
			String line;
			String[] fields;
			Number date, val;
			while( (line=rd.readLine()) != null ) {
				if( line.contains("Velocidad") )
					break;
			}
			if( line == null )
				return;
			//this.station.clear();
			while( rd.readLine() != null ) {
				line = rd.readLine();	// Skip first line
				if( line.contains("table") )
					break;
				fields = line.split("</td><td>");
				if( fields.length != 11 )
					continue;
				date = toEpoch(getContent(fields[1],false));
				val = Integer.parseInt(getContent(fields[4],true).replaceAll("\\..*", ""));
		        updateList(this.station.listDirection, date, val);
		        val = Float.parseFloat(getContent(fields[2],true))*3.6F;
		        updateList(this.station.listSpeedMed, date, val);
		        val = Float.parseFloat(getContent(fields[3],true))*3.6F;
		        updateList(this.station.listSpeedMax, date, val);
		        val = MyCharts.convertHumidity((int)Float.parseFloat(getContent(fields[5],true)));
	        	updateList(this.station.listHumidity, date, val);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}				
	}		

	public int getRefresh() {
		return 60;
	}		
	
	private long toEpoch( String str ) {
		Calendar cal = Calendar.getInstance();
		String[] tmp = str.split(" ");
		String[] date = tmp[0].split("/");
		String[] time = tmp[1].split(":");
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(date[1])-1);
		cal.set(Calendar.YEAR, Integer.parseInt(date[2]));
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
		cal.set(Calendar.MINUTE, Integer.parseInt(time[1]));
		cal.set(Calendar.SECOND, Integer.parseInt(time[2]));
		cal.set(Calendar.MILLISECOND, 0 );
	    return cal.getTimeInMillis();
	}
	
	private String getContent( String str, boolean first ) {
		str = str.replaceAll("<font.*\">", "");
		str = str.replaceAll("</font>", "");
		if( first )
			str = str.split(" ")[0]; 
		return str.replace(',', this.separator); 	
	}	
	
	public String getInfoUrl(String code) {
		return "http://www.redvigia.es/DetalleBoya.aspx?codigoBoya="+code;
	}
	
	private char separator;
}
