package com.akrog.tolomet.view;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

import com.akrog.tolomet.Manager;
import com.akrog.tolomet.Meteo;
import com.akrog.tolomet.R;
import com.akrog.tolomet.SettingsActivity;
import com.akrog.tolomet.Tolomet;

public class MyCharts {
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
	private MyPlot chartWind, chartAir;
	static final float fontSize = 16;
	private int minutes = 10;
	private int hours = 4;
	private int speedRange = -1;
	private Marker markerVmin = new Marker(0.0f, null, POINT_GRAY);
	private Marker markerVmax = new Marker(0.0f, null, POINT_GRAY);
	private Marker markerSea = new Marker(1013.0f, "1013 mb", POINT_GRAY);
	private Marker markerLow = new Marker(900.0f, "900 mb", POINT_GRAY);
	//private Marker markerHigh = new Marker(1100.0f, "1100 mb", POINT_GRAY);
	private Marker markerCloud = new Marker(100.0f, "100% humedad", LINE_BLUE);
	/*private Marker markerNorth = new Marker(0, "0º (N)", LINE_BLUE);
	private Marker markerSouth = new Marker(180, "180º (S)", LINE_BLUE);
	private Marker markerEast = new Marker(90, "90º (E)", LINE_BLUE);
	private Marker markerWest = new Marker(270, "270º (W)", LINE_BLUE);*/

	public MyCharts( Tolomet tolomet, Manager model ) {
		this.tolomet = tolomet;
		this.model = model;
		createCharts();
	}	
	
	private int getSpeedRange() {
		if( speedRange == -1 ) {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(tolomet);
			speedRange = Integer.parseInt(prefs.getString(SettingsActivity.KEY_SPEED_RANGE, tolomet.getString(R.string.pref_rangeDefault)));
		}
		return this.speedRange;
	}
	
	private void updateMarkers() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(tolomet);
		int pos = Integer.parseInt(prefs.getString(SettingsActivity.KEY_MIN_MARKER, tolomet.getString(R.string.pref_minMarkerDefault))); 
		markerVmin.setPos(pos);
		markerVmin.setLabel(pos+"");
		pos = Integer.parseInt(prefs.getString(SettingsActivity.KEY_MAX_MARKER, tolomet.getString(R.string.pref_maxMarkerDefault))); 
		markerVmax.setPos(pos);
		markerVmax.setLabel(pos+"");
	}
	
	@SuppressLint("SimpleDateFormat")
	private void createCharts() {				
    	chartAir = (MyPlot)tolomet.findViewById(R.id.chartDirection);
        chartAir.setTitle(tolomet.getString(R.string.DirectionHumidity));
        chartAir.setY1Label("Temp. (grados)");        
        chartAir.setY1Range(0, 30);        
        chartAir.setStepsY1(6);
        chartAir.setY2Label("Hum. (%)");
        chartAir.setY2Range(10, 110);
        chartAir.setY3Range(813, 1113);
        chartAir.setXLabel(tolomet.getString(R.string.Time));        
        chartAir.setStepsX(4);
        chartAir.setTicksPerStepX(6);       
        
        Meteo meteo = model.getCurrentStation().getMeteo(); 
        
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
        
        chartWind = (MyPlot)tolomet.findViewById(R.id.chartSpeed);      
        chartWind.setTitle(tolomet.getString(R.string.Speed));
        chartWind.setY1Label("Vel. (km/h)");        
        chartWind.setY1Range(0, getSpeedRange());
        chartWind.setStepsY1(getSpeedRange()/5);
        chartWind.setY2Range(0, 360);
        chartWind.setY2Label("Dir. (grados)");        
        chartWind.setXLabel(tolomet.getString(R.string.Time));        
        chartWind.setStepsX(4);
        chartWind.setTicksPerStepX(6); 
        
        chartWind.addY1Graph(new Graph(
        	meteo.getWindSpeedMed(), -1.0f, "Vel. Med.", LINE_GREEN, POINT_GREEN));
        chartWind.addY1Graph(new Graph(
        	meteo.getWindSpeedMax(), -1.0f, "Vel. Máx.", LINE_RED, POINT_RED)); 
        chartWind.addY2Graph(new Graph(
        	meteo.getWindDirection(), 180.0f, "Dir. Med.", LINE_BLUE, POINT_BLUE));        
        
        updateMarkers();
        chartWind.addY1Marker(markerVmin);
        chartWind.addY1Marker(markerVmax);
        /*chartWind.addY2Marker(markerSouth);
        chartWind.addY2Marker(markerNorth);
        chartWind.addY2Marker(markerEast);
        chartWind.addY2Marker(markerWest);*/
        
        chartAir.connectXRanges(chartWind);
        chartWind.connectXRanges(chartAir);
        
        //updateDomainBoundaries();
        setRefresh(15);
    }
    
    private void updateBoundaries() {
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
        chartWind.setY1Range(0, getSpeedRange());
        chartWind.setY1ZoomLimits(0, getSpeedRange());
    }
    
    public void redraw() {
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