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
	private Tolomet tolomet;
	private StationManager stations;
	private MyPlot chartSpeed, chartDirection;
	static final float fontSize = 16;
	private int minutes = 10;
	private int hours = 4;
	private int speedRange = -1;

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
	
	private int getMarker1() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.tolomet);
		return Integer.parseInt(prefs.getString(SettingsActivity.KEY_MIN_MARKER, this.tolomet.getString(R.string.pref_minMarkerDefault)));
	}
	
	private int getMarker2() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.tolomet);
		return Integer.parseInt(prefs.getString(SettingsActivity.KEY_MAX_MARKER, this.tolomet.getString(R.string.pref_maxMarkerDefault)));
	}
	
	@SuppressLint("SimpleDateFormat")
	private void createCharts() {				
    	this.chartDirection = (MyPlot)this.tolomet.findViewById(R.id.chartDirection);
        this.chartDirection.setTitle(this.tolomet.getString(R.string.DirectionHumidity));
        this.chartDirection.setY1Label(this.tolomet.getString(R.string.Degrees));        
        this.chartDirection.setY1Range(0, 360);
        this.chartDirection.setStepsY1(8);
        this.chartDirection.setZoomVertically(false);
        //this.chartDirection.setTicksPerStepY1(3);
        //this.chartDirection.setDomainLabel(this.tolomet.getString(R.string.Time));        
        this.chartDirection.setStepsX(4);
        this.chartDirection.setTicksPerStepX(6);       
        
        this.chartDirection.addGraph(
        	new Graph(this.stations.current.listDirection, "Dir. Med.", Color.rgb(0, 0, 200), Color.rgb(0, 0, 100)));
        this.chartDirection.addGraph(
            new Graph(this.stations.current.listHumidity, "% Hum.", Color.rgb(200, 200, 200), Color.rgb(100, 100, 100)));        
        this.chartDirection.addY1Marker(convertHumidity(0),0+"%");
        this.chartDirection.addY1Marker(convertHumidity(50),50+"%");
        this.chartDirection.addY1Marker(convertHumidity(100),100+"%");
        this.chartDirection.setZoomVertically(false);
        
        this.chartSpeed = (MyPlot)this.tolomet.findViewById(R.id.chartSpeed);      
        this.chartSpeed.setTitle(this.tolomet.getString(R.string.Speed));
        this.chartSpeed.setY1Label("km/h");        
        this.chartSpeed.setY1Range(0, getSpeedRange());
        this.chartSpeed.setStepsY1(getSpeedRange()/5);
        //this.chartSpeed.setTicksPerStepY(2);
        //this.chartSpeed.setDomainLabel(this.tolomet.getString(R.string.Time));        
        this.chartSpeed.setStepsX(4);
        this.chartSpeed.setTicksPerStepX(6); 
        
        this.chartSpeed.addGraph(new Graph(
        	this.stations.current.listSpeedMed, "Vel. Med.", Color.rgb(0, 200, 0), Color.rgb(0, 100, 0)));
        this.chartSpeed.addGraph(new Graph(
        	this.stations.current.listSpeedMax, "Vel. Máx.", Color.rgb(200, 0, 0), Color.rgb(100, 0, 0))); 
        
        this.chartSpeed.addY1Marker(getMarker1(),null);
        this.chartSpeed.addY1Marker(getMarker2(),null);
        
        //this.chartDirection.connectDomains(this.chartSpeed);
        //this.chartSpeed.connectDomains(this.chartDirection);
        
        //updateDomainBoundaries();
        setRefresh(15);
    }	
	
	public static Float convertHumidity( int hum ) {
    	return 45.0F+hum*2.7F;
    	//return hum*3.6F;
    	//return hum*3.15F;
    }
    
    public static int convertHumidity( float hum ) {
    	return (int)((hum-45.0)/2.7+0.05);
    	//return (int)(hum/3.6+0.05);
    	//return (int)(hum/3.15+0.05);
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
    	this.chartDirection.setXRange(date1.getTime(), date2.getTime());
    	this.chartDirection.setXZoomLimits(date0.getTime(), date2.getTime());
        this.chartSpeed.setXRange(date1.getTime(), date2.getTime());
        this.chartSpeed.setXZoomLimits(date0.getTime(), date2.getTime());
        this.chartSpeed.setY1Range(0, getSpeedRange());
        this.chartSpeed.setY1ZoomLimits(0, getSpeedRange());
    }
    
    public void redraw() {
    	updateBoundaries();
    	/*this.markerMin.setValue(getMarker1());
    	this.markerMax.setValue(getMarker2());*/
    	this.chartDirection.redraw();
        this.chartSpeed.redraw();
    }
	
	public void setRefresh( int minutes ) {		
		this.minutes = minutes;
		this.hours = minutes * 24 / 60;
		updateBoundaries();
	}
	
	public boolean getZoomed() {
        return this.chartDirection.getZoomed() || this.chartSpeed.getZoomed();
    }
}
