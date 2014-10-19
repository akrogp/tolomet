package com.akrog.tolomet.view;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

import com.akrog.tolomet.R;
import com.akrog.tolomet.SettingsActivity;
import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.data.StationManager;

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
	private StationManager stations;
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

	public MyCharts( Tolomet tolomet, StationManager data ) {
		this.tolomet = tolomet;
		this.stations = data;
		createCharts();
	}	
	
	private int getSpeedRange() {
		if( this.speedRange == -1 ) {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.tolomet);
			this.speedRange = Integer.parseInt(prefs.getString(SettingsActivity.KEY_SPEED_RANGE, this.tolomet.getString(R.string.pref_rangeDefault)));
		}
		return this.speedRange;
	}
	
	private void updateMarkers() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.tolomet);
		int pos = Integer.parseInt(prefs.getString(SettingsActivity.KEY_MIN_MARKER, this.tolomet.getString(R.string.pref_minMarkerDefault))); 
		markerVmin.setPos(pos);
		markerVmin.setLabel(pos+"");
		pos = Integer.parseInt(prefs.getString(SettingsActivity.KEY_MAX_MARKER, this.tolomet.getString(R.string.pref_maxMarkerDefault))); 
		markerVmax.setPos(pos);
		markerVmax.setLabel(pos+"");
	}
	
	@SuppressLint("SimpleDateFormat")
	private void createCharts() {				
    	this.chartAir = (MyPlot)this.tolomet.findViewById(R.id.chartDirection);
        this.chartAir.setTitle(this.tolomet.getString(R.string.DirectionHumidity));
        this.chartAir.setY1Label("Temp. (grados)");        
        this.chartAir.setY1Range(0, 30);        
        this.chartAir.setStepsY1(6);
        this.chartAir.setY2Label("Hum. (%)");
        this.chartAir.setY2Range(10, 110);
        this.chartAir.setY3Range(813, 1113);
        this.chartAir.setXLabel(this.tolomet.getString(R.string.Time));        
        this.chartAir.setStepsX(4);
        this.chartAir.setTicksPerStepX(6);       
        
        this.chartAir.addY1Graph(new Graph(
            this.stations.current.listTemperature, -1.0f, "Temp.", LINE_RED, POINT_RED));
        this.chartAir.addY2Graph(new Graph(
        	this.stations.current.listHumidity, -1.0f, "% Hum.", LINE_BLUE, POINT_BLUE));
        this.chartAir.addY3Graph(new Graph(
            this.stations.current.listPressure, -1.0f, "Pres.", LINE_GRAY, POINT_GRAY));
        
        this.chartAir.addY2Marker(markerCloud);
        this.chartAir.addY3Marker(markerSea);
        this.chartAir.addY3Marker(markerLow);
        //this.chartAir.addY3Marker(markerHigh);
        
        this.chartWind = (MyPlot)this.tolomet.findViewById(R.id.chartSpeed);      
        this.chartWind.setTitle(this.tolomet.getString(R.string.Speed));
        this.chartWind.setY1Label("Vel. (km/h)");        
        this.chartWind.setY1Range(0, getSpeedRange());
        this.chartWind.setStepsY1(getSpeedRange()/5);
        this.chartWind.setY2Range(0, 360);
        this.chartWind.setY2Label("Dir. (grados)");        
        this.chartWind.setXLabel(this.tolomet.getString(R.string.Time));        
        this.chartWind.setStepsX(4);
        this.chartWind.setTicksPerStepX(6); 
        
        this.chartWind.addY1Graph(new Graph(
        	this.stations.current.listSpeedMed, -1.0f, "Vel. Med.", LINE_GREEN, POINT_GREEN));
        this.chartWind.addY1Graph(new Graph(
        	this.stations.current.listSpeedMax, -1.0f, "Vel. Máx.", LINE_RED, POINT_RED)); 
        this.chartWind.addY2Graph(new Graph(
        	this.stations.current.listDirection, 180.0f, "Dir. Med.", LINE_BLUE, POINT_BLUE));        
        
        updateMarkers();
        this.chartWind.addY1Marker(markerVmin);
        this.chartWind.addY1Marker(markerVmax);
        /*this.chartWind.addY2Marker(markerSouth);
        this.chartWind.addY2Marker(markerNorth);
        this.chartWind.addY2Marker(markerEast);
        this.chartWind.addY2Marker(markerWest);*/
        
        this.chartAir.connectXRanges(this.chartWind);
        this.chartWind.connectXRanges(this.chartAir);
        
        //updateDomainBoundaries();
        setRefresh(15);
    }
    
    private void updateBoundaries() {
    	Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.SECOND, 0);
    	cal.set(Calendar.MILLISECOND, 0);
    	int minute = (cal.get(Calendar.MINUTE)+this.minutes-1)/this.minutes*this.minutes;
    	int hour = cal.get(Calendar.HOUR_OF_DAY);
    	hour += minute/60;
    	minute -= minute/60*60;
    	cal.set(Calendar.MINUTE, minute );
    	cal.set(Calendar.HOUR_OF_DAY, hour );
    	Date date2 = cal.getTime();
        Date date1 = new Date();
        date1.setTime(date2.getTime()-this.hours*60*60*1000);
        cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.set(Calendar.SECOND, 0);
    	cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        Date date0 = cal.getTime();
    	this.chartAir.setXRange(date1.getTime(), date2.getTime());
    	this.chartAir.setXZoomLimits(date0.getTime(), date2.getTime());
        this.chartWind.setXRange(date1.getTime(), date2.getTime());
        this.chartWind.setXZoomLimits(date0.getTime(), date2.getTime());
        this.chartWind.setY1Range(0, getSpeedRange());
        this.chartWind.setY1ZoomLimits(0, getSpeedRange());
    }
    
    public void redraw() {
    	updateBoundaries();
    	updateMarkers();
    	this.chartAir.redraw();
        this.chartWind.redraw();
    }
	
	public void setRefresh( int minutes ) {		
		this.minutes = minutes;
		this.hours = minutes * 24 / 60;
		updateBoundaries();
	}
	
	public boolean getZoomed() {
        return this.chartAir.getZoomed() || this.chartWind.getZoomed();
    }
}
