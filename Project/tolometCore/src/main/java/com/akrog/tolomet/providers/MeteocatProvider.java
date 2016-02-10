package com.akrog.tolomet.providers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import com.akrog.tolomet.Measurement;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;
import com.akrog.tolomet.io.Downloader.FakeBrowser;

public class MeteocatProvider implements WindProvider {
	@Override
	public void refresh(Station station) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		downloader = new Downloader();
		downloader.setBrowser(FakeBrowser.WGET);
		downloader.setUrl("http://www.meteo.cat/observacions/xema/dades");
		downloader.addParam("codi", station.getCode());
		downloader.addParam("dia", String.format("%d-%02d-%02dT00:00Z", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH)));
		updateStation(station,downloader.download("\"tabs-2\""));
	}
	
	@Override
	public void cancel() {
		if( downloader != null )
			downloader.cancel();	
	}

	protected void updateStation(Station station, String data) {
		if( data == null )
			return;				
		int iWind = data.indexOf("renderitzarGraficaVelocitatDireccioVent");
		if( iWind < 0 )
			return;
		int iAir = data.indexOf("renderitzarGraficaTemperaturaHumitat");
		station.clear();
		
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 30);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		long date = cal.getTimeInMillis(); 
				
		updateMeasurement(station.getMeteo().getAirTemperature(), date, getValues(data, iAir, "temperatura"));
		updateMeasurement(station.getMeteo().getAirHumidity(), date, getValues(data, iAir, "humitat"));
		updateMeasurement(station.getMeteo().getWindDirection(), date, getValues(data, iWind, "direccioVent"));
		updateMeasurement(station.getMeteo().getWindSpeedMed(), date, getValues(data, iWind, "velocitatVent"));
	}
	
	private void updateMeasurement( Measurement measurement, long stamp, List<Float> values ) {
		if( values == null )
			return;
		for( Float value : values ) {
			measurement.put(stamp, value);
			stamp += 30*60*1000;
		}
	}
	
	private List<Float> getValues( String data, int off, String label ) {
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

	@Override
	public int getRefresh( String code ) {
		return 30;
	}		
		
	@Override
	public String getInfoUrl(String code) {
		return String.format("http://www.meteo.cat/observacions/xema/dades?codi=%s", code);
	}
	
	private Downloader downloader; 
}
