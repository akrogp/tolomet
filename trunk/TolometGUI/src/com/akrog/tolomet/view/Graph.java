package com.akrog.tolomet.view;

import com.akrog.tolomet.Measurement;

public class Graph {
	private final Measurement data;
    private final String title;
    private final int lineColor;
    private final int pointColor;
    private final float wrap;
    private int yAxis= 0;
 
    public Graph( Measurement data, float wrap, String title, int lineColor, int pointColor ) {
        this.data = data;
        this.wrap = wrap;
        this.title = title;
        this.lineColor = lineColor;
        this.pointColor = pointColor;
    }
    
    public String getTitle() {
        return title;
    }
 
    public int size() {
        return data.size();
    }
 
    public long getX(int index) {
        return data.getTimes()[index];
    }
 
    public float getY(int index) {
    	return data.getValues()[index].floatValue();
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