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
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidplot.series.XYSeries;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYStepMode;

public class Tolomet extends Activity
	implements OnItemSelectedListener, View.OnClickListener {//, OnTouchListener {
    @SuppressWarnings("unchecked")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_tolomet);
        
        @SuppressWarnings({ "deprecation" })
		Map<String,Object> data = (Map<String, Object>)getLastNonConfigurationInstance();
        int sel = 0;
        if( data == null ) {
        	mMapDirection = new HashMap<String, List<Number>>();
        	mMapSpeedMed = new HashMap<String, List<Number>>();
        	mMapSpeedMax = new HashMap<String, List<Number>>();
        } else {
        	sel = (Integer)data.get("station");
        	mMapDirection = (Map<String, List<Number>>)data.get("direction");
        	mMapSpeedMax = (Map<String, List<Number>>)data.get("speedmax");
        	mMapSpeedMed =(Map<String, List<Number>>)data.get("speedmed");        	
        }
        mListDirection = new ArrayList<Number>();
    	mListSpeedMed = new ArrayList<Number>();
    	mListSpeedMax = new ArrayList<Number>();
        
        mSummary = (TextView)findViewById(R.id.textView1);
        
        mSpinner = (Spinner)findViewById(R.id.spinner1);
        mSpinner.setSelection(sel);
        mSpinner.setOnItemSelectedListener(this);        
        
        Button button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(this);                    	
        
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
    
    @Override
    public Object onRetainNonConfigurationInstance() {
    	Map<String,Object> data = new HashMap<String, Object>();
    	data.put("station", mSpinner.getSelectedItemPosition());
    	data.put("direction", mMapDirection);
    	data.put("speedmax", mMapSpeedMax);
    	data.put("speedmed", mMapSpeedMed);    	
    	return data;
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
    
    private void createCharts() {    	       
    	mChartDirection = (XYPlot)findViewById(R.id.chartDirection);
        mChartDirection.disableAllMarkup();
        mChartDirection.setTitle(getString(R.string.Direction));
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
        
        XYSeries series = new DynamicXYSeries( mListDirection, "Dir. Med." );
        LineAndPointFormatter format = new LineAndPointFormatter(
                Color.rgb(0, 0, 200),                   // line color
                Color.rgb(0, 0, 100),                   // point color
                null);                                  // fill color (none)
        mChartDirection.addSeries(series, format);
        
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
    
    /*protected void onResume() {
        super.onResume();
    }*/
    
    public void onClick(View v) {    	
    	refresh();
	}
    
    private void getStation() {
    	mStation = (String)mSpinner.getSelectedItem();
    	if( mStation == null )
    		mStation = (String)mSpinner.getItemAtPosition(0);
    	String[] fields = mStation.split(" - ");
    	mStationCode = fields[0];
    	mStationName = fields[1];
    }    
    
    private void loadData() {
    	loadStored();
    	Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    	String time1 = "00:00";
    	String time2 = String.format("%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE) );
    	if( mListDirection.size() > 2 ) {
    		Calendar last = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    		last.setTimeInMillis((Long)mListDirection.get(mListDirection.size()-2));
    		long t1 = cal.getTimeInMillis();
    		long t2 = last.getTimeInMillis();
    		long d = t1-t2;
    		d = d/1000/60;
    		if( last.get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH) ) {
    			if( (cal.getTimeInMillis()-last.getTimeInMillis()) <= 10*60*1000 ) {
    				AlertDialog alertDialog = new AlertDialog.Builder(this).create();
    				alertDialog.setMessage( getString(R.string.Impatient) );
    				alertDialog.show();
    				return;
    			}
    			last.setTimeInMillis(last.getTimeInMillis()+10*60*1000);
    			time1 = String.format("%02d:%02d", last.get(Calendar.HOUR_OF_DAY), last.get(Calendar.MINUTE) );
    		}
    	}   	
    	String uri = String.format(
    			"%s&anyo=%d&mes=%02d&dia=%02d&hora=%s%%20%s&CodigoEstacion=%s&pagina=1&R01HNoPortal=true", new Object[]{
    			"http://www.euskalmet.euskadi.net/s07-5853x/es/meteorologia/lectur_fr.apl?e=5",
    			cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH),
    			time1, time2, mStationCode
    	} );
    	//System.out.println(uri);
    	mDownloader = new Downloader();
    	mDownloader.execute(uri);
    }   
    
    private void loadStored() {
    	List<Number> list;
    	list = mMapDirection.get(mStationCode);
    	mListDirection.clear();
    	if( list != null )
    		mListDirection.addAll(list);
    	list = mMapSpeedMed.get(mStationCode);
    	mListSpeedMed.clear();
    	if( list != null )
    		mListSpeedMed.addAll(list);
    	list = mMapSpeedMax.get(mStationCode);
    	mListSpeedMax.clear();
    	if( list != null )
    		mListSpeedMax.addAll(list);
    }
    
    public void refresh() {
    	getStation();
    	loadData();
    }
    
    public void redraw() {
    	mChartDirection.redraw();
        mChartSpeed.redraw();
        updateSummary();
        updateDomainBoundaries();
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
		getStation();
		if( mMapDirection.containsKey(mStationCode) ) {
			loadStored();
			redraw();
		} else
			loadData();		
	}	
	
	private void updateSummary() {
		if( mListDirection == null || mListDirection.size() < 2 )
			return;
		int i = mListDirection.size()-1;
        int dir = (Integer)mListDirection.get(i);
        float med = (Float)mListSpeedMed.get(i);
        float max = (Float)mListSpeedMax.get(i);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis((Long)mListDirection.get(i-1));
        SimpleDateFormat df = new SimpleDateFormat();
        df.applyPattern("HH:mm");
        String date = df.format(cal.getTime());
		mSummary.setText( String.format("%s> %dº (%s), %.1f~%.1f km/h", date, dir, getDir(dir), med, max ));		
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
	        try {		        
		        String[] lines = result.split("<tr>");
		        Number date, val;
		        /*mListDirection.clear();
		        mListSpeedMed.clear();
		        mListSpeedMax.clear();*/		        
		        for( int i = 1; i < lines.length; i++ ) {
		        	String[] cells = lines[i].split("<td");
		        	if( getContent(cells[1]).equals("Med") || getContent(cells[2]).equals("-") )
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
		        redraw();
	        } catch (Exception e) {
				System.out.println( e.getMessage() );
				loadStored();
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
	private String mStation, mStationCode, mStationName;
	private Spinner mSpinner;
	private TextView mSummary;
	private ProgressDialog mProgress;
	Downloader mDownloader;
	private List<Number> mListDirection, mListSpeedMed, mListSpeedMax;
	private Map<String,List<Number>> mMapDirection, mMapSpeedMed, mMapSpeedMax;
	static final float mFontSize = 16;
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
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
