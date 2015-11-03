package com.akrog.tolomet;

import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

public class Measurement {
	private final Map<Long, Number> map = new TreeMap<Long, Number>();
	private Long[] times;
	private Number[] values;
	private Number minimum;
	private Number maximum;
	
	private void clearCache() {
		times = null;
		values = null;
		minimum = null;
		maximum = null;
	}
	
	public void put(long time, Number value ) {
		if( value == null )
			return;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		//map.put(time, value);
		map.put(calendar.getTimeInMillis(), value);
		clearCache();
	}
	
	public Long[] getTimes() {
		if( times == null )
			times = map.keySet().toArray(new Long[0]); 
		return times;
	}
	
	public Long getStamp() {
		if( isEmpty() )
			return null;
		return getTimes()[size()-1];
	}
	
	public Number getFirst() {
		if( isEmpty() )
			return null;
		return getValues()[0];
	}
	
	public Number getLast() {
		if( isEmpty() )
			return null;
		return getValues()[size()-1];
	}
	
	public Number[] getValues() {
		if( values == null )
			values = map.values().toArray(new Number[0]); 
		return values;
	}
	
	public Number getMinimum() {
		if( isEmpty() )
			return null;
		if( minimum == null ) {
			Number[] values = getValues();
			minimum = values[0];
			for(int i = 1; i < values.length; i++ )
				if( values[i].doubleValue() < minimum.doubleValue() )
					minimum = values[i];
		}
		return minimum;		
	}
	
	public Number getMaximum() {
		if( isEmpty() )
			return null;
		if( maximum == null ) {
			Number[] values = getValues();
			maximum = values[0];
			for(int i = 1; i < values.length; i++ )
				if( values[i].doubleValue() > maximum.doubleValue() )
					maximum = values[i];
		}
		return maximum;
	}
	
	public int size() {
		return map.size();
	}
	
	public void clear() {
		map.clear();
		clearCache();
	}
	
	public void clear( long fromStamp ) {
		for( Long stamp : getTimes() )
			if( stamp < fromStamp )
				map.remove(stamp);
		clearCache();
	}
	
	public boolean isEmpty() {
		return map.isEmpty();
	}
	
	public synchronized void merge(Measurement data) {
		map.putAll(data.map);
		clearCache();
	}
	
	public Integer getStep() {
		if( size() < 2 )
			return null;
		Long[] times = getTimes();
		return (int)Math.round((times[1]-times[0])/1000.0/60.0);
	}
}