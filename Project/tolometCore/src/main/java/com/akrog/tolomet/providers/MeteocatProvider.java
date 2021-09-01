package com.akrog.tolomet.providers;

import com.akrog.tolomet.Measurement;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;
import com.akrog.tolomet.io.Downloader.FakeBrowser;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MeteocatProvider implements WindProvider {
	@Override
	public void refresh(Station station) {
		travel(station, System.currentTimeMillis());
	}

	@Override
	public boolean travel(Station station, long date) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		cal.setTimeInMillis(date);
		downloader = new Downloader();
		downloader.setBrowser(FakeBrowser.WGET);
		downloader.setUrl("https://www.meteo.cat/observacions/xema/dades");
		downloader.addParam("codi", station.getCode());
		downloader.addParam("dia", String.format("%d-%02d-%02dT00:00Z", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH)));
		updateStationV2(station, cal, downloader.download("taronja"));
		if( station.isEmpty() )
			updateStationV1(station, cal, downloader.download("\"tabs-2\""));
		return true;
	}

	@Override
	public void cancel() {
		if( downloader != null )
			downloader.cancel();	
	}

	protected void updateStationV1(Station station, Calendar cal, String data) {
		if( data == null )
			return;				
		int iWind = data.indexOf("renderitzarGraficaVelocitatDireccioVent");
		int iAir = data.indexOf("renderitzarGraficaTemperaturaHumitat");
		if( iWind < 0 && iAir < 0 )
			return;

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 30);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		long date = cal.getTimeInMillis(); 
				
		updateMeasurementV1(station.getMeteo().getAirTemperature(), date, getValuesV1(data, iAir, "temperatura"));
		updateMeasurementV1(station.getMeteo().getAirHumidity(), date, getValuesV1(data, iAir, "humitat"));
		updateMeasurementV1(station.getMeteo().getWindDirection(), date, getValuesV1(data, iWind, "direccioVent"));
		updateMeasurementV1(station.getMeteo().getWindSpeedMed(), date, getValuesV1(data, iWind, "velocitatVent"));
	}
	
	private void updateMeasurementV1(Measurement measurement, long stamp, List<Float> values) {
		if( values == null )
			return;
		for( Float value : values ) {
			measurement.put(stamp, value);
			stamp += 30*60*1000;
		}
	}
	
	private List<Float> getValuesV1(String data, int off, String label) {
		if( off < 0 )
			return null;
		String[] fields = data.substring(off).split("\\[");
		int i;
		for( i = 0; i < fields.length; i++ )
			if( fields[i].contains(label) )
				break;
		i++;
		if( i >= fields.length )
			return null;
		List<Float> list = new ArrayList<Float>();
		fields = fields[i].replaceAll("\\].*", "").split(", *");
		for( i = 0; i < fields.length; i++ ) {
			try {
				float num = Float.parseFloat(fields[i]);
				list.add(num);
			} catch( Exception e ) {
				break;
			}
		}
		return list;
	}

	protected void updateStationV2(Station station, Calendar cal, String data) {
		if( data == null )
			return;

		String table = data.substring(data.indexOf("tblperiode"));
		String[] rows = table.split("</tr>");

		int iStamp = findCol(rows[0], "TU");
		if( iStamp != 0 )
			return;

		int iTemp = findCol(rows[0], "TM");
		int iHum = findCol(rows[0], "HRM");
		int iMed = findCol(rows[0], "VVM");
		int iMax = findCol(rows[0], "VVX");
		int iDir = findCol(rows[0], "DVM");
		int iPres = findCol(rows[0], "PM");

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		NumberFormat format = DecimalFormat.getInstance(Locale.ENGLISH);

		for( int i = 1; i < rows.length-1; i++ ) {
			String[] cols = getCols(rows[i]);
			getStamp(cols[iStamp], cal);
			addMeasurementV2(station.getMeteo().getAirTemperature(), cal, cols, iTemp, format);
			addMeasurementV2(station.getMeteo().getAirHumidity(), cal, cols, iHum, format);
			addMeasurementV2(station.getMeteo().getAirPressure(), cal, cols, iPres, format);
			addMeasurementV2(station.getMeteo().getWindSpeedMed(), cal, cols, iMed, format);
			addMeasurementV2(station.getMeteo().getWindSpeedMax(), cal, cols, iMax, format);
			addMeasurementV2(station.getMeteo().getWindDirection(), cal, cols, iDir, format);
		}
	}

	int findCol(String header, String name) {
		int off = header.indexOf(name);
		if( off < 0 )
			return -1;
		return header.substring(0,off).split("</th>").length-1;
	}

	String[] getCols(String row) {
		String[] fields = row.split("</");
		String[] cols = new String[fields.length-1];
		for( int i = 0; i < cols.length; i++ )
			cols[i] = fields[i].substring(fields[i].lastIndexOf('>')+1).trim();
		return cols;
	}

	void getStamp(String str, Calendar cal) {
		String[] fields = str.split(" - ");
		fields = fields[1].split(":");
		cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(fields[0]));
		cal.set(Calendar.MINUTE,Integer.parseInt(fields[1]));
	}

	void addMeasurementV2(Measurement measurement, Calendar stamp, String[] cols, int index, NumberFormat format) {
		if( index < 0 || index > cols.length )
			return;
		try {
			Number value = format.parse(cols[index]);
			measurement.put(stamp.getTimeInMillis(), value.floatValue());
		} catch (ParseException e) {
		}
	}

	@Override
	public int getRefresh( String code ) {
		return 30;
	}		
		
	@Override
	public String getInfoUrl(String code) {
		return String.format("http://www.meteo.cat/observacions/xema/dades?codi=%s", code);
	}

	@Override
	public String getUserUrl(String code) {
		return getInfoUrl(code);
	}

	@Override
	public List<Station> downloadStations() {
		List<Station> stations = new ArrayList<>();
		Downloader dw = new Downloader();
		dw.setUrl("https://www.meteo.cat/observacions/llistat-xema");
		String data = dw.download();
		int tr1 = -1, tr2;
		while( (tr1 = data.indexOf("<tr>", tr1+1)) >= 0 && (tr2 = data.indexOf("</tr>", tr1+1)) >= 0 ) {
			int td1 = tr1;
			Station station = new Station(null, -1);
			int i = 0;
			while( station != null && (td1 = data.indexOf("<td", td1+1)) >= 0 && td1 < tr2 ) {
				td1 = data.indexOf("\">", td1+1);
				int td2 = data.indexOf("</td>", td1+1);
				String cell = data.substring(td1 + 2, td2);
				switch( i ) {
					case 2:	// Name and code
						int a1 = cell.indexOf(">");
						int c1 = cell.indexOf(" [");
						int c2 = cell.indexOf("]");
						if( a1 < 0 || c1 < 0 || c2 < 0 ) {
							station = null;
							break;
						}
						station.setName(cell.substring(a1+1, c1));
						station.setCode(cell.substring(c1+2, c2));
						break;
					case 3:	// Latitude
						station.setLatitude(Double.parseDouble(cell.replace(',', '.')));
						break;
					case 4:	// Longitude
						station.setLongitude(Double.parseDouble(cell.replace(',', '.')));
						break;
					case 8:	// Status
						if( !cell.equalsIgnoreCase("Operativa") )
							station = null;
						break;
				}
				i++;
			}
			if( station == null || station.getName() == null )
				continue;
			station.setCountry("ES");
			station.setRegion(173);
			station.setProviderType(WindProviderType.Meteocat);
			stations.add(station);
		}
		return stations;
	}

	private Downloader downloader; 
}
