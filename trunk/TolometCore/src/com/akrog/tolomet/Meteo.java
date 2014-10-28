package com.akrog.tolomet;

public class Meteo {
	public Data getWindDirection() {
		return windDirection;
	}

	public Data getWindSpeedMed() {
		return windSpeedMed;
	}

	public Data getWindSpeedMax() {
		return windSpeedMax;
	}

	public Data getAirTemperature() {
		return airTemperature;
	}

	public Data getAirPressure() {
		return airPressure;
	}

	public Data getAirHumidity() {
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

	private final Data windDirection = new Data();
	private final Data windSpeedMed = new Data();
	private final Data windSpeedMax = new Data();
	private final Data airTemperature = new Data();
	private final Data airPressure = new Data();
	private final Data airHumidity = new Data();
}