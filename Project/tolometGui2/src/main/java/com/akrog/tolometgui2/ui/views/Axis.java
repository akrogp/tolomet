package com.akrog.tolometgui2.ui.views;

import java.util.HashSet;
import java.util.Set;

public class Axis {
	public interface ChangeListener {
		void onNewLimit(Number value);
	}

	public Axis( PlotYY plot ) {
		this.plot = plot;
	}
	
	public void connect( Axis axis ) {
		if( axis == this )
			return;
		syncAxis.add(axis);
	}

	public void addMaxListener( ChangeListener listener ) {
		maxListeners.add(listener);
	}
	
	public boolean setRange( Number min, Number max ) {		
		if( updating )
			return false;
		zoomed = false;
		updating = true;		
		setMin(min);
		setMax(max);
		for( Axis axis : syncAxis ) {
			if( !zoomed )
				axis.setRange(min, max);
			else
				axis.zoom(min, max);
			plot.redraw();
		}
		updating = false;
		return true;
	}
	
	public boolean zoom( Number from, Number to ) {		
		boolean result = setRange(from, to);
		if( result )
			zoomed = true;
		return result;
	}
	
	public Number getMin() {
		return min;
	}
	
	public void setMin(Number min) {
		this.min = min;
	}
	
	public Number getMax() {
		return max;
	}
	
	public void setMax(Number max) {
		this.max = max;
		for( ChangeListener listener : maxListeners )
			listener.onNewLimit(max);
	}
	
	public void setLimits( Number minLimit, Number maxLimit ) {
		setMinLimit(minLimit);
		setMaxLimit(maxLimit);		
	}
	
	public Number getMinLimit() {
		if( minLimit == null )
			minLimit = min;
		return minLimit;
	}
	
	public void setMinLimit(Number minLimit) {
		if( min.doubleValue() < minLimit.doubleValue() )
			this.minLimit = min;
		else
			this.minLimit = minLimit;
		zoomed = false;
	}
	
	public Number getMaxLimit() {
		if( maxLimit == null )
			maxLimit = max;
		return maxLimit;
	}
	
	public void setMaxLimit(Number maxLimit) {
		if( max.doubleValue() > maxLimit.doubleValue() )
			this.maxLimit = max;
		else
			this.maxLimit = maxLimit;
		zoomed = false;
	}
	
	public boolean isZommed() {
		return zoomed;
	}
	
	public Number getWrap() {
		return wrap;
	}
	
	public Number getWrap2() {
		return wrap2;
	}
	
	public void setWrap(Number wrap) {
		this.wrap = wrap;
		wrap2 = wrap.floatValue()/2;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public int getSteps() {
		return steps;
	}
	
	public void setSteps(int steps) {
		this.steps = steps;
	}
	
	public int getTicksPerStep() {
		return ticksPerStep;
	}
	
	public void setTicksPerStep(int ticksPerStep) {
		this.ticksPerStep = ticksPerStep;
	}
	
	public float scale(Number val, float range) {
		double value = val.doubleValue();
		double num = val.doubleValue()-min.doubleValue();
		double den;
		if( wrap == null || value < 0 || value > wrap.doubleValue() ) {
			den = max.doubleValue()-min.doubleValue();
		} else {
			if( num < 0 )
				num += wrap.doubleValue();
			den = wrap.doubleValue();
		}
		return (float)((num/den)*(range-1));
	}
	
	public float get( Number val ) {
		float num = val.floatValue();
		if( wrap == null )
			return num;
		float max = this.max.floatValue();
		float min = this.min.floatValue();
		float wrap = this.wrap.floatValue();
		while( num > max )
			num-=wrap;
		while( num < min )
			num+=wrap;
		return num;
	}
	
	private Number min = 0, minLimit;
	private Number max = 10, maxLimit;
	private boolean updating = false;
	private boolean zoomed = false;
	private Number wrap;
	private Number wrap2;
	private String label = "label";
	private int steps = 10;
	private int ticksPerStep = 1;
	private final Set<Axis> syncAxis = new HashSet<Axis>();
	private final Set<ChangeListener> maxListeners = new HashSet<ChangeListener>();
	private final PlotYY plot;
}
