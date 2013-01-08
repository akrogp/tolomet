package com.akrog.tolomet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

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
                
        mStations = new ArrayList<Station>();
        String code = loadState( savedInstanceState );
        mStation = new Station();
        
        mSummary = (TextView)findViewById(R.id.textView1);
        
        createSpinner(code);
                
        Button button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(this);
        
        mFavorite = (CheckBox)findViewById(R.id.favorite_button);
        mFavorite.setChecked(false);
        mFavorite.setOnCheckedChangeListener(this);
        
        createCharts();                                                                   
                        
        mProgress = new ProgressDialog(this);
        mProgress.setMessage( getString(R.string.Downloading)+"..." );
        mProgress.setTitle( "" );//getString(R.string.Progress) );
        mProgress.setIndeterminate(true);
        mProgress.setCancelable(true);
        mProgress.setOnCancelListener(new OnCancelListener(){
        	public void onCancel(DialogInterface dialog) {
        		mDownloader.cancel(true);
        	}
        });
    }
    
    private void createSpinner( String code ) {
    	mSpinner = (Spinner)findViewById(R.id.spinner1);
    	
    	// Initial population
    	if( mStations.isEmpty() )
    		loadStations();
    	
    	// AddFavorites
    	SharedPreferences settings = getPreferences(0);
    	int favs = 0;
    	for( int i = mStations.size()-1; i >= favs; i-- )
    		if( settings.contains(mStations.get(i).Code) ) {
    			mStations.get(i).Favorite = true;
    			mStations.add(0, mStations.get(i).getLinkedClone());
    			i++;
    			favs++;
    		}
    	if( favs == 0 ) {
    		mAdapter = null;
    		addFavorite("C072");	// Orduña
    		addFavorite("C042");	// Punta Galea
    	}
    	
    	// Adapter
    	mAdapter = new ArrayAdapter<Station>(this,android.R.layout.simple_spinner_item,mStations);
    	mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	mSpinner.setAdapter(mAdapter);
    	
    	// Selection
    	int sel = 0;
    	if( code != null ) {
	    	for( Station station : mStations )
	    		if( station.Code.equals(code) )
	    			break;
	    		else
	    			sel++;
	    	if( sel >= mStations.size() )
	    		sel = 0;
    	}
    	mSpinner.setSelection(sel);
        mSpinner.setOnItemSelectedListener(this);
    }
    
    private void loadStations() {
    	InputStream inputStream = getResources().openRawResource(R.raw.stations);
		InputStreamReader in = new InputStreamReader(inputStream);
		BufferedReader rd = new BufferedReader(in);
		String line;
		Station station;
		try {
			while( (line=rd.readLine()) != null ) {
				station = new Station(line);
				mStations.add(station);
			}
			rd.close();
		} catch( Exception e ) {			
		}		
    }
    
    private String loadState( Bundle bundle ) {
    	if( bundle != null ) {
    		loadStations();    		
	    	for( Station station : mStations )
	    		station.loadState(bundle);
    	}
    	SharedPreferences settings = getPreferences(0);
		return settings.getString("selection", null);
	}

	@Override
    protected void onPause() {
    	SharedPreferences settings = getPreferences(0);
    	SharedPreferences.Editor editor = settings.edit();
    	editor.putString("selection", mStation.Code);
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
    		if( !station.Clone )
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
    
    /*protected void onResume() {
        super.onResume();
    }*/
    
    public void onClick(View v) {    	
    	loadData();
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
    	
    	// Spinner
    	Station fav = null;
    	for( Station station : mStations )
    		if( station.Code.equals(code) ) {
    			station.Favorite = true;
    			fav = station.getLinkedClone();
    			break;
    		}
    	int i = 0;
    	for( Station station : mStations )
    		if( !station.Favorite || station.Name.compareTo(fav.Name) >= 0 )
    			break;
    		else
    			i++;
    	mStations.add(i, fav);
    	if( mAdapter != null ) {
    		mAdapter.notifyDataSetChanged();
    		mSpinner.setSelection(i);
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
		
		// Spinner
    	int i = 0;
    	for( Station station : mStations )
    		if( station.Code.equals(mStation.Code) )
    			break;
    		else
    			i++;
    	if( i >= mStations.size() )
    		return;
    	mStations.remove(i);
    	i = 0;
    	for( Station station : mStations )
    		if( station.Code.equals(mStation.Code) ) {
    			station.Favorite = false;
    			break;
    		} else
    			i++;
    	mAdapter.notifyDataSetChanged();
    	mSpinner.setSelection(i);
    	
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
    	String uri = mProvider.getUrl(mStation); 
    	//System.out.println(uri);
    	mDownloader = new Downloader();
    	mDownloader.execute(uri);
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
		if( mStation.isEmpty() )
			loadData();
		else
			redraw();	
	}
	
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub	
	}
	
	@SuppressLint("SimpleDateFormat")
	private void updateSummary() {
		if( mStation.isEmpty() ) {
			mSummary.setText(getString(R.string.NoData));
			return;
		}
		int i = mStation.ListDirection.size()-1;
        int dir = (Integer)mStation.ListDirection.get(i);
        int hum = -1;
        if( mStation.ListHumidity.size() > i )
        	hum = convertHumidity((Float)mStation.ListHumidity.get(i));
        float med = (Float)mStation.ListSpeedMed.get(i);
        float max = (Float)mStation.ListSpeedMax.get(i);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis((Long)mStation.ListDirection.get(i-1));
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
	
	private class Downloader extends AsyncTask<String, Void, String> {		
		@Override
	    protected void onPreExecute() {
	        super.onPreExecute();	        
	        mProgress.show();
	    }
		
		@Override
		protected void onCancelled() {
			super.onCancelled();
			//mProgress.dismiss();
		}
		
		@Override
		protected String doInBackground(String... urls) {
			StringBuilder builder = new StringBuilder();
	    	try {
	    		URL url = new URL(urls[0]);
	    		URLConnection con = url.openConnection();
	    		BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
	    		String line;
	    		while( (line=rd.readLine()) != null && !isCancelled() )
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
	        Station sel = (Station)mSpinner.getSelectedItem();
	        try {
	        	mProvider.updateStation(mStation, result);
	        	sel.replace(mStation);
	        } catch (Exception e) {
				System.out.println( e.getMessage() );
				mStation.replace(sel);
			}
	        redraw();
	    }
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
	
	private XYPlot mChartSpeed, mChartDirection;
	private Spinner mSpinner;
	private TextView mSummary;
	private CheckBox mFavorite;
	private ProgressDialog mProgress;
	Downloader mDownloader;
	private Station mStation;
	private List<Station> mStations;
	private ArrayAdapter<Station> mAdapter;
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
