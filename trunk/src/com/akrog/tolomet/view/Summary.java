package com.akrog.tolomet.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.widget.TextView;

import com.akrog.tolomet.R;
import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.data.StationManager;

public class Summary {
	private Tolomet tolomet;
	private StationManager stations;
	private TextView summary;
	
	public Summary( Tolomet tolomet, StationManager stations ) {
		this.tolomet = tolomet;
		this.stations = stations;
		this.summary = (TextView)this.tolomet.findViewById(R.id.textView1);
	}
	
	@SuppressLint("SimpleDateFormat")
	public void update() {
		if( this.stations.current.isEmpty() ) {
			this.summary.setText(this.tolomet.getString(R.string.NoData));
			return;
		}
		
        Number[] last = new Number[2];        
        long d, d2;
        int dir;
        float med, max, h;
        int hum = -1;
        
        getLast(this.stations.current.listDirection, last);
        d = (Long)last[0];
        dir = (Integer)last[1];
        getLast(this.stations.current.listSpeedMed, last);
        med = (Float)last[1];
        getLast(this.stations.current.listSpeedMax, last);
        max = (Float)last[1];
        if( getLast(this.stations.current.listHumidity, last) ) {
        	d2 = (Long)last[0];
            h = (Float)last[1];
        	if( d2 == d )
        		hum = MyCharts.convertHumidity(h);
        }
        
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(d);
        SimpleDateFormat df = new SimpleDateFormat();
        df.applyPattern("HH:mm");
        String date = df.format(cal.getTime());
        if( hum < 0 )
        	this.summary.setText( String.format("%s | %dº (%s) | %.1f~%.1f km/h", date, dir, getDir(dir), med, max ));
        else
        	if( this.tolomet.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE )
        		this.summary.setText( String.format("%s | %dº (%s) | %d %% | %.1f~%.1f km/h", date, dir, getDir(dir), hum, med, max ));
        	else
        		this.summary.setText( String.format("%s|%dº(%s)|%d%%|%.1f~%.1f", date, dir, getDir(dir), hum, med, max ));
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
	
	private boolean getLast( List<Number> list, Number[] vals ) {
		int len =  list.size();
		if( len < 2 || vals.length < 2 )
			return false;		
		vals[0] = list.get(len-2);
		vals[1] = list.get(len-1);		
		return true;
	}
}
