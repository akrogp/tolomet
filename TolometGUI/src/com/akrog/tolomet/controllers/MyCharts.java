package com.akrog.tolomet.controllers;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import com.akrog.tolomet.Manager;
import com.akrog.tolomet.Meteo;
import com.akrog.tolomet.R;
import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.data.Settings;
import com.akrog.tolomet.view.Graph;
import com.akrog.tolomet.view.Marker;
import com.akrog.tolomet.view.MyPlot;

public class MyCharts implements Controller {
	private static final int LINE_BLUE=Color.rgb(0, 0, 200);
	private static final int POINT_BLUE=Color.rgb(0, 0, 100);
	private static final int LINE_RED=Color.rgb(200, 0, 0);
	private static final int POINT_RED=Color.rgb(100, 0, 0);
	private static final int LINE_GREEN=Color.rgb(0, 200, 0);
	private static final int POINT_GREEN=Color.rgb(0, 100, 0);
	private static final int LINE_GRAY=Color.rgb(200, 200, 200);
	private static final int POINT_GRAY=Color.rgb(100, 100, 100);
	private Tolomet tolomet;
	private Manager model;
	private Settings settings;
	private final Meteo meteo = new Meteo();
	private final Graph airTemperature = new Graph(meteo.getAirTemperature(), -1.0f, "Temp.", LINE_RED, POINT_RED);
	private final Graph airHumidity = new Graph(meteo.getAirHumidity(), -1.0f, "% Hum.", LINE_BLUE, POINT_BLUE);
	private final Graph airHumiditySimple = new Graph(meteo.getAirHumidity(), -1.0f, "% Hum.", LINE_GRAY, POINT_GRAY);
	private final Graph airPressure = new Graph(meteo.getAirPressure(), -1.0f, "Pres.", LINE_GRAY, POINT_GRAY);
	private final Graph windDirection = new Graph(meteo.getWindDirection(), 360.0f, "Dir. Med.", LINE_BLUE, POINT_BLUE);
	private final Graph windSpeedMed = new Graph(meteo.getWindSpeedMed(), -1.0f, "Vel. Med.", LINE_GREEN, POINT_GREEN);
	private final Graph windSpeedMax = new Graph(meteo.getWindSpeedMax(), -1.0f, "Vel. Máx.", LINE_RED, POINT_RED); 
	private MyPlot chartWind, chartAir;
	static final float fontSize = 16;
	private final Marker markerVmin = new Marker(0.0f, null, POINT_GRAY);
	private final Marker markerVmax = new Marker(0.0f, null, POINT_GRAY);
	private final Marker markerSea = new Marker(1013.0f, "1013 mb", POINT_GRAY);
	private final Marker markerLow = new Marker(1000.0f, "1000 mb", POINT_GRAY);
	//private final Marker markerHigh = new Marker(1100.0f, "1100 mb", POINT_GRAY);
	private final Marker markerCloud = new Marker(100.0f, "100% humedad", LINE_BLUE);
	private final Marker markerCloudSimple = new Marker(100.0f, "100% humedad", POINT_GRAY);
	private final Marker markerNorth = new Marker(0, "0º (N)", LINE_BLUE);
	private final Marker markerSouth = new Marker(180, "180º (S)", LINE_BLUE);
	private final Marker markerEast = new Marker(90, "90º (E)", LINE_BLUE);
	private final Marker markerWest = new Marker(270, "270º (W)", LINE_BLUE);
	private boolean simpleMode;

	@Override
	public void initialize(Tolomet tolomet, Bundle bundle) {
		this.tolomet = tolomet;
		model = tolomet.getModel();
		settings = tolomet.getSettings();
		initializeCharts();
	}
	
	@Override
	public void save(Bundle bundle) {	
	}
	
	private void updateMarkers() {
		int pos = settings.getMinMarker(); 
		markerVmin.setPos(pos);
		markerVmin.setLabel(pos+" km/h");
		pos = settings.getMaxMarker(); 
		markerVmax.setPos(pos);
		markerVmax.setLabel(pos+" km/h");
	}
	
