package com.akrog.tolomet.data.providers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import android.annotation.SuppressLint;

import com.akrog.tolomet.R;
import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.data.Downloader;
import com.akrog.tolomet.data.Station;

public class EuskalmetProvider extends AbstractProvider {
	public EuskalmetProvider( Tolomet tolomet ) {
		super(tolomet);
		loadCols();
		this.separator = '.';//(new DecimalFormatSymbols()).getDecimalSeparator();
	}
	
	@SuppressLint("DefaultLocale")
	public void download(Station station, Calendar past, Calendar now) {
		this.station = station;
		String time1 = String.format("%02d:%02d", past.get(Calendar.HOUR_OF_DAY), past.get(Calendar.MINUTE) );
		String time2 = String.format("%02d:%02d", now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE) );
		this.downloader = new Downloader(this.tolomet, this);
		//this.downloader.setUrl("http://www.euskalmet.euskadi.net/s07-5853x/es/meteorologia/lectur_fr.apl");
		this.downloader.setUrl("http://www.euskalmet.euskadi.net/s07-5853x/es/meteorologia/lectur_imp.apl");
		this.downloader.addParam("e", "5");
		this.downloader.addParam("anyo", now.get(Calendar.YEAR));
		this.downloader.addParam("mes", now.get(Calendar.MONTH)+1);
		this.downloader.addParam("dia", now.get(Calendar.DAY_OF_MONTH));
		this.downloader.addParam("hora", "%s %s", time1, time2);
		this.downloader.addParam("CodigoEstacion", station.code);
		this.downloader.addParam("pagina", "1");
		this.downloader.addParam("R01HNoPortal", "true");
		this.downloader.execute();
	}
	
	@Override
	protected void updateStation(String data) {
		int col = this.humidityCol.containsKey(station.code) ? this.humidityCol.get(station.code) : -1;
	    String[] lines = data.split("<tr ?>");
	    Number date, val;		        
	    for( int i = 4; i < lines.length; i++ ) {
	        String[] cells = lines[i].split("<td");
	        if( getContent(cells[1]).equals("Med") )
	        	break;
	        if( getContent(cells[2]).equals("-") )
	        	continue;
	        date = toEpoch(getContent(cells[1]));
	        val = Integer.parseInt(getContent(cells[3]));
	        this.station.listDirection.add(date);
	        this.station.listDirection.add(val);
	        if( col >= 0 )
	        	try {	// We can go on without humidity data		        	
		        	val = (float)Integer.parseInt(getContent(cells[col]));
		        	this.station.listHumidity.add(date);
		        	this.station.listHumidity.add(val);
	        	} catch( Exception e ) {}
	        val = Float.parseFloat(getContent(cells[2]));
	        this.station.listSpeedMed.add(date);
	        this.station.listSpeedMed.add(val);
	        val = Float.parseFloat(getContent(cells[4]));
	        this.station.listSpeedMax.add(date);
	        this.station.listSpeedMax.add(val);
	        val = Float.parseFloat(getContent(cells[7]));
	        this.station.listTemperature.add(date);
	        this.station.listTemperature.add(val);
	        val = Float.parseFloat(getContent(cells[10]));
	        this.station.listPressure.add(date);
	        this.station.listPressure.add(val);
	    }
	}

	public int getRefresh() {
		return 10;
	}		
	
	private long toEpoch( String str ) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		String[] fields = str.split(":");
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(fields[0]) );
	    cal.set(Calendar.MINUTE, Integer.parseInt(fields[1]) );
	    return cal.getTimeInMillis();
	}
	
	private String getContent( String cell ) {
		cell = cell.replaceAll("\n","").replaceAll("\r","").replaceAll("\t","").replaceAll(" ","");
		int i = cell.indexOf('>')+1;
		if( cell.charAt(i) == '<' )
			i = cell.indexOf('>', i)+1;
		int i2 = cell.indexOf('<', i);
		//return cell.substring(i, i2).replace(',', '.');
		return cell.substring(i, i2).replace(',', this.separator);
	}
	
	private void loadCols() {
		this.humidityCol = new HashMap<String, Integer>();
		
		InputStream inputStream = this.tolomet.getResources().openRawResource(R.raw.euskalmet);
		InputStreamReader in = new InputStreamReader(inputStream);
		BufferedReader rd = new BufferedReader(in);
		String line;
		String[] fields;
		try {
			while( (line=rd.readLine()) != null ) {
				fields = line.split(",");
				this.humidityCol.put(fields[0], Integer.parseInt(fields[1]));
			}
			rd.close();
		} catch( Exception e ) {			
		}
    }
	
	public String getInfoUrl(String code) {
		return "http://www.euskalmet.euskadi.net/s07-5853x/es/meteorologia/estacion.apl?e=5&campo="+code;
	}
	
	private Map<String,Integer> humidityCol;
	private char separator;
}