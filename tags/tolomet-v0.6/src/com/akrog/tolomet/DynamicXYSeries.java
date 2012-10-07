package com.akrog.tolomet;

import java.util.List;

import com.androidplot.series.XYSeries;

public class DynamicXYSeries implements XYSeries {
	private List<Number> mData;
    private String mTitle;
 
    public DynamicXYSeries( List<Number> data, String title ) {
        mData = data;
        mTitle = title;
    }
    
    public String getTitle() {
        return mTitle;
    }
 
    public int size() {
        return mData.size()/2;
    }
 
    public Number getX(int index) {
        return mData.get(index*2);
    }
 
    public Number getY(int index) {
    	return mData.get(index*2+1);
    }
}
