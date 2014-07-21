package com.akrog.tolomet.view;

import java.util.List;

public class Graph {
	private final List<Number> data;
    private final String title;
    private final int lineColor;
    private final int pointColor;
 
    public Graph( List<Number> data, String title, int lineColor, int pointColor ) {
        this.data = data;
        this.title = title;
        this.lineColor = lineColor;
        this.pointColor = pointColor;
    }
    
    public String getTitle() {
        return this.title;
    }
 
    public int size() {
        return this.data.size()/2;
    }
 
    public long getX(int index) {
        return this.data.get(index*2).longValue();
    }
 
    public float getY(int index) {
    	return this.data.get(index*2+1).floatValue();
    }

	public int getLineColor() {
		return lineColor;
	}

	public int getPointColor() {
		return pointColor;
	}
}
