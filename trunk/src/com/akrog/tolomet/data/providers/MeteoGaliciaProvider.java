package com.akrog.tolomet.data.providers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.xmlpull.v1.XmlPullParser;

import android.annotation.SuppressLint;
import android.util.Xml;

import com.akrog.tolomet.R;
import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.data.Downloader;
import com.akrog.tolomet.data.Station;
import com.akrog.tolomet.view.MyCharts;

public class MeteoGaliciaProvider extends AbstractProvider {
	public MeteoGaliciaProvider( Tolomet tolomet ) {
		super(tolomet);
		loadParams();
		this.separator = '.';//(new DecimalFormatSymbols()).getDecimalSeparator();
	}
	
	@SuppressLint("DefaultLocale")
	public void download(Station station, Calendar past, Calendar now) {
		this.station = station;
		String[] fields = this.urlParams.get(station.code).split(":"); 
		String time = String.format("%d/%d/%d", now.get(Calendar.DAY_OF_MONTH), now.get(Calendar.MONTH)+1, now.get(Calendar.YEAR) );
		this.downloader = new Downloader(this.tolomet, this);
		this.downloader.setUrl("http://www2.meteogalicia.es/galego/observacion/estacions/contidos/DatosHistoricosXML_dezminutal.asp");
		this.downloader.addParam("est", station.code);
		this.downloader.addParam("param", fields[1]);
		this.downloader.addParam("data1", time);
		this.downloader.addParam("data2", time);
		this.downloader.addParam("idprov", fields[2]);
		this.downloader.addParam("red", fields[3]);
		this.downloader.execute();
	}

	@Override
	protected void updateStation(String data) {
		Number date = null, val;
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);		
			parser.setInput(new StringReader(data));
			parser.nextTag();
			//parser.require(XmlPullParser.START_TAG, null, "Estacion");
			this.station.clear();
			while( parser.next() != XmlPullParser.END_DOCUMENT ) {			
				if( parser.getEventType() != XmlPullParser.START_TAG ) {
		            continue;
		        }
				String name = parser.getName();
				if( name.equals("Valores") ) {
					date = toEpoch(parser.getAttributeValue(null, "Data"));
				} else if( name.equals("Medida") ) {
					String attr = parser.getAttributeValue(null, "ID");
					if( attr.equals("81") ) {
						if( parser.getAttributeValue(null, "Unidades").equals("m/s") )
							val = Float.parseFloat(getContent(parser))*3.6F;
						else
							val = Float.parseFloat(getContent(parser));
						this.station.listSpeedMed.add(date);
				        this.station.listSpeedMed.add(val);
					} else if( attr.equals("86") ) {
						val = MyCharts.convertHumidity(Integer.parseInt(getContent(parser)));
						this.station.listHumidity.add(date);
			        	this.station.listHumidity.add(val);
					} else if( attr.equals("82") || attr.equals("10124") ) {
						val = Integer.parseInt(getContent(parser));
						this.station.listDirection.add(date);
				        this.station.listDirection.add(val);
					} else if( attr.equals("10003") ) {
						if( parser.getAttributeValue(null, "Unidades").equals("m/s") )
							val = Float.parseFloat(getContent(parser))*3.6F;
						else
							val = Float.parseFloat(getContent(parser));
						this.station.listSpeedMax.add(date);
				        this.station.listSpeedMax.add(val);
					}
				}
			}
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}

	public int getRefresh() {
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
	
	private String getContent( XmlPullParser parser ) {
		String val = parser.getAttributeValue(null, "Valor"); 	
		return val.replace(',', this.separator);
	}
	
	private void loadParams() {
		this.urlParams = new HashMap<String, String>();
		
		InputStream inputStream = this.tolomet.getResources().openRawResource(R.raw.meteogalicia);
		InputStreamReader in = new InputStreamReader(inputStream);
		BufferedReader rd = new BufferedReader(in);
		String line;
		String[] fields;
		try {
			while( (line=rd.readLine()) != null ) {
				fields = line.split(":");
				this.urlParams.put(fields[0], line);
			}
			rd.close();
		} catch( Exception e ) {			
		}
    }
	
	public String getInfoUrl(String code) {
		return "http://www2.meteogalicia.es/galego/observacion/estacions/estacionsinfo.asp?Nest="+code;
	}
	
	private Map<String,String> urlParams;
	private char separator;
}