	@SuppressLint("SimpleDateFormat")
	private void initializeCharts() {				
    	chartAir = (MyPlot)tolomet.findViewById(R.id.chartAir);
    	chartWind = (MyPlot)tolomet.findViewById(R.id.chartWind);
    	simpleMode = settings.isSimpleMode();
        createCharts();
        chartAir.getXAxis().connect(chartWind.getXAxis());
        chartWind.getXAxis().connect(chartAir.getXAxis());
    }
	
	private void createCharts() {
		chartAir.clear();
		chartWind.clear();
		if( simpleMode )
			createSimpleCharts();
		else
			createCompleteCharts();		
		updateBoundaries();
		updateMarkers();
	}
    
    private void createCompleteCharts() {
        chartAir.setTitle(tolomet.getString(R.string.Air));
        chartAir.getY1Axis().setLabel("Temp. (ºC)");                        
        chartAir.getY1Axis().setSteps(10);
        //chartAir.setTicksPerStepY1(5);
        chartAir.getY2Axis().setLabel("Hum. (%)");
        chartAir.getY2Axis().setRange(10, 110);
        chartAir.getY2Axis().setSteps(10);        
        chartAir.getXAxis().setLabel(tolomet.getString(R.string.Time));        
        chartAir.getXAxis().setSteps(4);
        chartAir.getXAxis().setTicksPerStep(6);       
        
        chartAir.addY1Graph(airTemperature);
        chartAir.addY2Graph(airHumidity);
        chartAir.addY3Graph(airPressure);
        
        chartAir.addY2Marker(markerCloud);
        chartAir.addY3Marker(markerSea);
        chartAir.addY3Marker(markerLow);
        //chartAir.addY3Marker(markerHigh);
              
        chartWind.setTitle(tolomet.getString(R.string.Wind));
        //chartWind.getY1Axis().setWrap(360);
        chartWind.getY1Axis().setRange(0, 360);
        //chartWind.getY1Axis().setRange(180, 180);
        chartWind.getY1Axis().setLabel("Dir. (º)");
        //chartWind.setStepsY1(12);
        //chartWind.setStepsY2(12);
        chartWind.getY1Axis().setSteps(8);
        chartWind.getY2Axis().setSteps(8);
        chartWind.getY2Axis().setLabel("Vel. (km/h)");     
        chartWind.getXAxis().setLabel(tolomet.getString(R.string.Time));        
        chartWind.getXAxis().setSteps(4);
        chartWind.getXAxis().setTicksPerStep(6); 
        
        chartWind.addY1Graph(windDirection);    
        chartWind.addY2Graph(windSpeedMed);
        chartWind.addY2Graph(windSpeedMax);             
        
        //chartWind.addY1Marker(markerSouth);
        //chartWind.addY1Marker(markerNorth);
        //chartWind.addY1Marker(markerEast);
        //chartWind.addY1Marker(markerWest);
        chartWind.addY2Marker(markerVmin);
        chartWind.addY2Marker(markerVmax);
	}

