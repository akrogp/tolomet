package com.akrog.tolomet;

import java.util.ArrayList;
import java.util.List;

public class Meteo {
	public Meteo() {
		measurements.add(windDirection);
		measurements.add(windSpeedMed);
		measurements.add(windSpeedMax);
		measurements.add(airHumidity);
		measurements.add(airTemperature);
		measurements.add(airPressure);
	}
	
	public Measurement getWindDirection() {
		return windDirection;
	}

	public Measurement getWindSpeedMed() {
		return windSpeedMed;
	}

	public Measurement getWindSpeedMax() {
		return windSpeedMax;
	}

	public Measurement getAirTemperature() {
		return airTemperature;
	}

	public Measurement getAirPressure() {
		return airPressure;
	}

	public Measurement getAirHumidity() {
		return airHumidity;
	}
	
	public void clear() {
		for( Measurement measurement : measurements )
			measurement.clear();
	}
	
	public void clear( long fromStamp ) {
		for( Measurement measurement : measurements )
			measurement.clear(fromStamp);
	}
	
	public void merge(Meteo meteo) {
		windDirection.merge(meteo.getWindDirection());
		windSpeedMed.merge(meteo.getWindSpeedMed());
		windSpeedMax.merge(meteo.getWindSpeedMax());
		airTemperature.merge(meteo.getAirTemperature());
		airPressure.merge(meteo.getAirPressure());
		airHumidity.merge(meteo.getAirHumidity());
	}
	
	public boolean isEmpty() {
		for( Measurement measurement : measurements )
			if( !measurement.isEmpty() )
				return false;
		return true;
	}
	
	public Long getStamp() {
		Long stamp = null;
		Long tmp;
		for( Measurement measurement : measurements ) {
			tmp = measurement.getStamp();
			if( tmp == null )
				continue;
			if( stamp == null ) {
				stamp = tmp;
				continue;
			}
			if( tmp > stamp )
				stamp = tmp;
		}
		return stamp;
	}

	private final Measurement windDirection = new Measurement();
	private final Measurement windSpeedMed = new Measurement();
	private final Measurement windSpeedMax = new Measurement();
	private final Measurement airTemperature = new Measurement();
	private final Measurement airPressure = new Measurement();
	private final Measurement airHumidity = new Measurement();
	private final List<Measurement> measurements = new ArrayList<Measurement>();
}