package com.akrog.tolomet.view;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.graphics.Color;

import com.akrog.tolomet.R;
import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.data.StationManager;
import com.androidplot.series.XYSeries;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYStepMode;
import com.androidplot.xy.YValueMarker;

public class MyCharts {
	private Tolomet tolomet;
	private StationManager stations;
	private XYPlot chartSpeed, chartDirection;
	static final float fontSize = 16;
	private boolean zoom;		

	public MyCharts( Tolomet tolomet, StationManager data ) {
		this.tolomet = tolomet;
		this.stations = data;
		createCharts();
	}
	
	@SuppressLint("SimpleDateFormat")
	private void createCharts() {    	       
    	this.chartDirection = (XYPlot)this.tolomet.findViewById(R.id.chartDirection);
        this.chartDirection.disableAllMarkup();
        //this.chartDirection.setTitle(getString(R.string.Direction));
        this.chartDirection.setTitle(this.tolomet.getString(R.string.DirectionHumidity));
        this.chartDirection.setRangeLabel(this.tolomet.getString(R.string.Degrees));                
        this.chartDirection.setRangeValueFormat(new DecimalFormat("#"));        
        this.chartDirection.setRangeBoundaries(0, 360, BoundaryMode.FIXED);
        this.chartDirection.setRangeStep(XYStepMode.SUBDIVIDE, 9);
        //this.chartDirection.setTicksPerRangeLabel(3);
        this.chartDirection.setDomainLabel(this.tolomet.getString(R.string.Time));
        this.chartDirection.setDomainValueFormat(new SimpleDateFormat("HH:mm"));        
        this.chartDirection.setDomainStep(XYStepMode.SUBDIVIDE, 25);
        this.chartDirection.setTicksPerDomainLabel(6);
        adjustFonts(this.chartDirection);
        
        XYSeries series = new DynamicXYSeries( this.stations.current.listDirection, "Dir. Med." );
        LineAndPointFormatter format = new LineAndPointFormatter(
                Color.rgb(0, 0, 200),                   // line color
                Color.rgb(0, 0, 100),                   // point color
                null);                                  // fill color (none)
        this.chartDirection.addSeries(series, format);
        series = new DynamicXYSeries( this.stations.current.listHumidity, "% Hum." );
        format = new LineAndPointFormatter(
                Color.rgb(200, 200, 200),                   // line color
                Color.rgb(100, 100, 100),                   // point color
                null);                                  // fill color (none)
        this.chartDirection.addSeries(series, format);
        
        /*for( int i = 0; i <= 100; i += 25 )
        	this.chartDirection.addMarker(getYMarker(convertHumidity(i),i+"%"));*/
        this.chartDirection.addMarker(getYMarker(convertHumidity(0),0+"%"));
        this.chartDirection.addMarker(getYMarker(convertHumidity(50),50+"%"));
        this.chartDirection.addMarker(getYMarker(convertHumidity(100),100+"%"));
        
        this.chartSpeed = (XYPlot)this.tolomet.findViewById(R.id.chartSpeed);
        this.chartSpeed.disableAllMarkup();
        this.chartSpeed.setTitle(this.tolomet.getString(R.string.Speed));
        this.chartSpeed.setRangeLabel("km/h");        
        this.chartSpeed.setRangeValueFormat(new DecimalFormat("#"));
        this.chartSpeed.setRangeBoundaries(0, 50, BoundaryMode.FIXED);
        this.chartSpeed.setRangeStep(XYStepMode.SUBDIVIDE, 11);
        //this.chartSpeed.setTicksPerRangeLabel(2);
        this.chartSpeed.setDomainLabel(this.tolomet.getString(R.string.Time));
        this.chartSpeed.setDomainValueFormat(new SimpleDateFormat("HH:mm"));        
        this.chartSpeed.setDomainStep(XYStepMode.SUBDIVIDE, 25);
        this.chartSpeed.setTicksPerDomainLabel(6);
        adjustFonts(this.chartSpeed);
        
        series = new DynamicXYSeries( this.stations.current.listSpeedMed, "Vel. Med." );
        format = new LineAndPointFormatter(
                Color.rgb(0, 200, 0),                   // line color
                Color.rgb(0, 100, 0),                   // point color
                null);                                  // fill color (none)
        this.chartSpeed.addSeries(series, format);
        series = new DynamicXYSeries( this.stations.current.listSpeedMax, "Vel. Máx." );
        format = new LineAndPointFormatter(
                Color.rgb(200, 0, 0),                   // line color
                Color.rgb(100, 0, 0),                   // point color
                null);                                  // fill color (none)
        this.chartSpeed.addSeries(series, format);
        
        this.chartSpeed.addMarker(getYMarker(10));
        this.chartSpeed.addMarker(getYMarker(30));
        
        //updateDomainBoundaries();
        setZoom(true);
    }
	