	private void createSimpleCharts() {
		chartAir.setTitle(tolomet.getString(R.string.DirectionHumidity));
        chartAir.getY1Axis().setLabel("Dir. (ºC)");
        chartAir.getY1Axis().setRange(0, 360);
        chartAir.getY1Axis().setSteps(8);
        //chartAir.setTicksPerStepY1(5);
        chartAir.getY2Axis().setLabel("Hum. (%)");
        chartAir.getY2Axis().setRange(30, 110);
        chartAir.getY2Axis().setSteps(8);        
        chartAir.getXAxis().setLabel(tolomet.getString(R.string.Time));        
        chartAir.getXAxis().setSteps(4);
        chartAir.getXAxis().setTicksPerStep(6);       
        
        chartAir.addY1Graph(windDirection);
        chartAir.addY1Marker(markerNorth);
        chartAir.addY1Marker(markerSouth);
        chartAir.addY1Marker(markerEast);
        chartAir.addY1Marker(markerWest);
        chartAir.addY2Graph(airHumiditySimple);        
        chartAir.addY2Marker(markerCloudSimple);
              
        chartWind.setTitle(tolomet.getString(R.string.Speed));
        chartWind.getY1Axis().setLabel("Vel. (km/h)");
        chartWind.getY2Axis().setLabel("Vel. (km/h)");
        //chartWind.setStepsY1(12);
        //chartWind.setStepsY2(12);        
        chartWind.getY1Axis().setSteps(8);
        chartWind.getY2Axis().setSteps(8);
        chartWind.getXAxis().setLabel(tolomet.getString(R.string.Time));        
        chartWind.getXAxis().setSteps(4);
        chartWind.getXAxis().setTicksPerStep(6); 
            
        chartWind.addY1Graph(windSpeedMed);
        chartWind.addY2Graph(windSpeedMax);             

        chartWind.addY1Marker(markerVmin);
        chartWind.addY1Marker(markerVmax);		
	}

	private void updateBoundaries() {
    	updateTimeRange();
        
        if( simpleMode )
        	updateBoundariesSimple();
        else
        	updateBoundariesComplete();
    }
	
	private void updateBoundariesSimple() {
		int speedRange = settings.getSpeedRange(meteo.getWindSpeedMax());
		chartWind.getY1Axis().setRange(0, speedRange);
        chartWind.getY2Axis().setRange(0, speedRange);
        chartWind.getY2Axis().setLimits(0, speedRange);
        chartWind.getY1Axis().setLimits(0, speedRange);
        //chartWind.setStepsY2(speedRange/5);
	}
	
	private void updateBoundariesComplete() {
		int speedRange = settings.getSpeedRange(meteo.getWindSpeedMax());
        chartWind.getY2Axis().setRange(0, speedRange);
        chartWind.getY2Axis().setLimits(0, speedRange);
        //chartWind.setStepsY2(speedRange/5);
        
        int minTemp = settings.getMinTemp(meteo.getAirTemperature());
        int maxTemp = settings.getMaxTemp(meteo.getAirTemperature());
        chartAir.getY1Axis().setRange(minTemp, maxTemp);
        chartAir.getY1Axis().setLimits(minTemp, maxTemp);
        
        // See: http://www.theweatherprediction.com/habyhints2/410/
        chartAir.getY3Axis().setRange(settings.getMinPres(meteo.getAirPressure()), settings.getMaxPres(meteo.getAirPressure()));
	}
    
    private void updateTimeRange() {
    	int minutes = model.getRefresh();
		int hours = minutes * 24 / 60;
    	    	
    	long round = minutes*60*1000;
    	long x2 = System.currentTimeMillis()/round*round;
    	long x1 = x2-hours*60*60*1000;
    	//long x0 = x2-24*60*60*1000;
    	Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.HOUR_OF_DAY, 0);
    	cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.SECOND, 0);
    	cal.set(Calendar.MILLISECOND, 0);
    	long x0 = cal.getTimeInMillis();
    	if( x1 < x0 )
    		x0 = x1;
    	
    	chartAir.getXAxis().setRange(x1,x2);
    	chartAir.getXAxis().setLimits(x0,x2);
        chartWind.getXAxis().setRange(x1,x2);
        chartWind.getXAxis().setLimits(x0,x2);
    }

    @Override
    public void redraw() {    	
    	meteo.clear();
    	if( !model.getCurrentStation().isSpecial() )
    		meteo.merge(model.getCurrentStation().getMeteo());
    	if( settings.isSimpleMode() != simpleMode ) {
    		simpleMode = !simpleMode;
    		createCharts();
    	} else {
    		updateBoundaries();
    		updateMarkers();
    	}
    	chartAir.redraw();
        chartWind.redraw();
    }
	
	public boolean getZoomed() {
        return chartAir.getZoomed() || chartWind.getZoomed();
    }
}
