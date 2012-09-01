package com.akrog.tolomet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
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
        
        mListDirection = new ArrayList<Number>();
    	mListSpeedMed = new ArrayList<Number>();
    	mListSpeedMax = new ArrayList<Number>();
    	mMapDirection = new HashMap<String, List<Number>>();
    	mMapSpeedMed = new HashMap<String, List<Number>>();
    	mMapSpeedMax = new HashMap<String, List<Number>>();
        
        createCharts();                                                                   
        
        mProgress = new ProgressDialog(this);
        mProgress.setMessage( getString(R.string.Downloading)+"..." );
        mProgress.setTitle( getString(R.string.Progress) );
        mProgress.setIndeterminate(true);
        mProgress.setCancelable(true);
    }
    
    private void createCharts() {
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
        
        XYSeries series = new DynamicXYSeries( mListDirection, "Dir. Med." );
        LineAndPointFormatter format = new LineAndPointFormatter(
                Color.rgb(0, 0, 200),                   // line color
                Color.rgb(0, 0, 100),                   // point color
                null);                                  // fill color (none)
        mChartDirection.addSeries(series, format);
        
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
        
        series = new DynamicXYSeries( mListSpeedMed, "Vel. Med." );
        format = new LineAndPointFormatter(
                Color.rgb(0, 200, 0),                   // line color
                Color.rgb(0, 100, 0),                   // point color
                null);                                  // fill color (none)
        mChartSpeed.addSeries(series, format);
        series = new DynamicXYSeries( mListSpeedMax, "Vel. Máx." );
        format = new LineAndPointFormatter(
                Color.rgb(200, 0, 0),                   // line color
                Color.rgb(100, 0, 0),                   // point color
                null);                                  // fill color (none)
        mChartSpeed.addSeries(series, format);
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
    	//System.out.println(uri);
    	AsyncTask<String, Void, String> task = new Downloader();
    	task.execute(uri);
    }   
    
    private void loadStored() {
    	List<Number> list;
    	list = mMapDirection.get(mStationCode);
    	mListDirection.clear(); mListDirection.addAll(list);
    	list = mMapSpeedMed.get(mStationCode);
    	mListSpeedMed.clear(); mListSpeedMed.addAll(list);
    	list = mMapSpeedMax.get(mStationCode);
    	mListSpeedMax.clear(); mListSpeedMax.addAll(list);
    }
    
    public void refresh() {
    	getStation();
    	loadData();
    }    

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate(R.menu.activity_tolomet, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected( MenuItem item ) {
    	switch( item.getItemId() ) {
    		case R.id.about:
    			AboutDialog about = new AboutDialog(this);
    			about.setTitle(getString(R.string.About));
    			about.show();
    			break;
    	}
    	return true;
    }

	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		getStation();
		if( mMapDirection.containsKey(mStationCode) ) {
			loadStored();
			mChartDirection.redraw();
	        mChartSpeed.redraw();
	        updateSummary();
		} else
			loadData();
	}

	public void onNothingSelected(AdapterView<?> parent) {
	}
	
	private void updateSummary() {
		int i = mListDirection.size()-1;
        int dir = (Integer)mListDirection.get(i);
        float med = (Float)mListSpeedMed.get(i);
        float max = (Float)mListSpeedMax.get(i);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis((Long)mListDirection.get(i-1));
        SimpleDateFormat df = new SimpleDateFormat();
        df.applyPattern("HH:mm");
        String date = df.format(cal.getTime());
		mSummary.setText( String.format("%s> %dº (%s), %.1f~%.1f km/h",
				date, dir, getDir(dir), med, max ));
	}
	
	private String getDir( int degrees ) {
		String[] vals = {"N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW"};
		degrees += 12;
		if( degrees > 360 )
			degrees -= 360;
		return vals[degrees/24];
	}
	
	private class Downloader extends AsyncTask<String, Void, String> {
		@Override
	    protected void onPreExecute() {
	        super.onPreExecute();	        
	        mProgress.show();
	    }
		
		@Override
		protected String doInBackground(String... urls) {
			StringBuilder builder = new StringBuilder();
	    	try {
	    		URL url = new URL(urls[0]);
	    		URLConnection con = url.openConnection();
	    		BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
	    		String line;
	    		while( (line=rd.readLine()) != null )
	    			builder.append(line);
	    		rd.close();
	    	} catch( Exception e ) {
	    		System.out.println(e.getMessage());
			}
	    	return builder.toString();
		}		
		
		@Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);	        
	        mProgress.dismiss();
	        try {		        
		        String[] lines = result.split("<tr>");
		        Number date, val;
		        mListDirection.clear();
		        mListSpeedMed.clear();
		        mListSpeedMax.clear();		        
		        for( int i = 1; i < lines.length; i++ ) {
		        	String[] cells = lines[i].split("<td");
		        	if( getContent(cells[2]).equals("-") )
		        		break;
		        	date = toEpoch(getContent(cells[1]));
		        	val = Integer.parseInt(getContent(cells[3]));
		        	mListDirection.add(date);
		        	mListDirection.add(val);
		        	val = Float.parseFloat(getContent(cells[2]));
		        	mListSpeedMed.add(date);
		        	mListSpeedMed.add(val);
		        	val = Float.parseFloat(getContent(cells[4]));
		        	mListSpeedMax.add(date);
		        	mListSpeedMax.add(val);
		        }
		        updateLists();
		        mChartDirection.postRedraw(false);
		        mChartSpeed.postRedraw(false);
		        updateSummary();	        
	        } catch (Exception e) {
				System.out.println( e.getMessage() );
			}
	    }
		
		private void updateLists() {
			List<Number> list;
	        if( mMapDirection.containsKey(mStationCode) ) {	        	
	        	list = mMapDirection.get(mStationCode);
	        	list.clear(); list.addAll(mListDirection);
	        	list = mMapSpeedMed.get(mStationCode);
	        	list.clear(); list.addAll(mListSpeedMed);
	        	list = mMapSpeedMax.get(mStationCode);
	        	list.clear(); list.addAll(mListSpeedMax);
	        } else {
	        	list = new ArrayList<Number>();
	        	list.addAll(mListDirection);
	        	mMapDirection.put(mStationCode, list);
	        	list = new ArrayList<Number>();
	        	list.addAll(mListSpeedMed);
	        	mMapSpeedMed.put(mStationCode, list);
	        	list = new ArrayList<Number>();
	        	list.addAll(mListSpeedMax);
	        	mMapSpeedMax.put(mStationCode, list);
	        }
		}								
		
		private String getContent( String cell ) {
			cell = cell.replaceAll("\n", "").replaceAll("\r", "").replaceAll("\t", "").replaceAll(" ", "");
			int i = cell.indexOf('>')+1;
			if( cell.charAt(i) == '<' )
				i = cell.indexOf('>', i)+1;
			int i2 = cell.indexOf('<', i);
			return cell.substring(i, i2).replace(',', '.');
		}
		
		private long toEpoch( String str ) {
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			String[] fields = str.split(":");
			cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(fields[0]) );
		    cal.set(Calendar.MINUTE, Integer.parseInt(fields[1]) );
		    return cal.getTimeInMillis();
		}
	}
	
	private XYPlot mChartSpeed, mChartDirection;
	private String mStation, mStationCode, mStationName;
	private Spinner mSpinner;
	private TextView mSummary;
	private ProgressDialog mProgress;
	private List<Number> mListDirection, mListSpeedMed, mListSpeedMax;
	private Map<String,List<Number>> mMapDirection, mMapSpeedMed, mMapSpeedMax;
}
