package com.akrog.tolomet.view;

import java.util.List;

public class Graph {
	private final List<Number> data;
    private final String title;
    private final int lineColor;
    private final int pointColor;
    private final float wrap;
    private int yAxis= 0;
 
    public Graph( List<Number> data, float wrap, String title, int lineColor, int pointColor ) {
        this.data = data;
        this.wrap = wrap;
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

	public float getWrap() {
		return wrap;
	}

	public int getyAxis() {
		return yAxis;
	}

	public void setyAxis(int yAxis) {
		this.yAxis = yAxis;
	}
}
