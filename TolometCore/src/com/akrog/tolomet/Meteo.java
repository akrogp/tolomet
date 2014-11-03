package com.akrog.tolomet;

public class Meteo {
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
		windDirection.clear();
		windSpeedMed.clear();
		windSpeedMax.clear();
		airTemperature.clear();
		airPressure.clear();
		airHumidity.clear();
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
		return windDirection.isEmpty();
	}
	
	public Long getStamp() {
		return windDirection.getStamp();
	}

	private final Measurement windDirection = new Measurement();
	private final Measurement windSpeedMed = new Measurement();
	private final Measurement windSpeedMax = new Measurement();
	private final Measurement airTemperature = new Measurement();
	private final Measurement airPressure = new Measurement();
	private final Measurement airHumidity = new Measurement();
}