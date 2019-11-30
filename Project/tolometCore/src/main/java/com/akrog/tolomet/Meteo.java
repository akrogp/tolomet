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

	public Long getBegin() {
		Long stamp = null;
		Long tmp;
		for( Measurement measurement : measurements ) {
			tmp = measurement.getBegin();
			if( tmp == null )
				continue;
			if( stamp == null ) {
				stamp = tmp;
				continue;
			}
			if( tmp < stamp )
				stamp = tmp;
		}
		return stamp;
	}

	public Long getStamp(Long stamp) {
		Long result = getStamp();
		if( result == null || stamp == null )
			return result;
		long diff = Math.abs(stamp-result);
		long tmpDiff;
		Long tmp;
		for( Measurement measurement : measurements ) {
			tmp = measurement.getStamp(stamp);
			if( tmp == null )
				continue;
			tmpDiff = Math.abs(stamp-tmp);
			if( tmpDiff < diff ) {
				diff = tmpDiff;
				result = tmp;
			}
		}
		return result;
	}
	
	public Integer getStep() {
		for( Measurement measurement : measurements ) {
			Integer step = measurement.getStep();
			if( step != null )
				return step;
		}
		return null;
	}

	private final Measurement windDirection = new Measurement(0F,360F);
	private final Measurement windSpeedMed = new Measurement(0F,1000F);
	private final Measurement windSpeedMax = new Measurement(0F,1000F);
	private final Measurement airTemperature = new Measurement(-100F,100F);
	private final Measurement airPressure = new Measurement(0F,10000F);
	private final Measurement airHumidity = new Measurement(0F,110F);
	private final List<Measurement> measurements = new ArrayList<Measurement>();
}