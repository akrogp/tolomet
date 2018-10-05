package com.akrog.tolomet.ui.view;

import com.akrog.tolomet.Measurement;

public class Graph {
	private final Measurement data;
    private final String title;
    private int lineColor;
    private int pointColor;
    private final float wrap;
    private final float wrap2;
    private int yAxis= 0;
    private float yFactor = 1.0F;

	public Graph( Measurement data, float wrap, String title ) {
		this(data, wrap, title, 0, 0);
	}
 
    public Graph( Measurement data, float wrap, String title, int lineColor, int pointColor ) {
        this.data = data;
        this.wrap = wrap;
        wrap2 = wrap/2;
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
    
    public long[] getX() {
    	Long[] stamps = data.getTimes();
    	long[] x = new long[data.size()];
    	for( int i = 0; i < x.length; i++ )
    		x[i] = stamps[i];
    	return x;
    }
 
    public float getY(int index) {
    	return data.getValues()[index].floatValue()*yFactor;
    }
    
    public float[] getY() {
    	Number[] values = data.getValues();
    	float[] y = new float[data.size()];
    	for( int i = 0; i < y.length; i++ )
    		y[i] = values[i].floatValue()*yFactor;
    	return y;
    }

    public void setColors( int lineColor, int pointColor ) {
        setLineColor(lineColor);
        setPointColor(pointColor);
    }

    public void setLineColor( int lineColor ) {
        this.lineColor = lineColor;
    }

	public int getLineColor() {
		return lineColor;
	}

	public void setPointColor( int pointColor ) {
        this.pointColor = pointColor;
    }

    public int getPointColor() {
		return pointColor;
	}

	public float getWrap() {
		return wrap;
	}
	
	public float getWrap2() {
		return wrap2;
	}

	public int getyAxis() {
		return yAxis;
	}

	public void setyAxis(int yAxis) {
		this.yAxis = yAxis;
	}

    public float getyFactor() {
        return yFactor;
    }

    public void setyFactor(float yFactor) {
        this.yFactor = yFactor;
    }
}