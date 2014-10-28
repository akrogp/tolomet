package com.akrog.tolomet;

import java.util.LinkedHashMap;
import java.util.Map;

public class Data {
	private final Map<Long, Number> map = new LinkedHashMap<Long, Number>();
	
	public void put(long time, Number value ) {
		map.put(time, value);
	}
	
	public Long[] getTimes() {
		return map.keySet().toArray(new Long[0]);
	}
	
	public Long getStamp() {
		if( isEmpty() )
			return null;
		return getTimes()[size()-1];
	}
	
	public Number[] getValues() {
		return map.values().toArray(new Number[0]);
	}
	
	public int size() {
		return map.size();
	}
	
	public void clear() {
		map.clear();
	}
	
	public boolean isEmpty() {
		return map.isEmpty();
	}
	
	public void merge(Data data) {
		map.putAll(data.map);
	}
}