	public static float convertHumidity( int hum ) {
    	return 45.0F+hum*2.7F;
    	//return hum*3.6F;
    	//return hum*3.15F;
    }
    
    public static int convertHumidity( float hum ) {
    	return (int)((hum-45.0)/2.7+0.05);
    	//return (int)(hum/3.6+0.05);
    	//return (int)(hum/3.15+0.05);
    }
    
    private void adjustFonts( XYPlot plot ) {
    	plot.getGraphWidget().setMarginBottom(MyCharts.fontSize);
    	plot.getGraphWidget().setMarginTop(MyCharts.fontSize);
    	plot.getGraphWidget().setMarginRight(1.5f*MyCharts.fontSize);
    	//plot.getTitleWidget().getLabelPaint().setTextSize(this.fontSize);
    	//plot.getLegendWidget().getTextPaint().setTextSize(this.fontSize);
        plot.getGraphWidget().getDomainLabelPaint().setTextSize(MyCharts.fontSize);
        plot.getGraphWidget().getDomainOriginLabelPaint().setTextSize(MyCharts.fontSize);
        plot.getGraphWidget().getRangeLabelPaint().setTextSize(MyCharts.fontSize);
        plot.getGraphWidget().getRangeOriginLabelPaint().setTextSize(MyCharts.fontSize);
        plot.getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE);
    }
    
    private YValueMarker getYMarker( float y ) {
        return getYMarker(y, null);
    }
    
    private YValueMarker getYMarker( float y, String text ) {
    	YValueMarker m = new YValueMarker(y, text);
    	m.getLinePaint().setColor(Color.BLACK);
        m.getLinePaint().setStrokeWidth(0.0f);
        m.getTextPaint().setColor(Color.BLACK);
        return m;
    }
    
    private void updateDomainBoundaries() {
    	Calendar cal = Calendar.getInstance();
    	Date date2;
        Date date1;
    	cal.set(Calendar.SECOND, 0);
    	cal.set(Calendar.MILLISECOND, 0);
    	if( zoom ) {    		
	        cal.set(Calendar.MINUTE, (cal.get(Calendar.MINUTE)+9)/10*10 );
	        date2 = cal.getTime();
	        date1 = new Date();
	        date1.setTime(date2.getTime()-4*60*60*1000);	        
    	} else {
    		cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY)+1 );
    		cal.set(Calendar.MINUTE, 0 );
    		date2 = cal.getTime();
	        date1 = new Date();
	        date1.setTime(date2.getTime()-24*60*60*1000);
    	}
    	this.chartDirection.setDomainBoundaries(date1.getTime(), date2.getTime(), BoundaryMode.FIXED);
        this.chartSpeed.setDomainBoundaries(date1.getTime(), date2.getTime(), BoundaryMode.FIXED);
    }
    
    public void redraw() {
    	updateDomainBoundaries();
    	this.chartDirection.redraw();
        this.chartSpeed.redraw();
    }
    
    public boolean isZoom() {
		return zoom;
	}

	public void setZoom(boolean zoom) {
		this.zoom = zoom;		
        updateDomainBoundaries();
	}
}
