package com.akrog.tolomet.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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
	private MyPlot chartWind, chartAir;
	static final float fontSize = 16;
	private int minutes = 10;
	private int hours = 4;
	private Marker markerVmin = new Marker(0.0f, null, POINT_GRAY);
	private Marker markerVmax = new Marker(0.0f, null, POINT_GRAY);
	private Marker markerSea = new Marker(1013.0f, "1013 mb", POINT_GRAY);
	private Marker markerLow = new Marker(1000.0f, "1000 mb", POINT_GRAY);
	//private Marker markerHigh = new Marker(1100.0f, "1100 mb", POINT_GRAY);
	private Marker markerCloud = new Marker(100.0f, "100% humedad", LINE_BLUE);
	//private Marker markerNorth = new Marker(0, "0º (N)", LINE_BLUE);
	private Marker markerSouth = new Marker(180, "180º (S)", LINE_BLUE);
	//private Marker markerEast = new Marker(90, "90º (E)", LINE_BLUE);
	//private Marker markerWest = new Marker(270, "270º (W)", LINE_BLUE);

	@Override
	public void initialize(Tolomet tolomet, Bundle bundle) {
		this.tolomet = tolomet;
		model = tolomet.getModel();
		settings = tolomet.getSettings();
		createCharts();
		//PreferenceManager.getDefaultSharedPreferences(tolomet.getApplicationContext()).registerOnSharedPreferenceChangeListener(this);
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
	private void createCharts() {				
    	chartAir = (MyPlot)tolomet.findViewById(R.id.chartAir);
        chartAir.setTitle(tolomet.getString(R.string.Air));
        chartAir.setY1Label("Temp. (grados)");                        
        chartAir.setStepsY1(10);
        //chartAir.setTicksPerStepY1(5);
        chartAir.setY2Label("Hum. (%)");
        chartAir.setY2Range(10, 110);
        chartAir.setStepsY2(10);        
        chartAir.setXLabel(tolomet.getString(R.string.Time));        
        chartAir.setStepsX(4);
        chartAir.setTicksPerStepX(6);       
        
        chartAir.addY1Graph(new Graph(
            meteo.getAirTemperature(), -1.0f, "Temp.", LINE_RED, POINT_RED));
        chartAir.addY2Graph(new Graph(
        	meteo.getAirHumidity(), -1.0f, "% Hum.", LINE_BLUE, POINT_BLUE));
        chartAir.addY3Graph(new Graph(
            meteo.getAirPressure(), -1.0f, "Pres.", LINE_GRAY, POINT_GRAY));
        
        chartAir.addY2Marker(markerCloud);
        chartAir.addY3Marker(markerSea);
        chartAir.addY3Marker(markerLow);
        //chartAir.addY3Marker(markerHigh);
        
        chartWind = (MyPlot)tolomet.findViewById(R.id.chartWind);      
        chartWind.setTitle(tolomet.getString(R.string.Wind));
        chartWind.setY1Range(0, 360);
        chartWind.setY1Label("Dir. (grados)");
        chartWind.setStepsY1(12);
        chartWind.setStepsY2(12);
        chartWind.setY2Label("Vel. (km/h)");     
        chartWind.setXLabel(tolomet.getString(R.string.Time));        
        chartWind.setStepsX(4);
        chartWind.setTicksPerStepX(6); 
        
        chartWind.addY1Graph(new Graph(
            	meteo.getWindDirection(), 360.0f, "Dir. Med.", LINE_BLUE, POINT_BLUE));    
        chartWind.addY2Graph(new Graph(
        	meteo.getWindSpeedMed(), -1.0f, "Vel. Med.", LINE_GREEN, POINT_GREEN));
        chartWind.addY2Graph(new Graph(
        	meteo.getWindSpeedMax(), -1.0f, "Vel. Máx.", LINE_RED, POINT_RED));             
        
        updateMarkers();
        chartWind.addY1Marker(markerSouth);
        //chartWind.addY1Marker(markerNorth);
        //chartWind.addY1Marker(markerEast);
        //chartWind.addY1Marker(markerWest);
        chartWind.addY2Marker(markerVmin);
        chartWind.addY2Marker(markerVmax);        
        
        chartAir.connectXRanges(chartWind);
        chartWind.connectXRanges(chartAir);
        
        setRefresh(15);
        updateBoundaries();
    }
    
    private void updateBoundaries() {
    	updateTimeRange();
        
        int speedRange = settings.getSpeedRange(meteo.getWindSpeedMax());
        chartWind.setY2Range(0, speedRange);
        chartWind.setY2ZoomLimits(0, speedRange);
        //chartWind.setStepsY2(speedRange/5);
        
        int minTemp = settings.getMinTemp(meteo.getAirTemperature());
        int maxTemp = settings.getMaxTemp(meteo.getAirTemperature());
        chartAir.setY1Range(minTemp, maxTemp);
        chartAir.setY1ZoomLimits(minTemp, maxTemp);
        
        // See: http://www.theweatherprediction.com/habyhints2/410/
        chartAir.setY3Range(settings.getMinPres(meteo.getAirPressure()), settings.getMaxPres(meteo.getAirPressure()));
    }
    
    private void updateTimeRange() {
    	Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.SECOND, 0);
    	cal.set(Calendar.MILLISECOND, 0);
    	int minute = (cal.get(Calendar.MINUTE)+minutes-1)/minutes*minutes;
    	int hour = cal.get(Calendar.HOUR_OF_DAY);
    	hour += minute/60;
    	minute -= minute/60*60;
    	cal.set(Calendar.MINUTE, minute );
    	cal.set(Calendar.HOUR_OF_DAY, hour );
    	Date date2 = cal.getTime();
        Date date1 = new Date();
        date1.setTime(date2.getTime()-hours*60*60*1000);
        cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.set(Calendar.SECOND, 0);
    	cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        Date date0 = cal.getTime();
    	chartAir.setXRange(date1.getTime(), date2.getTime());
    	chartAir.setXZoomLimits(date0.getTime(), date2.getTime());
        chartWind.setXRange(date1.getTime(), date2.getTime());
        chartWind.setXZoomLimits(date0.getTime(), date2.getTime());
    }

    @Override
    public void redraw() {    	
    	meteo.clear();
    	if( !model.getCurrentStation().isSpecial() )
    		meteo.merge(model.getCurrentStation().getMeteo());
    	updateBoundaries();
    	updateMarkers();
    	chartAir.redraw();
        chartWind.redraw();
    }
	
	public void setRefresh( int minutes ) {		
		this.minutes = minutes;
		hours = minutes * 24 / 60;
		updateBoundaries();
	}
	
	public boolean getZoomed() {
        return chartAir.getZoomed() || chartWind.getZoomed();
    }
}
