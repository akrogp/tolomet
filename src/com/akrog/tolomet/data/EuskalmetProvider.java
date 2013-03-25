package com.akrog.tolomet.data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.content.Context;

import com.akrog.tolomet.R;
import com.akrog.tolomet.view.MyCharts;

public class EuskalmetProvider implements WindProvider {
	public EuskalmetProvider( Context context ) {
		this.context = context;
		loadCols();
		this.separator = '.';//(new DecimalFormatSymbols()).getDecimalSeparator();
	}
	
	@SuppressLint("DefaultLocale")
	public String getUrl(Station station, Calendar past, Calendar now) {		
		String time1 = String.format("%02d:%02d", past.get(Calendar.HOUR_OF_DAY), past.get(Calendar.MINUTE) );
		String time2 = String.format("%02d:%02d", now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE) );
		return String.format(
	    		"%s&anyo=%d&mes=%02d&dia=%02d&hora=%s%%20%s&CodigoEstacion=%s&pagina=1&R01HNoPortal=true",
	    		new Object[]{
	    		"http://www.euskalmet.euskadi.net/s07-5853x/es/meteorologia/lectur_fr.apl?e=5",
	    		now.get(Calendar.YEAR), now.get(Calendar.MONTH)+1, now.get(Calendar.DAY_OF_MONTH),
	    		time1, time2, station.code
	    		} );
	}

	public void updateStation(Station station, String data) {
		int col = this.humidityCol.containsKey(station.code) ? this.humidityCol.get(station.code) : -1;
	    String[] lines = data.split("<tr>");
	    Number date, val;		        
	    for( int i = 1; i < lines.length; i++ ) {
	        String[] cells = lines[i].split("<td");
	        if( getContent(cells[1]).equals("Med") )
	        	break;
	        if( getContent(cells[2]).equals("-") )
	        	continue;
	        date = toEpoch(getContent(cells[1]));
	        val = Integer.parseInt(getContent(cells[3]));
	        station.listDirection.add(date);
	        station.listDirection.add(val);
	        if( col >= 0 )
	        	try {	// We can go on without humidity data		        	
		        	val = MyCharts.convertHumidity(Integer.parseInt(getContent(cells[col])));
		        	station.listHumidity.add(date);
		        	station.listHumidity.add(val);
	        	} catch( Exception e ) {}
	        val = Float.parseFloat(getContent(cells[2]));
	        station.listSpeedMed.add(date);
	        station.listSpeedMed.add(val);
	        val = Float.parseFloat(getContent(cells[4]));
	        station.listSpeedMax.add(date);
	        station.listSpeedMax.add(val);
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
		
		InputStream inputStream = this.context.getResources().openRawResource(R.raw.euskalmet);
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
	private Context context;	
}
