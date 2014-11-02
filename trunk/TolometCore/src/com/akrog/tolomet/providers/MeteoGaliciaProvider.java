package com.akrog.tolomet.providers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

public class MeteoGaliciaProvider implements WindProvider {
	public MeteoGaliciaProvider() {
		loadParams();
	}
		
	@Override
	public void refresh(com.akrog.tolomet.Station station) {
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
	public void cancel() {
		downloader.cancel();	
	}

	protected void updateStation(Station station, String data) {
		if( data == null )
			return;
		XMLEventReader rd;
		try {
			rd = XMLInputFactory.newInstance().createXMLEventReader(new StringReader(data));
		} catch (Exception e) {
			return;
		}
		long date=0;
		Number val;
		try {
			//parser.require(XmlPullParser.START_TAG, null, "Estacion");
			station.clear();
			while( rd.hasNext() ) {
				XMLEvent event = rd.nextEvent();
				if( !event.isStartElement() )
					continue;				
				StartElement element = event.asStartElement();
				String name = element.getName().getLocalPart();
				if( name.equals("Valores") ) {					
					date = toEpoch(element.getAttributeByName(new QName("Data")).getValue());
				} else if( name.equals("Medida") ) {
					String attr = element.getAttributeByName(new QName("ID")).getValue();
					if( attr.equals("81") ) {
						if( element.getAttributeByName(new QName("Unidades")).getValue().equals("m/s") )
							val = Float.parseFloat(getContent(element))*3.6F;
						else
							val = Float.parseFloat(getContent(element));
				        station.getMeteo().getWindSpeedMed().put(date, val);
					} else if( attr.equals("86") ) {
						val = (float)Integer.parseInt(getContent(element));
			        	station.getMeteo().getAirHumidity().put(date, val);
					} else if( attr.equals("82") || attr.equals("10124") ) {
						val = Integer.parseInt(getContent(element));
				        station.getMeteo().getWindDirection().put(date, val);
					} else if( attr.equals("10003") ) {
						if( element.getAttributeByName(new QName("Unidades")).getValue().equals("m/s") )
							val = Float.parseFloat(getContent(element))*3.6F;
						else
							val = Float.parseFloat(getContent(element));
				        station.getMeteo().getWindSpeedMax().put(date, val);
					}
				}
			}
		} catch( Exception e ) {
			e.printStackTrace();
		}
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
	
	private String getContent( StartElement element ) {
		String val = element.getAttributeByName(new QName("Valor")).getValue(); 	
		return val.replace(',', this.separator);
	}
	
	private void loadParams() {
		urlParams = new HashMap<String, String>();
		
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
	
	private Map<String,String> urlParams;
	private final char separator = '.';
	private Downloader downloader;	
}
