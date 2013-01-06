package com.akrog.tolomet;

import android.annotation.SuppressLint;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class EuskalmetProvider implements WindProvider {
	public EuskalmetProvider() {
		loadCols();
		mSeparator = '.';//(new DecimalFormatSymbols()).getDecimalSeparator();
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
	    		time1, time2, station.Code
	    		} );
	}

	public void updateStation(Station station, String data) {
		int col = mHumidityCol.containsKey(station.Code) ? mHumidityCol.get(station.Code) : -1;
	    String[] lines = data.split("<tr>");
	    Number date, val;		        
	    for( int i = 1; i < lines.length; i++ ) {
	        String[] cells = lines[i].split("<td");
	        if( getContent(cells[1]).equals("Med") || getContent(cells[2]).equals("-") )
	        	break;
	        date = toEpoch(getContent(cells[1]));
	        val = Integer.parseInt(getContent(cells[3]));
	        station.ListDirection.add(date);
	        station.ListDirection.add(val);
	        if( col >= 0 ) {
	        	station.ListHumidity.add(date);
	        	val = Tolomet.convertHumidity(Integer.parseInt(getContent(cells[col])));
	        	station.ListHumidity.add(val);
	        }
	        val = Float.parseFloat(getContent(cells[2]));
	        station.ListSpeedMed.add(date);
	        station.ListSpeedMed.add(val);
	        val = Float.parseFloat(getContent(cells[4]));
	        station.ListSpeedMax.add(date);
	        station.ListSpeedMax.add(val);
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
		return cell.substring(i, i2).replace(',', mSeparator);
	}
	
	private void loadCols() {
    	mHumidityCol = new HashMap<String, Integer>();
    	mHumidityCol.put("C072", 9);
    	mHumidityCol.put("C042", 8);
    	mHumidityCol.put("C0B4", 8);
    }
	
	private Map<String,Integer> mHumidityCol;
	private char mSeparator;
}