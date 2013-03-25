package com.akrog.tolomet.view;

import java.util.List;

import com.androidplot.series.XYSeries;

public class DynamicXYSeries implements XYSeries {
	private List<Number> data;
    private String title;
 
    public DynamicXYSeries( List<Number> data, String title ) {
        this.data = data;
        this.title = title;
    }
    
    public String getTitle() {
        return this.title;
    }
 
    public int size() {
        return this.data.size()/2;
    }
 
    public Number getX(int index) {
        return this.data.get(index*2);
    }
 
    public Number getY(int index) {
    	return this.data.get(index*2+1);
    }
}
