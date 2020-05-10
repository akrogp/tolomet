package com.akrog.tolomet.providers;

import com.akrog.tolomet.Header;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.utils.Utils;
import com.akrog.tolomet.io.Downloader;
import com.akrog.tolomet.io.Downloader.FakeBrowser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EuskalmetProvider implements WindProvider {
	
	@Override
	public String getInfoUrl(String code) {
		return "http://www.euskalmet.euskadi.eus/s07-5853x/es/meteorologia/estacion.apl?e=5&campo="+code;
	}

	@Override
	public String getUserUrl(String code) {
		return "http://www.euskalmet.euskadi.eus/s07-5853x/es/meteorologia/lectur.apl?e=5&campo="+code;
	}

	@Override
	public List<Station> downloadStations() {
		Downloader dw = new Downloader();
		dw.setUrl("http://opendata.euskadi.eus/contenidos/ds_meteorologicos/estaciones_meteorologicas/opendata/estaciones.json");
		String data = dw.download().replaceAll("\n","");
		Matcher object = PATTERN_OBJECT.matcher(data);
		List<Station> result = new ArrayList<>();
		while( object.find() ) {
			Matcher field = PATTERN_FIELD.matcher(object.group(1));
			Station station = new Station();
			station.setRegion(183);
			station.setProviderType(WindProviderType.Euskalmet);
			while( field.find() ) {
				String key = field.group(1);
				String value = field.group(2);
				if (key.equals("Nombre"))
					station.setName(value);
				else if( key.equals("XMLdatos") ) {
					Matcher code = PATTERN_CODE.matcher(value);
					if( !code.find() || !downloadCoords(station, value))
						return null;
					station.setCode(code.group(1).toUpperCase());
				}
			}
			Utils.utm2ll(station);
			result.add(station);
		}
		return result;
	}

	int kk;

	private boolean downloadCoords(Station station, String url) {
		Downloader dw = new Downloader();
		dw.setUrl(url);
		int fields = 2;
		try {
			String xml = dw.download().replaceAll("&", "");
			XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(new StringReader(xml));
			parser.nextTag();
			parser.require(XmlPullParser.START_TAG, null, "stationData");
			while(fields > 0 && parser.next() != XmlPullParser.END_DOCUMENT) {
				if (parser.getEventType() != XmlPullParser.START_TAG)
            		continue;
				String name = parser.getName();
				if( name.equals("latitudeUTM") ) {
					station.setLatitude(readDouble(parser));
					fields--;
				} else if( name.equals("longitudeUTM")) {
					station.setLongitude(readDouble(parser));
					fields--;
				}
			}
			kk++;
		} catch (Exception e) {
			System.err.println(kk);
			e.printStackTrace();
			return false;
		}
		return fields == 0;
	}

	private Double readDouble(XmlPullParser parser) throws IOException, XmlPullParserException {
		if (parser.next() == XmlPullParser.TEXT)
			return Double.parseDouble(parser.getText());
		return null;
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
		downloader.setUrl("http://www.euskalmet.euskadi.eus/s07-5853x/es/meteorologia/lectur_imp.apl");
		downloader.addParam("e", "5");
		downloader.addParam("anyo", now.get(Calendar.YEAR));
		downloader.addParam("mes", now.get(Calendar.MONTH)+1);
		downloader.addParam("dia", now.get(Calendar.DAY_OF_MONTH));
		downloader.addParam("hora", "%s %s", time1, time2);
		downloader.addParam("CodigoEstacion", station.getCode());
		downloader.addParam("pagina", "1");
		downloader.addParam("R01HNoPortal", "true");
		updateStation(station, downloader.download(),null);
	}

	@Override
	public boolean travel(Station station, long date) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.setTimeInMillis(date);

        downloader = new Downloader();
        downloader.setBrowser(FakeBrowser.WGET);
        downloader.setUrl("http://www.euskalmet.euskadi.eus/s07-5853x/es/meteorologia/lectur_imp.apl");
        downloader.addParam("e", "5");
        downloader.addParam("anyo", cal.get(Calendar.YEAR));
        downloader.addParam("mes", cal.get(Calendar.MONTH)+1);
        downloader.addParam("dia", cal.get(Calendar.DAY_OF_MONTH));
        downloader.addParam("hora", "00:00 23:59");
        downloader.addParam("CodigoEstacion", station.getCode());
        downloader.addParam("pagina", "1");
        downloader.addParam("R01HNoPortal", "true");
        updateStation(station, downloader.download(), date);
		return true;
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
	
	private void updateStation(Station station, String data, Long hist) {
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
	        date = toEpoch(getContent(cells[index.getDate()]), hist);
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

	private long toEpoch( String str, Long date ) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		if( date != null )
			cal.setTimeInMillis(date);
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
	private static final Pattern PATTERN_FIELD = Pattern.compile("\"([^\"]*)\" ?: ?\"([^\"}]*)\"");
	private static final Pattern PATTERN_OBJECT = Pattern.compile("\\{([^\\}]*)\\}");
	private static final Pattern PATTERN_CODE = Pattern.compile("station_([^\\/]*)\\/");
}
