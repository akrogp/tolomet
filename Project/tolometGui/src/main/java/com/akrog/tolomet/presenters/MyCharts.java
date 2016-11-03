package com.akrog.tolomet.presenters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.akrog.tolomet.BaseActivity;
import com.akrog.tolomet.Meteo;
import com.akrog.tolomet.Model;
import com.akrog.tolomet.R;
import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.data.AppSettings;
import com.akrog.tolomet.view.Axis;
import com.akrog.tolomet.view.Graph;
import com.akrog.tolomet.view.Marker;
import com.akrog.tolomet.view.MyPlot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyCharts implements Presenter, MyPlot.BoundaryListener {
	private static final int LINE_BLUE = Color.rgb(0, 0, 200);
	private static final int POINT_BLUE = Color.rgb(0, 0, 100);
	private static final int LINE_RED = Color.rgb(200, 0, 0);
	private static final int POINT_RED = Color.rgb(100, 0, 0);
	private static final int LINE_GREEN = Color.rgb(0, 200, 0);
	private static final int POINT_GREEN = Color.rgb(0, 100, 0);
	private static final int LINE_GRAY = Color.rgb(200, 200, 200);
	private static final int POINT_GRAY = Color.rgb(100, 100, 100);
    private static final DateFormat df = new SimpleDateFormat("EEE (dd/MMM)");
	private BaseActivity activity;
	private final Model model = Model.getInstance();
	private AppSettings settings;
	private final Meteo meteo = new Meteo();
	private final Graph airTemperature = new Graph(meteo.getAirTemperature(), -1.0f, "Temp.", LINE_RED, POINT_RED);
	private final Graph airHumidity = new Graph(meteo.getAirHumidity(), -1.0f, "% Hum.", LINE_BLUE, POINT_BLUE);
	private final Graph airHumiditySimple = new Graph(meteo.getAirHumidity(), -1.0f, "% Hum.", LINE_GRAY, POINT_GRAY);
	private final Graph airPressure = new Graph(meteo.getAirPressure(), -1.0f, "Pres.", LINE_GRAY, POINT_GRAY);
	private final Graph windDirection = new Graph(meteo.getWindDirection(), 360.0f, "Dir. Med.", LINE_BLUE, POINT_BLUE);
	private Graph windSpeedMed, windSpeedMax;
	private MyPlot chartWind, chartAir;
	static final float fontSize = 16;
	private final Marker markerVmin = new Marker(0.0f, null, POINT_GRAY);
	private final Marker markerVmax = new Marker(0.0f, null, POINT_GRAY);
	private final Marker markerSea = new Marker(1013.0f, "1013 mb", POINT_GRAY);
	private final Marker markerLow = new Marker(1000.0f, "1000 mb", POINT_GRAY);
	//private final Marker markerHigh = new Marker(1030.0f, "1030 mb", POINT_GRAY);
	private final Marker markerMountain = new Marker(900.0f, "900 mb", POINT_GRAY);
	private Marker markerCloud, markerCloudSimple;
	private Marker markerNorth, markerSouth, markerEast, markerWest;
	private boolean simpleMode;
	private final Axis.ChangeListener axisListener;
    private Long pastDate;
    private AsyncTask<Void,Void,Void> downloader;

	public MyCharts() {
		this(null);
	}

	public MyCharts(Axis.ChangeListener axisListener) {
		this.axisListener = axisListener;
	}

	@Override
	public void initialize(BaseActivity activity, Bundle bundle) {
		this.activity = activity;
		settings = activity.getSettings();
		windSpeedMed = new Graph(meteo.getWindSpeedMed(), -1.0f, activity.getString(R.string.chart_speedMed), LINE_GREEN, POINT_GREEN);
		windSpeedMax = new Graph(meteo.getWindSpeedMax(), -1.0f, activity.getString(R.string.chart_speedMax), LINE_RED, POINT_RED);
		markerCloud = new Marker(100.0f, activity.getString(R.string.chart_covered), LINE_BLUE);
		markerCloudSimple = new Marker(100.0f, activity.getString(R.string.chart_covered), POINT_GRAY);
		markerNorth = new Marker(0, activity.getString(R.string.markerNorth), LINE_BLUE);
		markerSouth = new Marker(180, activity.getString(R.string.markerSouth), LINE_BLUE);
		markerEast = new Marker(90, activity.getString(R.string.markerEast), LINE_BLUE);
		markerWest = new Marker(270, activity.getString(R.string.markerWest), LINE_BLUE);
		initializeCharts();
	}
	
	@Override
	public void save(Bundle bundle) {	
	}

	@Override
	public void setEnabled(boolean enabled) {
		chartWind.setEnabled(enabled);
		chartAir.setEnabled(enabled);
	}

	private void updateMarkers() {
		int pos = settings.getMinMarker(); 
		markerVmin.setPos(pos);
		markerVmin.setLabel(pos+" km/h");
		pos = settings.getMaxMarker(); 
		markerVmax.setPos(pos);
		markerVmax.setLabel(pos + " km/h");
	}
	
	@SuppressLint("SimpleDateFormat")
	private void initializeCharts() {				
    	chartAir = (MyPlot) activity.findViewById(R.id.chartAir);
    	chartWind = (MyPlot) activity.findViewById(R.id.chartWind);
    	simpleMode = settings.isSimpleMode();
		if( axisListener != null )
			chartAir.getXAxis().addMaxListener(axisListener);
        createCharts();
        chartAir.getXAxis().connect(chartWind.getXAxis());
        chartWind.getXAxis().connect(chartAir.getXAxis());
        chartAir.addBoundaryListener(this);
        chartWind.addBoundaryListener(this);
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
        chartAir.setTitle(activity.getString(R.string.Air));
        chartAir.getY1Axis().setLabel("Temp. (ºC)");
		chartAir.getY1Axis().setSteps(10);
        //chartAir.setTicksPerStepY1(5);
        chartAir.getY2Axis().setLabel("Hum. (%)");
        chartAir.getY2Axis().setRange(10, 110);
        chartAir.getY2Axis().setSteps(10);        
        chartAir.getXAxis().setLabel(activity.getString(R.string.Time));
        chartAir.getXAxis().setSteps(4);
		chartAir.getXAxis().setTicksPerStep(6);

		chartAir.addY1Graph(airTemperature);
        chartAir.addY2Graph(airHumidity);
        chartAir.addY3Graph(airPressure);

		chartAir.addY2Marker(markerCloud);
        chartAir.addY3Marker(markerSea);
        chartAir.addY3Marker(markerLow);
        //chartAir.addY3Marker(markerHigh);
        chartAir.addY3Marker(markerMountain);

		chartWind.setTitle(activity.getString(R.string.Wind));
        //chartWind.getY1Axis().setWrap(360);
		chartWind.getY1Axis().setRange(0, 360);
        //chartWind.getY1Axis().setRange(180, 180);
		chartWind.getY1Axis().setLabel("Dir. (º)");
        //chartWind.setStepsY1(12);
        //chartWind.setStepsY2(12);
        chartWind.getY1Axis().setSteps(8);
        chartWind.getY2Axis().setSteps(8);
        chartWind.getY2Axis().setLabel(activity.getString(R.string.chart_speed));
        chartWind.getXAxis().setLabel(activity.getString(R.string.Time));
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
		chartAir.setTitle(activity.getString(R.string.DirectionHumidity));
        chartAir.getY1Axis().setLabel("Dir. (º)");
        chartAir.getY1Axis().setRange(0, 360);
        chartAir.getY1Axis().setSteps(8);
        //chartAir.setTicksPerStepY1(5);
        chartAir.getY2Axis().setLabel("Hum. (%)");
        chartAir.getY2Axis().setRange(30, 110);
        chartAir.getY2Axis().setSteps(8);        
        chartAir.getXAxis().setLabel(activity.getString(R.string.Time));
        chartAir.getXAxis().setSteps(4);
        chartAir.getXAxis().setTicksPerStep(6);       
        
        chartAir.addY1Graph(windDirection);
        chartAir.addY1Marker(markerNorth);
        chartAir.addY1Marker(markerSouth);
        chartAir.addY1Marker(markerEast);
        chartAir.addY1Marker(markerWest);
        chartAir.addY2Graph(airHumiditySimple);        
        chartAir.addY2Marker(markerCloudSimple);
              
        chartWind.setTitle(activity.getString(R.string.Speed));
        chartWind.getY1Axis().setLabel(activity.getString(R.string.chart_speed));
        chartWind.getY2Axis().setLabel(activity.getString(R.string.chart_speed));
        //chartWind.setStepsY1(12);
        //chartWind.setStepsY2(12);        
        chartWind.getY1Axis().setSteps(8);
        chartWind.getY2Axis().setSteps(8);
        chartWind.getXAxis().setLabel(activity.getString(R.string.Time));
        chartWind.getXAxis().setSteps(4);
        chartWind.getXAxis().setTicksPerStep(6); 
            
        chartWind.addY1Graph(windSpeedMed);
        chartWind.addY2Graph(windSpeedMax);             

        chartWind.addY1Marker(markerVmin);
        chartWind.addY1Marker(markerVmax);		
	}

	private void updateBoundaries() {
    	updateTimeRange(true);
        
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
    
    private void updateTimeRange(boolean tail) {
    	int minutes = model.getRefresh();
		if( minutes < 5 )
			minutes = 5;
		int hours = minutes * 24 / 60;
		if( hours > 24 )
			hours = 24;
    	    	
    	long round = minutes*60*1000;
    	long x3 = System.currentTimeMillis()/round*round;

    	Calendar cal = Calendar.getInstance();
        if( pastDate != null )
            cal.setTimeInMillis(pastDate);
    	cal.set(Calendar.HOUR_OF_DAY, 0);
    	cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.SECOND, 0);
    	cal.set(Calendar.MILLISECOND, 0);
    	long x0 = cal.getTimeInMillis();

        long span = hours * 60 * 60 * 1000;
        long x1, x2;
        if( tail ) {
            x2 = x3;
            x1 = x3 - span;
            if (x1 < x0) {
                x1 = x0;
                x3 = x1 + span;
            }
        } else {
            x1 = chartAir.getXAxis().getMin().longValue();
            x2 = x1 + span;
        }
    	
    	chartAir.getXAxis().setRange(x1,x2);
    	chartAir.getXAxis().setLimits(x0, x3);
        chartWind.getXAxis().setRange(x1, x2);
        chartWind.getXAxis().setLimits(x0, x3);
    }

    @Override
    public void updateView() {
        if( downloader != null )
            downloader.cancel(true);
    	meteo.clear();
        pastDate = null;
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

	@Override
	public void onBoundaryReached(final long requestedDate) {
        if( settings.getUpdateMode() == 0 || downloader != null || (pastDate != null && requestedDate >= pastDate) )
            return;
		if( !Tolomet.isNetworkAvailable() )
			return;
        downloader = new AsyncTask<Void,Void,Void>() {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				activity.beginProgress();
			}

			@Override
            protected Void doInBackground(Void... params) {
                if( !model.travel(requestedDate) )
					cancel(true);
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                pastDate = requestedDate;
                Toast.makeText(activity,
                        df.format(new Date(pastDate)),
                        Toast.LENGTH_SHORT
                ).show();
                updateTimeRange(false);
                meteo.clear();
                meteo.merge(model.getCurrentStation().getMeteo());
                chartAir.redraw();
                chartWind.redraw();
                downloader = null;
				activity.endProgress();
            }
            @Override
            protected void onCancelled() {
                downloader = null;
				activity.endProgress();
            }
        }.execute();
	}
}
