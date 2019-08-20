package com.akrog.tolomet;

import java.util.Calendar;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Measurement {
	private final Map<Long, Number> map = new TreeMap<Long, Number>();
	private Long[] times;
	private Number[] values;
	private Number cachedMinimum, cachedMaximum;
	private final Float validMinimum, validMaximum;

	public Measurement() {
		this(null,null);
	}

	public Measurement(Float validMinimum, Float validMaximum) {
		this.validMinimum = validMinimum;
		this.validMaximum = validMaximum;
	}
	
	private void clearCache() {
		times = null;
		values = null;
		cachedMinimum = null;
		cachedMaximum = null;
	}
	
	public void put(long time, Number value ) {
		if( value == null )
			return;
		if( validMinimum != null && value.floatValue() < validMinimum )
			return;
		if( validMaximum != null && value.floatValue() > validMaximum )
			return;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		//map.put(time, value);
		map.put(calendar.getTimeInMillis(), value);
		clearCache();
	}

	public Set<Map.Entry<Long,Number>> getEntrySet() {
		return map.entrySet();
	}
	
	public Long[] getTimes() {
		if( times == null )
			times = map.keySet().toArray(new Long[0]); 
		return times;
	}
	
	public Long getStamp() {
		if( isEmpty() )
			return null;
		Long[] times = getTimes();
		return times[times.length-1];
	}

	public Long getBegin() {
		if( isEmpty() )
			return null;
		Long[] times = getTimes();
		return times[0];
	}

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
		if( cachedMinimum == null ) {
			Number[] values = getValues();
			cachedMinimum = values[0];
			for(int i = 1; i < values.length; i++ )
				if( values[i].doubleValue() < cachedMinimum.doubleValue() )
					cachedMinimum = values[i];
		}
		return cachedMinimum;
	}
	
	public Number getMaximum() {
		if( isEmpty() )
			return null;
		if( cachedMaximum == null ) {
			Number[] values = getValues();
			cachedMaximum = values[0];
			for(int i = 1; i < values.length; i++ )
				if( values[i].doubleValue() > cachedMaximum.doubleValue() )
					cachedMaximum = values[i];
		}
		return cachedMaximum;
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