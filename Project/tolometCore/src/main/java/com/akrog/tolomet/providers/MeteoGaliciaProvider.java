package com.akrog.tolomet.providers;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;
import com.akrog.tolomet.io.XmlElement;
import com.akrog.tolomet.io.XmlParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class MeteoGaliciaProvider implements WindProvider {
	public MeteoGaliciaProvider() {
		loadParams();
	}
		
	@Override
	public void refresh(Station station) {
		String[] fields = urlParams.get(station.getCode()).split(":");
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		String time = String.format("%d/%d/%d", now.get(Calendar.DAY_OF_MONTH), now.get(Calendar.MONTH)+1, now.get(Calendar.YEAR) );
		
		downloader = new Downloader();
		downloader.setUrl("http://www2.meteogalicia.es/galego/observacion/estacions/contidos/DatosHistoricosXML_dezminutal.asp");
		downloader.addParam("est", station.getCode());
		downloader.addParam("param", fields[1]);
		downloader.addParam("data1", time);
		downloader.addParam("data2", time);
		downloader.addParam("idprov", fields[2]);
		downloader.addParam("red", fields[3]);
		updateStation(station, downloader.download());
	}

	@Override
	public boolean travel(Station station, long date) {
		return false;
	}

	@Override
	public void cancel() {
		if( downloader != null )
			downloader.cancel();	
	}

	protected void updateStation(Station station, String data) {
		if( data == null )
			return;
		XmlElement root;
		try {
			root = XmlParser.load(new StringReader(data));
		} catch (IOException e) {
			return;
		}
		if( root == null || !root.getName().equals("Estacion") )
			return;
		
		long date=0;
		Number val;
		station.clear();
		for( XmlElement element : root.getSubElements() ) {
			String name = element.getName();
			if( !name.equals("Valores") )
				continue;
			date = toEpoch(element.getAttribute("Data"));
			for( XmlElement meas : element.getSubElements() ) {					
				String attr = meas.getAttribute("ID");
				if( attr.equals("82") || attr.equals("10124") ) {
					val = Integer.parseInt(getContent(meas));
					station.getMeteo().getWindDirection().put(date, val);
				} else if( attr.equals("81") ) {
					station.getMeteo().getWindSpeedMed().put(date, getSpeed(meas));
				} else if( attr.equals("10003") ) {
					station.getMeteo().getWindSpeedMax().put(date, getSpeed(meas));
				} else if( attr.equals("86") ) {
					try {
						val = Float.parseFloat(getContent(meas));
						station.getMeteo().getAirHumidity().put(date, val);
					} catch( Exception e ) {}
				} else if( attr.equals("83") ) {
					try {
						val = Float.parseFloat(getContent(meas));
						station.getMeteo().getAirTemperature().put(date, val);
					} catch( Exception e ) {}
				} else if( attr.equals("10002") ) {
					try {
						val = Float.parseFloat(getContent(meas));
						station.getMeteo().getAirPressure().put(date, val);
					} catch( Exception e ) {}
				}
			}
		}
	}
	
	private float getSpeed( XmlElement meas ) {
		float val = Float.parseFloat(getContent(meas));
		if( meas.getAttribute("Unidades").equals("m/s") )
			return val*3.6F;
		return val;
	}

	@Override
	public int getRefresh(String code) {
		return 10;
	}		
	
	private long toEpoch( String str ) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
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
	
	private String getContent( XmlElement element ) {
		String val = element.getAttribute("Valor"); 	
		return val.replace(',', this.separator);
	}
	
	private void loadParams() {
		urlParams = new HashMap<>();
		
		InputStream inputStream = getClass().getResourceAsStream("/res/meteogalicia.csv");
		InputStreamReader in = new InputStreamReader(inputStream);
		BufferedReader rd = new BufferedReader(in);
		String line;
		String[] fields;
		try {
			while( (line=rd.readLine()) != null ) {
				fields = line.split(":");
				urlParams.put(fields[0], line);
			}
			rd.close();
		} catch( Exception e ) {			
		}
    }
	
	@Override
	public String getInfoUrl(String code) {
		return "http://www2.meteogalicia.es/galego/observacion/estacions/estacionsinfo.asp?Nest="+code;
	}

	@Override
	public String getUserUrl(String code) {
		return "http://www2.meteogalicia.es/galego/observacion/estacions/estacions.asp?idEst="+code;
	}

	private Map<String,String> urlParams;
	private final char separator = '.';
	private Downloader downloader;	
}
