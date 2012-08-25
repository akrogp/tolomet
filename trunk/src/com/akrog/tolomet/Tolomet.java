package com.akrog.tolomet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidplot.series.XYSeries;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYStepMode;

public class Tolomet extends Activity implements OnItemSelectedListener, View.OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
        setContentView(R.layout.activity_tolomet);
        
        mSummary = (TextView)findViewById(R.id.textView1);
        
        mSpinner = (Spinner)findViewById(R.id.spinner1);
        mSpinner.setSelection(0);
        mSpinner.setOnItemSelectedListener(this);        
        
        Button button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(this);
             
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, (cal.get(Calendar.MINUTE)+9)/10*10 );
        Date date2 = cal.getTime();
        Date date1 = new Date();
        date1.setTime(date2.getTime()-4*60*60*1000);
                
        mChartDirection = (XYPlot)findViewById(R.id.chartDirection);
        mChartDirection.disableAllMarkup();
        mChartDirection.setRangeLabel("Grados");                
        mChartDirection.setRangeValueFormat(new DecimalFormat("#"));        
        mChartDirection.setRangeBoundaries(0, 360, BoundaryMode.FIXED);
        mChartDirection.setRangeStep(XYStepMode.SUBDIVIDE, 9);
        //mChartDirection.setTicksPerRangeLabel(3);
        mChartDirection.setDomainLabel("Hora");
        mChartDirection.setDomainValueFormat(new SimpleDateFormat("HH:mm"));
        mChartDirection.setDomainBoundaries(date1.getTime(), date2.getTime(), BoundaryMode.FIXED);
        mChartDirection.setDomainStep(XYStepMode.SUBDIVIDE, 25);
        mChartDirection.setTicksPerDomainLabel(6);
        
        mChartSpeed = (XYPlot)findViewById(R.id.chartSpeed);
        mChartSpeed.disableAllMarkup();
        mChartSpeed.setRangeLabel("km/h");        
        mChartSpeed.setRangeValueFormat(new DecimalFormat("#"));
        mChartSpeed.setRangeBoundaries(0, 50, BoundaryMode.FIXED);
        mChartSpeed.setRangeStep(XYStepMode.SUBDIVIDE, 11);
        //mChartSpeed.setTicksPerRangeLabel(2);
        mChartSpeed.setDomainLabel("Hora");
        mChartSpeed.setDomainValueFormat(new SimpleDateFormat("HH:mm"));
        mChartSpeed.setDomainBoundaries(date1.getTime(), date2.getTime(), BoundaryMode.FIXED);
        mChartSpeed.setDomainStep(XYStepMode.SUBDIVIDE, 25);
        mChartSpeed.setTicksPerDomainLabel(6);        
    }
    
    /*protected void onResume() {
        super.onResume();
    }*/
    
    public void onClick(View v) {    	
    	refresh();
	}
    
    private void getStation() {
    	mStation = ((TextView)mSpinner.getSelectedView()).getText().toString();
    	String[] fields = mStation.split(" - ");
    	mStationCode = fields[0];
    	mStationName = fields[1];
    }    
    
    private void loadData() {
    	Calendar cal = Calendar.getInstance();
    	String uri = String.format(
    			"%s&anyo=%d&mes=%d&dia=%d&hora=%s&CodigoEstacion=%s&pagina=1&R01HNoPortal=true", new Object[]{
    			"http://www.euskalmet.euskadi.net/s07-5853x/es/meteorologia/lectur_fr.apl?e=5",
    			cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH),
    			"00:00%2023:50", mStationCode
    	} );
    	System.out.println(uri);
    	StringBuilder builder = new StringBuilder();
    	try {
    		URL url = new URL(uri);
    		URLConnection con = url.openConnection();
    		BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
    		String line;
    		while( (line=rd.readLine()) != null )
    			builder.append(line);
    		rd.close();
    	} catch( Exception e ) {
    		System.out.println(e.getMessage());
		}
    	String html = builder.toString();
    	//html.split("<tr>")
    }
    
    public void refresh() {
    	getStation();
    	loadData();
    	
    	// Create a couple arrays of y-values to plot:
        Number[] series1Numbers = {1, 8, 5, 2, 7, 4};
        Number[] series2Numbers = {4, 6, 3, 8, 2, 10};
 
        // Turn the above arrays into XYSeries':
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers),          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "Series1");                             // Set the display title of the series
 
        // same as above
        XYSeries series2 = new SimpleXYSeries(Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series2");
 
        // Create a formatter to use for drawing a series using LineAndPointRenderer:
        LineAndPointFormatter series1Format = new LineAndPointFormatter(
                Color.rgb(0, 200, 0),                   // line color
                Color.rgb(0, 100, 0),                   // point color
                null);                                  // fill color (none)
 
        // add a new series' to the xyplot:
        mChartSpeed.addSeries(series1, series1Format);
 
        // same as above:
        mChartSpeed.addSeries(series2,
                new LineAndPointFormatter(Color.rgb(0, 0, 200), Color.rgb(0, 0, 100), null));
        
        if( mStation != null )
        	mSummary.setText(mStationName + ": 353º - 20km/h (00:00)");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_tolomet, menu);
        return true;
    }

	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	}

	public void onNothingSelected(AdapterView<?> parent) {
	}
	
	private XYPlot mChartSpeed, mChartDirection;
	private String mStation, mStationCode, mStationName;
	private Spinner mSpinner;
	private TextView mSummary;
}
