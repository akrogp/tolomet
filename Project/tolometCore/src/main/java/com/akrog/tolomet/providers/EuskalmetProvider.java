package com.akrog.tolomet.providers;

import com.akrog.tolomet.Header;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;
import com.akrog.tolomet.io.Downloader.FakeBrowser;

import java.util.Calendar;
import java.util.TimeZone;

public class EuskalmetProvider implements WindProvider {
	
	@Override
	public String getInfoUrl(String code) {
		return "http://www.euskalmet.euskadi.net/s07-5853x/es/meteorologia/estacion.apl?e=5&campo="+code;
	}

	@Override
	public String getUserUrl(String code) {
		return "http://www.euskalmet.euskadi.eus/s07-5853x/es/meteorologia/lectur.apl?e=5&campo="+code;
	}

	@Override
	public void refresh(Station station) {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		Calendar past = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		if( !station.isEmpty() )
			past.setTimeInMillis(station.getStamp());
		if( station.isEmpty() ||
			(past.get(Calendar.YEAR) != now.get(Calendar.YEAR)) ||
			(past.get(Calendar.MONTH) != now.get(Calendar.MONTH)) ||
			(past.get(Calendar.DAY_OF_MONTH) != now.get(Calendar.DAY_OF_MONTH)) ) {
			past.set(Calendar.HOUR_OF_DAY,0);
			past.set(Calendar.MINUTE,0);
		}		
		
		downloader = new Downloader();
		downloader.setBrowser(FakeBrowser.WGET);
		String time1 = String.format("%02d:%02d", past.get(Calendar.HOUR_OF_DAY), past.get(Calendar.MINUTE) );
		String time2 = String.format("%02d:%02d", now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE) );
		//downloader.setUrl("http://www.euskalmet.euskadi.net/s07-5853x/es/meteorologia/lectur_fr.apl");
		downloader.setUrl("http://www.euskalmet.euskadi.net/s07-5853x/es/meteorologia/lectur_imp.apl");
		downloader.addParam("e", "5");
		downloader.addParam("anyo", now.get(Calendar.YEAR));
		downloader.addParam("mes", now.get(Calendar.MONTH)+1);
		downloader.addParam("dia", now.get(Calendar.DAY_OF_MONTH));
		downloader.addParam("hora", "%s %s", time1, time2);
		downloader.addParam("CodigoEstacion", station.getCode());
		downloader.addParam("pagina", "1");
		downloader.addParam("R01HNoPortal", "true");
		updateStation(station, downloader.download());
	}

	@Override
	public boolean getHistory(Station station, long date) {
		return false;
	}

	@Override
	public void cancel() {
		if( downloader != null )
			downloader.cancel();
	}

	@Override
	public int getRefresh(String code) {
		return 10;
	}	
	
	private void updateStation(Station station, String data) {
		if( data == null )
			return;		
		
	    String[] lines = data.split("<tr ?>");
	    Header index = getIndex(lines[3]);
	    long date;
	    Number val;		        
	    for( int i = 4; i < lines.length; i++ ) {
	        String[] cells = lines[i].split("<td");
	        if( getContent(cells[1]).equals("Med") )
	        	break;
	        if( getContent(cells[2]).equals("-") )
	        	continue;
	        date = toEpoch(getContent(cells[index.getDate()]));
	        if( index.getDir() > 0 ) {
		        val = Integer.parseInt(getContent(cells[index.getDir()]));
		        station.getMeteo().getWindDirection().put(date, val);
	        }
	        if( index.getMed() > 0 ) {
		        val = Float.parseFloat(getContent(cells[index.getMed()]));
		        station.getMeteo().getWindSpeedMed().put(date, val);
	        }
	        if( index.getMax() > 0 ) {
		        val = Float.parseFloat(getContent(cells[index.getMax()]));
		        station.getMeteo().getWindSpeedMax().put(date, val);
	        }
	        if( index.getHum() > 0 ) {
		        val = (float)Integer.parseInt(getContent(cells[index.getHum()]));
	        	station.getMeteo().getAirHumidity().put(date, val);
	        }
	        if( index.getTemp() > 0 ) {
		        val = Float.parseFloat(getContent(cells[index.getTemp()]));
		        station.getMeteo().getAirTemperature().put(date, val);
	        }
	        if( index.getPres() > 0 ) {
		        val = Float.parseFloat(getContent(cells[index.getPres()]));
		        station.getMeteo().getAirPressure().put(date, val);
	        }
	    }
	}
	
	private Header getIndex(String row) {
		Header index = new Header();
		String[] cells = row.split("<td");
		index.findDate("Hora",cells);		// 1
		index.findDir("Dir", cells);		// 3
		index.findMed("Vel.Med", cells);	// 2
		index.findMax("Vel.Max", cells);	// 4
		index.findTemp("Tem.Aire", cells);
		index.findHum("Humedad", cells);
		index.findPres("Pres", cells);
		return index;
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
		return cell.substring(i, i2).replace(',', this.separator);
	}
	
	private final char separator = '.';	
	private Downloader downloader;
}
