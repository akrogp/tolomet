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

	/*public Long getStamp(Long stamp) {
		Long result = getStamp();
		if( stamp == null || result == null )
			return result;
		long diff = Math.abs(stamp-result);
		long tmp;
		for( int i = 0; i < size(); i++ ) {
			tmp = Math.abs(stamp - getTimes()[i]);
			if( tmp < diff ) {
				diff = tmp;
				result = getTimes()[i];
			}
		}
		return result;
	}*/

	public Long getStamp(Long stamp) {
		if( stamp == null || isEmpty() )
			return null;
		Long[] data = getTimes();
		int imin = 0;
		int imax = data.length-1;
		int imid = 0;
		while( imin <= imax ) {
			imid = (imin+imax)/2;
			if( data[imid] == stamp )
				return stamp;
			if( data[imid] < stamp )
				imin = imid+1;
			else
				imax = imid-1;
		}
		int inear;
		if( data[imid] > stamp )
			inear = imid > 0 ? imid-1 : imid;
		else
			inear = imid < data.length-2 ? imid+1 : imid;
		if( inear == imid )
			return data[imid];
		return Math.abs(data[inear]-stamp) < Math.abs(data[imid]-stamp) ? data[inear] : data[imid];
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

	public Number getAt(long time) {
		return map.get(time);
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