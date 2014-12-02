package com.akrog.tolomet.data;

import java.util.List;

import android.os.Bundle;

import com.akrog.tolomet.Measurement;
import com.akrog.tolomet.Station;

public class Bundler {
	public static void saveStations( List<Station> list, Bundle bundle ) {
		for( Station station : list ) {
			if( station.isEmpty() )
				continue;
			saveMeasurement(bundle, station.getCode(), "dir", station.getMeteo().getWindDirection());
			saveMeasurement(bundle, station.getCode(), "hum", station.getMeteo().getAirHumidity());
			saveMeasurement(bundle, station.getCode(), "med", station.getMeteo().getWindSpeedMed());
			saveMeasurement(bundle, station.getCode(), "max", station.getMeteo().getWindSpeedMax());
			saveMeasurement(bundle, station.getCode(), "temp", station.getMeteo().getAirTemperature());
			saveMeasurement(bundle, station.getCode(), "pres", station.getMeteo().getAirPressure());
		}
	}
	
	public static void loadStations( List<Station> list, Bundle bundle ) {
		for( Station station : list ) {
			loadMeasurement(bundle, station.getCode(), "dir", station.getMeteo().getWindDirection());
			loadMeasurement(bundle, station.getCode(), "hum", station.getMeteo().getAirHumidity());
			loadMeasurement(bundle, station.getCode(), "med", station.getMeteo().getWindSpeedMed());
			loadMeasurement(bundle, station.getCode(), "max", station.getMeteo().getWindSpeedMax());
			loadMeasurement(bundle, station.getCode(), "temp", station.getMeteo().getAirTemperature());
			loadMeasurement(bundle, station.getCode(), "pres", station.getMeteo().getAirPressure());
		}
	}
	
	private static boolean saveMeasurement(Bundle bundle, String code, String name, Measurement measurement) {
		if( measurement.isEmpty() )
			return false;
		
		long[] stamps = new long[measurement.size()];
		double[] values = new double[measurement.size()];
		
		int i = 0;
		for( Long stamp : measurement.getTimes() )
			stamps[i++] = stamp;
		i = 0;
		for( Number value : measurement.getValues() )
			values[i++] = value.doubleValue();
		
		bundle.putLongArray(String.format("%s-%sx", code, name), stamps);
		bundle.putDoubleArray(String.format("%s-%sy", code, name), values);
		
		return true;
	}
	
	private static boolean loadMeasurement(Bundle bundle, String code, String name, Measurement measurement) {
		long[] stamps = bundle.getLongArray(String.format("%s-%sx", code, name));
		double[] values = bundle.getDoubleArray(String.format("%s-%sy", code, name));
		if( stamps == null || values == null )
			return false;
		for(int i = 0; i < stamps.length; i++)
			measurement.put(stamps[i], values[i]);
		return true;
	}
}
