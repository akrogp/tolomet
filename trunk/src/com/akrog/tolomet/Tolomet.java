package com.akrog.tolomet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.akrog.tolomet.data.Station;
import com.akrog.tolomet.data.StationComparator;
import com.akrog.tolomet.data.WindProviderManager;
import com.akrog.tolomet.gae.GaeClient;
import com.akrog.tolomet.gae.Motd;
import com.androidplot.series.XYSeries;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYStepMode;
import com.androidplot.xy.YValueMarker;

public class Tolomet extends Activity
	implements OnItemSelectedListener, View.OnClickListener, OnCheckedChangeListener {//, OnTouchListener {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_tolomet);
        
        mProvider = new WindProviderManager(this);
        mDownloader = new Downloader(this);
        mGaeClient = new GaeClient(this);
        mSummary = (TextView)findViewById(R.id.textView1);
        mItems = new ArrayList<Station>();
        mStations = new ArrayList<Station>();
        mFavStations = new ArrayList<Station>();
        mCloseStations = new ArrayList<Station>();
        mRegions = new ArrayList<Station>();
        mOptions  = new ArrayList<Station>();
        mVowels  = new ArrayList<Station>();
        mStation = new Station();
        
        SpinnerState spinnerState = loadState( savedInstanceState );                       
        mSpinner = (Spinner)findViewById(R.id.spinner1);        
        mAdapter = new ArrayAdapter<Station>(this,android.R.layout.simple_spinner_item,mItems);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	mSpinner.setAdapter(mAdapter);
    	//changeSpinnerType(spinnerState.Type, spinnerState.Selection, savedInstanceState == null ? true : false);
    	changeSpinnerType(spinnerState.Type, spinnerState.Selection, false);
        mSpinner.setOnItemSelectedListener(this);
                
        mButtonRefresh = (ImageButton)findViewById(R.id.button1);
        mButtonRefresh.setOnClickListener(this);
        mButtonInfo = (ImageButton)findViewById(R.id.button2);
        mButtonInfo.setOnClickListener(this);
        
        mFavorite = (CheckBox)findViewById(R.id.favorite_button);
        mFavorite.setChecked(false);
        mFavorite.setOnCheckedChangeListener(this);
        
        createCharts();                
    }
    
    private void checkMotd() {
    	if( mGaeClient.getStatus() == Status.RUNNING || !isNetworkAvailable() )
    		return;
    	
    	// Once a day
    	SharedPreferences settings = getPreferences(0);
		Calendar cal1 = Calendar.getInstance();
		cal1.setTimeInMillis(settings.getLong("gae:last", 0));
		Calendar cal2 = Calendar.getInstance();
		if( cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
			cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) )
			return;
		
		long stamp = settings.getLong("gae:stamp", 0);
		int version = 0;
		try {
			version = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
		} catch( Exception e ) {}				
    	
    	mGaeClient = new GaeClient(this);
    	mGaeClient.execute("http://tolomet-gae.appspot.com/rest/motd?version="+version+"&stamp="+stamp);
	}
    
    private void changeSpinnerType( SpinnerType type, int sel ) {
    	changeSpinnerType(type, sel, true);
    }
	
	private void changeSpinnerType( SpinnerType type, int sel, boolean popup ) {
		mItems.clear();
		switch( type ) {
			case AllStations:
				mItems.addAll(mStations);
				break;
			case CloseStations:
				loadCloseStations();
				mItems.addAll(mCloseStations);
				break;
			case FavoriteStations:
				mItems.addAll(mFavStations);
				break;
			case RegionStations:
				mItems.addAll(mCloseStations);
				break;
			case VowelSations:
				mItems.addAll(mCloseStations);
				break;
			case Regions:
				mItems.addAll(mRegions);
				break;
			case StartMenu:
				mItems.addAll(mOptions);
				break;
			case Vowels:
				mItems.addAll(mVowels);
				break;
		}		
    	mSpinnerType = type;
    	mAdapter.notifyDataSetChanged();    	
    	mSpinner.setSelection(sel);
    	if( popup )
    		mSpinner.performClick();
	}
	
	private void changeSpinnerType( Station station ) {
		mButtonRefresh.setEnabled(false);
		mButtonInfo.setEnabled(false);
		mFavorite.setEnabled(false);
		if( station.Special == 0 )
			return;
		if( station.Special != SpinnerType.CloseStations.getValue() && mSpinnerType == SpinnerType.CloseStations ) 
			clearDistance();
		if( station.Special == SpinnerType.StartMenu.getValue() ) {
			if( mSpinnerType != SpinnerType.StartMenu )
				changeSpinnerType( SpinnerType.StartMenu, 0 );
			return;
		}
		if( station.Special < SpinnerType.StartMenu.getValue() || station.Special > 200 ) {
			mCloseStations.clear();			
			mCloseStations.add(mStations.get(0));
			mCloseStations.add(mStations.get(1));
			if( station.Special > 200 ) {
				for( Station s : mStations )
					if( s.Name.startsWith(""+(char)(station.Special-200)) )
						mCloseStations.add(s);
				changeSpinnerType( SpinnerType.VowelSations, 0 );
			} else {
				for( Station s : mStations )
					if( s.Region == station.Special )
						mCloseStations.add(s);
				changeSpinnerType( SpinnerType.RegionStations, 0 );
			}			
			return;
		}
		changeSpinnerType( SpinnerType.values()[station.Special-SpinnerType.StartMenu.getValue()], 0 );
	}
    
    private void loadStations() {
    	SharedPreferences settings = getPreferences(0);
    	InputStream inputStream = getResources().openRawResource(R.raw.stations);
		InputStreamReader in = new InputStreamReader(inputStream);
		BufferedReader rd = new BufferedReader(in);
		String line;
		Station station;
		int favs = 0;
		try {
			while( (line=rd.readLine()) != null ) {
				station = new Station(line);
				mStations.add(station);
				if( settings.contains(station.Code) ) {
					station.Favorite = true;
					mFavStations.add(station);
					favs++;
				}
			}
			rd.close();
			if( favs == 0 ) {
	    		addFavorite("C072");	// Orduña
	    		addFavorite("C042");	// Punta Galea
	    	}
		} catch( Exception e ) {			
		}    	
    }
    
    private void loadRegions() {
    	InputStream inputStream = getResources().openRawResource(R.raw.regions);
		InputStreamReader in = new InputStreamReader(inputStream);
		BufferedReader rd = new BufferedReader(in);
		String line;
		String[] fields;
		Station station;
		try {
			while( (line=rd.readLine()) != null ) {
				fields = line.split(",");
				station = new Station(fields[0],Integer.parseInt(fields[1]));
				mRegions.add(station);
			}
		} catch (IOException e) {}
	}
    
    private void loadOptions() {
    	mOptions.add(new Station(getString(R.string.menu_fav),SpinnerType.FavoriteStations.getValue()));
    	mOptions.add(new Station(getString(R.string.menu_reg),SpinnerType.Regions.getValue()));    	    	   
    	mOptions.add(new Station(getString(R.string.menu_close),SpinnerType.CloseStations.getValue()));
    	mOptions.add(new Station(getString(R.string.menu_index),SpinnerType.Vowels.getValue()));
    	mOptions.add(new Station(getString(R.string.menu_all),SpinnerType.AllStations.getValue()));
	}
    
    private void loadVowels() {
    	for( char c='A'; c <= 'Z'; c++ )
    		mVowels.add(new Station(""+c,200+c));
	}
    
    private void loadCloseStations() {
    	mCloseStations.clear();    	
    	LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    	Location ll = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    	float[] dist = new float[1];
    	for( Station station : mStations ) {    		
    		Location.distanceBetween(ll.getLatitude(), ll.getLongitude(), station.Latitude, station.Longitude, dist);
    		station.Distance = dist[0];
    		if( station.Distance < 50000.0F )
    			mCloseStations.add(station);
    	}
    	Collections.sort(mCloseStations, new StationComparator());
    	mCloseStations.add(0,mStations.get(0));
    	mCloseStations.add(1,mStations.get(1));
    }
    
    private void clearDistance() {
    	for( Station station : mStations )
    		station.Distance = -1.0F;
    }
    
    private SpinnerState loadState( Bundle bundle ) {
    	SharedPreferences settings = getPreferences(0);
    	SpinnerState spinner = new SpinnerState();
    	if( mStations.isEmpty() ) {
        	Station start = new Station("--- " + getString(R.string.select) + " ---", 0);
        	mOptions.add(start);
        	mStations.add(start);
        	mFavStations.add(start);
        	mCloseStations.add(start);
        	mRegions.add(start);
        	mVowels.add(start);
        	start = new Station("["+getString(R.string.menu_start)+"]", SpinnerType.StartMenu.getValue());
        	mStations.add(start);
        	mFavStations.add(start);
        	mCloseStations.add(start);
        	mRegions.add(start);
        	mVowels.add(start);
    		loadStations();
    		loadRegions();
    		loadOptions();
    		loadVowels();
    	}

    	if( bundle != null )
	    	for( Station station : mStations )
	    		if( !station.isSpecial() )
	    			station.loadState(bundle, settings.contains(station.Code));	    	
    	spinner.Type = SpinnerType.values()[settings.getInt("spinner-type", SpinnerType.StartMenu.getValue())-SpinnerType.StartMenu.getValue()];
    	spinner.Selection = settings.getInt("spinner-sel", 0);    	
		return spinner;
	}		

	@Override
    protected void onPause() {
    	SharedPreferences settings = getPreferences(0);
    	SharedPreferences.Editor editor = settings.edit();
    	//editor.putString("selection", mStation.Code);
    	editor.putInt("spinner-type", mSpinnerType.getValue());
    	editor.putInt("spinner-sel", mSpinner.getSelectedItemPosition());
    	editor.commit();
    	super.onPause();
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
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	for( Station station : mStations )
    		if( !station.isSpecial() && !station.isEmpty() )
    			station.saveState(outState);
    }
    
    private void updateDomainBoundaries() {
    	Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, (cal.get(Calendar.MINUTE)+9)/10*10 );
        Date date2 = cal.getTime();
        Date date1 = new Date();
        date1.setTime(date2.getTime()-4*60*60*1000);
        mChartDirection.setDomainBoundaries(date1.getTime(), date2.getTime(), BoundaryMode.FIXED);
        mChartSpeed.setDomainBoundaries(date1.getTime(), date2.getTime(), BoundaryMode.FIXED);
    }
    
    @SuppressLint("SimpleDateFormat")
	private void createCharts() {    	       
    	mChartDirection = (XYPlot)findViewById(R.id.chartDirection);
        mChartDirection.disableAllMarkup();
        //mChartDirection.setTitle(getString(R.string.Direction));
        mChartDirection.setTitle(getString(R.string.DirectionHumidity));
        mChartDirection.setRangeLabel(getString(R.string.Degrees));                
        mChartDirection.setRangeValueFormat(new DecimalFormat("#"));        
        mChartDirection.setRangeBoundaries(0, 360, BoundaryMode.FIXED);
        mChartDirection.setRangeStep(XYStepMode.SUBDIVIDE, 9);
        //mChartDirection.setTicksPerRangeLabel(3);
        mChartDirection.setDomainLabel(getString(R.string.Time));
        mChartDirection.setDomainValueFormat(new SimpleDateFormat("HH:mm"));        
        mChartDirection.setDomainStep(XYStepMode.SUBDIVIDE, 25);
        mChartDirection.setTicksPerDomainLabel(6);
        adjustFonts(mChartDirection);
        
        XYSeries series = new DynamicXYSeries( mStation.ListDirection, "Dir. Med." );
        LineAndPointFormatter format = new LineAndPointFormatter(
                Color.rgb(0, 0, 200),                   // line color
                Color.rgb(0, 0, 100),                   // point color
                null);                                  // fill color (none)
        mChartDirection.addSeries(series, format);
        series = new DynamicXYSeries( mStation.ListHumidity, "% Hum." );
        format = new LineAndPointFormatter(
                Color.rgb(200, 200, 200),                   // line color
                Color.rgb(100, 100, 100),                   // point color
                null);                                  // fill color (none)
        mChartDirection.addSeries(series, format);
        
        /*for( int i = 0; i <= 100; i += 25 )
        	mChartDirection.addMarker(getYMarker(convertHumidity(i),i+"%"));*/
        mChartDirection.addMarker(getYMarker(convertHumidity(0),0+"%"));
        mChartDirection.addMarker(getYMarker(convertHumidity(50),50+"%"));
        mChartDirection.addMarker(getYMarker(convertHumidity(100),100+"%"));
        
        mChartSpeed = (XYPlot)findViewById(R.id.chartSpeed);
        mChartSpeed.disableAllMarkup();
        mChartSpeed.setTitle(getString(R.string.Speed));
        mChartSpeed.setRangeLabel("km/h");        
        mChartSpeed.setRangeValueFormat(new DecimalFormat("#"));
        mChartSpeed.setRangeBoundaries(0, 50, BoundaryMode.FIXED);
        mChartSpeed.setRangeStep(XYStepMode.SUBDIVIDE, 11);
        //mChartSpeed.setTicksPerRangeLabel(2);
        mChartSpeed.setDomainLabel(getString(R.string.Time));
        mChartSpeed.setDomainValueFormat(new SimpleDateFormat("HH:mm"));        
        mChartSpeed.setDomainStep(XYStepMode.SUBDIVIDE, 25);
        mChartSpeed.setTicksPerDomainLabel(6);
        adjustFonts(mChartSpeed);
        
        series = new DynamicXYSeries( mStation.ListSpeedMed, "Vel. Med." );
        format = new LineAndPointFormatter(
                Color.rgb(0, 200, 0),                   // line color
                Color.rgb(0, 100, 0),                   // point color
                null);                                  // fill color (none)
        mChartSpeed.addSeries(series, format);
        series = new DynamicXYSeries( mStation.ListSpeedMax, "Vel. Máx." );
        format = new LineAndPointFormatter(
                Color.rgb(200, 0, 0),                   // line color
                Color.rgb(100, 0, 0),                   // point color
                null);                                  // fill color (none)
        mChartSpeed.addSeries(series, format);
        
        mChartSpeed.addMarker(getYMarker(10));        
        mChartSpeed.addMarker(getYMarker(30));
        
        updateDomainBoundaries();
        
        /*mChartSpeed.calculateMinMaxVals();
		mMinXY = new PointF(mChartSpeed.getCalculatedMinX().floatValue(),mChartSpeed.getCalculatedMinY().floatValue());
		mMaxXY = new PointF(mChartSpeed.getCalculatedMaxX().floatValue(),mChartSpeed.getCalculatedMaxY().floatValue());
		mChartSpeed.setOnTouchListener(this);*/
    }
    
    private void adjustFonts( XYPlot plot ) {
    	plot.getGraphWidget().setMarginBottom(mFontSize);
    	plot.getGraphWidget().setMarginTop(mFontSize);
    	plot.getGraphWidget().setMarginRight(1.5f*mFontSize);
    	//plot.getTitleWidget().getLabelPaint().setTextSize(mFontSize);
    	//plot.getLegendWidget().getTextPaint().setTextSize(mFontSize);
        plot.getGraphWidget().getDomainLabelPaint().setTextSize(mFontSize);
        plot.getGraphWidget().getDomainOriginLabelPaint().setTextSize(mFontSize);
        plot.getGraphWidget().getRangeLabelPaint().setTextSize(mFontSize);
        plot.getGraphWidget().getRangeOriginLabelPaint().setTextSize(mFontSize);
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
    
    public void onClick(View v) {
    	if( mStation.isSpecial() )
    		return;
    	switch( v.getId() ) {
    		case R.id.button1:
    			loadData();
    			break;
    		case R.id.button2:
    			if( alertNetwork() )
    				return;
    			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mProvider.getInfoUrl(mStation))));
    			break;
    	}
	}
    
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    	if( isChecked )
    		addFavorite();
    	else
    		removeFavorite();    	
	}
    
    private void addFavorite() {
    	addFavorite( mStation.Code );
    }
    
    private void addFavorite( String code ) {
    	// Redundancy check
    	if( code.equals("none") )
    		return;
    	SharedPreferences settings = getPreferences(0);
    	if( settings.contains(code) )
    		return;
    	
    	// List
    	mFavStations.clear();
    	mFavStations.add(mStations.get(0));
    	mFavStations.add(mStations.get(1));
    	for( Station station : mStations ) {
    		if( station.Code.equals(code) )
    			station.Favorite = true;
    		if( station.Favorite )
    			mFavStations.add(station);
    	}
    	
    	// State
    	SharedPreferences.Editor editor = settings.edit();
    	editor.putBoolean(code, true);
    	editor.commit();
	}

	private void removeFavorite() {
		// Redundancy check
		SharedPreferences settings = getPreferences(0);
		if( !settings.contains(mStation.Code) )
    		return;
		
		// List
    	for( Station station : mFavStations )
    		if( station.Code.equals(mStation.Code) ) {
    			station.Favorite = false;
    			mFavStations.remove(station);
    			break;
    		}
    	
    	// Spinner
    	if( mSpinnerType == SpinnerType.FavoriteStations ) {
    		mAdapter.notifyDataSetChanged();
    		//mSpinner.setSelection(0);
    	}
    	
    	// State
    	SharedPreferences.Editor editor = settings.edit();
    	editor.remove(mStation.Code);
    	editor.commit();
	}

	private void loadData() {
    	if( !mProvider.updateTimes(mStation) ) {
    		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setMessage( getString(R.string.Impatient) + " " + mProvider.getRefresh(mStation) + " " + getString(R.string.minutes) );
			alertDialog.show();
			return;
    	}
    	if( alertNetwork() )
			return;
    	String uri = mProvider.getUrl(mStation); 
    	//System.out.println(uri);
    	mDownloader = new Downloader(this);
    	mDownloader.execute(uri);
    }
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
	}
	
	private boolean alertNetwork() {
		if( !isNetworkAvailable() ) {
    		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setMessage( getString(R.string.NoNetwork) );
			alertDialog.show();
			return true;
    	}
		return false;
	}
    
    public void redraw() {
    	mChartDirection.redraw();
        mChartSpeed.redraw();
        updateSummary();
        updateDomainBoundaries();
        mFavorite.setChecked(mStation.Favorite);
    }
    
    /*public void postRedraw() {
    	runOnUiThread(new Runnable() {
            public void run() {
            	redraw();
            }
        });
    }*/

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
		Station station = (Station)mSpinner.getSelectedItem();
		mStation.replace(station);
		mFavorite.setChecked(station.Favorite);
		
		if( station.isSpecial() ) {
			mStation.clear();
			changeSpinnerType(station);
			redraw();
			return;
		}
		mButtonRefresh.setEnabled(true);
		mButtonInfo.setEnabled(true);
		mFavorite.setEnabled(true);
		if( mStation.isOutdated() )
			loadData();
		else
			redraw();	
	}
	
	public void onNothingSelected(AdapterView<?> arg0) {
		mStation.clear();
	}
	
	private boolean getLast( List<Number> list, Number[] vals ) {
		int len =  list.size();
		if( len < 2 || vals.length < 2 )
			return false;		
		vals[0] = list.get(len-2);
		vals[1] = list.get(len-1);		
		return true;
	}

	@SuppressLint("SimpleDateFormat")
	private void updateSummary() {
		if( mStation.isEmpty() ) {
			mSummary.setText(getString(R.string.NoData));
			return;
		}
		
        Number[] last = new Number[2];        
        long d, d2;
        int dir;
        float med, max, h;
        int hum = -1;
        
        getLast(mStation.ListDirection, last);
        d = (Long)last[0];
        dir = (Integer)last[1];
        getLast(mStation.ListSpeedMed, last);
        med = (Float)last[1];
        getLast(mStation.ListSpeedMax, last);
        max = (Float)last[1];
        if( getLast(mStation.ListHumidity, last) ) {
        	d2 = (Long)last[0];
            h = (Float)last[1];
        	if( d2 == d )
        		hum = convertHumidity(h);
        }
        
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(d);
        SimpleDateFormat df = new SimpleDateFormat();
        df.applyPattern("HH:mm");
        String date = df.format(cal.getTime());
        if( hum < 0 )
        	mSummary.setText( String.format("%s | %dº (%s) | %.1f~%.1f km/h", date, dir, getDir(dir), med, max ));
        else
        	if( getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE )
        		mSummary.setText( String.format("%s | %dº (%s) | %d %% | %.1f~%.1f km/h", date, dir, getDir(dir), hum, med, max ));
        	else
        		mSummary.setText( String.format("%s|%dº(%s)|%d%%|%.1f~%.1f", date, dir, getDir(dir), hum, med, max ));
	}
	
	private String getDir( int degrees ) {
		String[] vals = {"N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"};
        double deg = degrees + 11.25;
		while( deg >= 360.0 )
			deg -= 360.0;
		int index = (int)(deg/22.5);
		if( index < 0 )
			index = 0;
		else if( index >= 16 )
			index = 15;
		return vals[index];
	}
	
	public void onDownloaded(String result) {
        Station sel = (Station)mSpinner.getSelectedItem();
        try {
        	mProvider.updateStation(mStation, result);
        	sel.replace(mStation);
        } catch (Exception e) {
			System.out.println( e.getMessage() );
			mStation.replace(sel);
		}
        redraw();
        checkMotd();
    }
	
	private String getChanges( Motd motd  ) {
		StringWriter string = new StringWriter();
		PrintWriter writer = new PrintWriter(string);
		if( motd.getChanges() != null ) {
			writer.println(getString(R.string.improvements)+" v"+motd.getVersion()+":");
			for( String str : motd.getChanges() )
				writer.println("* "+str);
		}
		writer.close();
		return string.toString();
	}
	
	public void onMotd(Motd motd) {
		SharedPreferences settings = getPreferences(0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putLong("gae:last", Calendar.getInstance().getTimeInMillis());
		
		if( motd.getVersion() != null ) {		
			new AlertDialog.Builder(this)
		    .setTitle(R.string.newversion)
		    .setMessage(getChanges(motd))
		    .setPositiveButton(R.string.update,
		    new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		        	dialog.dismiss();
		            Intent marketIntent = new Intent(
		            Intent.ACTION_VIEW,
		            Uri.parse("http://market.android.com/details?id=com.akrog.tolomet"));
		            startActivity(marketIntent);
		        }
		    })
		    .setNegativeButton(R.string.tomorrow,
		    new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		            dialog.dismiss();
		        }
		    }).create().show();
		} else if( motd.getMotd() != null) {
			new AlertDialog.Builder(this)
		    .setTitle(R.string.motd)
		    .setMessage(motd.getMotd())
		    .create().show();			
			editor.putLong("gae:stamp", motd.getStamp());				    	
		}
		
		editor.commit();
	}

	public void OnCancelled() {
		redraw();
	}
	
	public boolean onTouch( View arg0, MotionEvent event ) {
		/*switch( event.getAction() & MotionEvent.ACTION_MASK ) {
			case MotionEvent.ACTION_DOWN: // Start gesture
				mFirstFinger = new PointF(event.getX(), event.getY());
				mTouchMode = ONE_FINGER_DRAG;
				break;
			case MotionEvent.ACTION_UP: 
			case MotionEvent.ACTION_POINTER_UP:
				//When the gesture ends, a thread is created to give inertia to the scrolling and zoom 
				Timer t = new Timer();
				t.schedule(new TimerTask() {
					@Override
					public void run() {
						while( Math.abs(mLastScrolling)>1f || Math.abs(mLastZooming-1)<1.01 ) { 
							mLastScrolling *= .8;
							scroll(mLastScrolling);
							mLastZooming += (1-mLastZooming)*.2;
							zoom(mLastZooming);
							mChartSpeed.setDomainBoundaries( mMinXY.x, mMaxXY.x, BoundaryMode.AUTO);
							try {
								mChartSpeed.postRedraw();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						// the thread lives until the scrolling and zooming are imperceptible
						}
					}
				}, 0);
			case MotionEvent.ACTION_POINTER_DOWN: // second finger
				mDistBetweenFingers = spacing(event);
				// the distance check is done to avoid false alarms
				if( mDistBetweenFingers > 5f ) {
					mTouchMode = TWO_FINGERS_DRAG;
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if( mTouchMode == ONE_FINGER_DRAG ) {
					PointF oldFirstFinger = mFirstFinger;
					mFirstFinger = new PointF(event.getX(), event.getY());
					mLastScrolling = oldFirstFinger.x-mFirstFinger.x;
					scroll(mLastScrolling);
					mLastZooming = (mFirstFinger.y-oldFirstFinger.y)/mChartSpeed.getHeight();
					if( mLastZooming<0 )
						mLastZooming = 1/(1-mLastZooming);
					else
						mLastZooming++;
					zoom(mLastZooming);
					mChartSpeed.setDomainBoundaries(mMinXY.x, mMaxXY.x, BoundaryMode.AUTO);
					mChartSpeed.redraw();
	 
				} else if( mTouchMode == TWO_FINGERS_DRAG ) {
					float oldDist = mDistBetweenFingers; 
					mDistBetweenFingers = spacing(event);
					mLastZooming = oldDist/mDistBetweenFingers;
					zoom(mLastZooming);
					mChartSpeed.setDomainBoundaries(mMinXY.x, mMaxXY.x, BoundaryMode.AUTO);
					mChartSpeed.redraw();
				}
				break;
		}*/
		return true;
	}
	
	/*private void zoom( float scale ) {
		float domainSpan = mMaxXY.x	 - mMinXY.x;
		float domainMidPoint = mMaxXY.x	 - domainSpan / 2.0f;
		float offset = domainSpan * scale / 2.0f;
		mMinXY.x = domainMidPoint - offset;
		mMaxXY.x = domainMidPoint + offset;
	}
 
	private void scroll( float pan ) {
		float domainSpan = mMaxXY.x	- mMinXY.x;
		float step = domainSpan / mChartSpeed.getWidth();
		float offset = pan * step;
		mMinXY.x += offset;
		mMaxXY.x += offset;
	}
 
	private float spacing( MotionEvent event ) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}*/

	private ImageButton mButtonRefresh, mButtonInfo;
	private XYPlot mChartSpeed, mChartDirection;
	private Spinner mSpinner;
	private ArrayAdapter<Station> mAdapter;
	private SpinnerType mSpinnerType;
	private TextView mSummary;
	private CheckBox mFavorite;
	Downloader mDownloader;
	GaeClient mGaeClient;
	private Station mStation;
	private List<Station> mItems, mStations, mFavStations, mCloseStations, mRegions, mOptions, mVowels;	
	private WindProviderManager mProvider;
	static final float mFontSize = 16;		
		
	// Touch	
	/*static final int NONE = 0;
	static final int ONE_FINGER_DRAG = 1;
	static final int TWO_FINGERS_DRAG = 2;
	int mTouchMode = NONE;	 
	PointF mFirstFinger;
	float mLastScrolling;
	float mDistBetweenFingers;
	float mLastZooming;
	private PointF mMinXY;
	private PointF mMaxXY;*/
}
