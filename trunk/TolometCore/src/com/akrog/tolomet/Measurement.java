package com.akrog.tolomet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Measurement {
	private final Map<Long, Number> map = new LinkedHashMap<Long, Number>();
	private Long[] times;
	private Number[] values;
	
	private void clearCache() {
		times = null;
		values = null;
	}
	
	public void put(long time, Number value ) {
		map.put(time, value);
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
	
	public void merge(Measurement data) {
		map.putAll(data.map);
		clearCache();
	}
	
	public void sort() {
		Set<Entry<Long,Number>> set = map.entrySet();
		List<Entry<Long,Number>> list = new ArrayList<Map.Entry<Long,Number>>();
		list.addAll(set);
		Collections.sort(list, new Comparator<Entry<Long,Number>>() {
			@Override
			public int compare(Entry<Long, Number> o1, Entry<Long, Number> o2) {
				return (int)(o1.getKey() - o2.getKey());
			}
		});
		map.clear();
		for( Entry<Long,Number> entry : list )
			map.put(entry.getKey(), entry.getValue());
		clearCache();
	}
}