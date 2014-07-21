package com.akrog.tolomet.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

@SuppressLint("ClickableViewAccessibility")
public class MyPlot extends PlotYY implements OnTouchListener {
	private static final float MIN_DIST_2_FING = 5f;

	// Definition of the touch states
	private enum State {
		NONE,
		ONE_FINGER_DRAG,
		TWO_FINGERS_DRAG
	}
	
	private enum Axes {
		X, Y1, Y2
	}

	private State mode = State.NONE;
	private float minXLimit = Float.MAX_VALUE;
	private float maxXLimit = Float.MAX_VALUE;
	private float minY1Limit = Float.MAX_VALUE;
	private float maxY1Limit = Float.MAX_VALUE;
	private float minY2Limit = Float.MAX_VALUE;
	private float maxY2Limit = Float.MAX_VALUE;
	private float lastMinX = Float.MAX_VALUE;
	private float lastMaxX = Float.MAX_VALUE;
	private float lastMinY1 = Float.MAX_VALUE;
	private float lastMaxY1 = Float.MAX_VALUE;
	private float lastMinY2 = Float.MAX_VALUE;
	private float lastMaxY2 = Float.MAX_VALUE;
	private PointF firstFingerPos;
	private float mDistX;
	private boolean mZoomEnabled; //default is enabled
	private boolean mZoomVertically;
	private boolean mZoomHorizontally;
	private boolean mZoomed = false;
	private List<PlotYY> mDomainPlots = new ArrayList<PlotYY>();
	private List<PlotYY> mRangePlots = new ArrayList<PlotYY>();
	
	private void init() {
		setZoomEnabled(true);
		mZoomHorizontally = true;
		mZoomVertically = true;
	}

	public MyPlot(Context context) {
		super(context);
		init();		
	}

	public MyPlot(Context context, AttributeSet attrs) {
		super(context,attrs);
		init();
	}

	public MyPlot(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context,attrs,defStyleAttr);
		init();
	}

	@Override
	public void setOnTouchListener(OnTouchListener l) {
		if(l != this)
			mZoomEnabled = false;
		super.setOnTouchListener(l);
	}

	public boolean getZoomVertically() {
		return mZoomVertically;
	}

	public void setZoomVertically(boolean zoomVertically) {
		mZoomVertically = zoomVertically;
	}

	public boolean getZoomHorizontally() {
		return mZoomHorizontally;
	}

	public void setZoomHorizontally(boolean zoomHorizontally) {
		mZoomHorizontally = zoomHorizontally;
	}

	public void setZoomEnabled(boolean enabled) {
		if(enabled) {
			setOnTouchListener(this);
		} else {
			setOnTouchListener(null);
		}
		mZoomEnabled = enabled;
	}

	public boolean getZoomEnabled() {
		return mZoomEnabled;
	}

	public boolean getZoomed() {
		return mZoomed;
	}

	private float getMinXLimit() {
		if(minXLimit == Float.MAX_VALUE) {
			minXLimit = getMinX();
			lastMinX = minXLimit;
		}
		return minXLimit;
	}

	private float getMaxXLimit() {
		if(maxXLimit == Float.MAX_VALUE) {
			maxXLimit = getMaxX();
			lastMaxX = maxXLimit;
		}
		return maxXLimit;
	}

	private float getMinY1Limit() {
		if(minY1Limit == Float.MAX_VALUE) {
			minY1Limit = getMinY1();
			lastMinY1 = minY1Limit;
		}
		return minY1Limit;
	}

	private float getMaxY1Limit() {
		if(maxY1Limit == Float.MAX_VALUE) {
			maxY1Limit = getMaxY1();
			lastMaxY1 = maxY1Limit;
		}
		return maxY1Limit;
	}
	
	private float getMinY2Limit() {
		if(minY2Limit == Float.MAX_VALUE) {
			minY2Limit = getMinY2();
			lastMinY2 = minY2Limit;
		}
		return minY2Limit;
	}

	private float getMaxY2Limit() {
		if(maxY2Limit == Float.MAX_VALUE) {
			maxY2Limit = getMaxY2();
			lastMaxY2 = maxY2Limit;
		}
		return maxY2Limit;
	}

	private float getLastMinX() {
		if(lastMinX == Float.MAX_VALUE)
			lastMinX = getMinX();
		return lastMinX;
	}

	private float getLastMaxX() {
		if(lastMaxX == Float.MAX_VALUE)
			lastMaxX = getMaxX();
		return lastMaxX;
	}

	private float getLastMinY1() {
		if(lastMinY1 == Float.MAX_VALUE)
			lastMinY1 = getMinY1();
		return lastMinY1;
	}

	private float getLastMaxY1() {
		if(lastMaxY1 == Float.MAX_VALUE)
			lastMaxY1 = getMaxY1();
		return lastMaxY1;
	}
	
	private float getLastMinY2() {
		if(lastMinY2 == Float.MAX_VALUE)
			lastMinY2 = getMinY2();
		return lastMinY2;
	}

	private float getLastMaxY2() {
		if(lastMaxY2 == Float.MAX_VALUE)
			lastMaxY2 = getMaxY2();
		return lastMaxY2;
	}

	@Override
	public void setXRange(float minX, float maxX) {
		super.setXRange(minX, maxX);
		lastMinX = minX;
		lastMaxX = maxX;
		for( PlotYY plot : mDomainPlots ) {
			plot.setXRange(minX, maxX);
			plot.redraw();
		}
	};
	
	@Override
	public void setY1Range(float minY, float maxY) {
		super.setY1Range(minY, maxY);
		lastMinY1 = minY;
		lastMaxY1 = maxY;
		for( PlotYY plot : mDomainPlots ) {
			plot.setY1Range(minY, maxY);
			plot.redraw();
		}
	}
	
	@Override
	public void setY2Range(float minY, float maxY) {
		super.setY2Range(minY, maxY);
		lastMinY2 = minY;
		lastMaxY2 = maxY;
		for( PlotYY plot : mDomainPlots ) {
			plot.setY2Range(minY, maxY);
			plot.redraw();
		}
	}

	public void setXZoomLimits( long lowerBoundary, long upperBoundary ) {
		minXLimit = lowerBoundary;
		maxXLimit = upperBoundary;
		if( getMinX() < minXLimit )
			minXLimit = getMinX();
		if( getMaxX() > maxXLimit )
			maxXLimit = getMaxX();
		lastMinX = Float.MAX_VALUE;
		lastMaxX = Float.MAX_VALUE;
		mZoomed = false;
	}

	public void setY1ZoomLimits( int lowerBoundary, int upperBoundary ) {
		minY1Limit = lowerBoundary;
		maxY1Limit = upperBoundary;
		if( getMinY1() < minY1Limit )
			minY1Limit = getMinY1();
		if( getMaxY1() > maxY1Limit )
			maxY1Limit = getMaxY1();
		lastMinY1 = Float.MAX_VALUE;
		lastMaxY1 = Float.MAX_VALUE;
		mZoomed = false;
	}
	
	public void setY2ZoomLimits( int lowerBoundary, int upperBoundary ) {
		minY2Limit = lowerBoundary;
		maxY2Limit = upperBoundary;
		if( getMinY2() < minY2Limit )
			minY2Limit = getMinY2();
		if( getMaxY2() > maxY2Limit )
			maxY2Limit = getMaxY2();
		lastMinY2 = Float.MAX_VALUE;
		lastMaxY2 = Float.MAX_VALUE;
		mZoomed = false;
	}

	public void connectDomains(PlotYY plot) {
		if( this != plot && !mDomainPlots.contains(plot) )
			mDomainPlots.add(plot);
	}

	public void connectRanges(PlotYY plot) {
		if( this != plot && !mRangePlots.contains(plot) )
			mRangePlots.add(plot);
	}

	public boolean onTouch(final View view, final MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN: // start gesture
				firstFingerPos = new PointF(event.getX(), event.getY());
				mode = State.ONE_FINGER_DRAG;
				break;            
			case MotionEvent.ACTION_POINTER_DOWN: // second finger
				mDistX = getXDistance(event);
				// the distance check is done to avoid false alarms
				if(mDistX > MIN_DIST_2_FING || mDistX < -MIN_DIST_2_FING)
					mode = State.TWO_FINGERS_DRAG;
				break;
			case MotionEvent.ACTION_POINTER_UP: // end zoom
				mode = State.NONE;                
				break;
			case MotionEvent.ACTION_MOVE:
				mZoomed = true;
				if(mode == State.ONE_FINGER_DRAG)
					pan(event);
				else if(mode == State.TWO_FINGERS_DRAG)
					zoom(event);
				break;
		}
		return true;
	}

	private float getXDistance(final MotionEvent event) {
		return event.getX(0) - event.getX(1);
	}

	private void pan(final MotionEvent motionEvent) {
		final PointF oldFirstFinger = firstFingerPos; //save old position of finger
		firstFingerPos = new PointF(motionEvent.getX(), motionEvent.getY()); //update finger position
		PointF newX = new PointF();
		if(mZoomHorizontally) {
			calculatePan(oldFirstFinger, newX, Axes.X);
			setXRange(newX.x, newX.y);
		}
		if(mZoomVertically) {
			calculatePan(oldFirstFinger, newX, Axes.Y1);
			setY1Range(newX.x, newX.y);
			calculatePan(oldFirstFinger, newX, Axes.Y2);
			setY2Range(newX.x, newX.y);
		}
		redraw();
	}

	private void calculatePan(final PointF oldFirstFinger, PointF newX, Axes axe) {
		final float offset;
		// multiply the absolute finger movement for a factor.
		// the factor is dependent on the calculated min and max
		if(axe==Axes.X) {
			newX.x = getLastMinX();
			newX.y = getLastMaxX();
			offset = (oldFirstFinger.x - firstFingerPos.x) * ((newX.y - newX.x) / getWidth());
		} else if(axe==Axes.Y1){
			newX.x = getLastMinY1();
			newX.y = getLastMaxY1();
			offset = -(oldFirstFinger.y - firstFingerPos.y) * ((newX.y - newX.x) / getHeight());
		} else{
			newX.x = getLastMinY2();
			newX.y = getLastMaxY2();
			offset = -(oldFirstFinger.y - firstFingerPos.y) * ((newX.y - newX.x) / getHeight());
		}
		// move the calculated offset
		newX.x = newX.x + offset;
		newX.y = newX.y + offset;
		//get the distance between max and min
		final float diff = newX.y - newX.x;
		//check if we reached the limit of panning
		float minLimit, maxLimit;
		if(axe==Axes.X) {
			minLimit = getMinXLimit();
			maxLimit = getMaxXLimit();
		} else if (axe==Axes.Y1){
			minLimit = getMinY1Limit();
			maxLimit = getMaxY1Limit();
		} else {
			minLimit = getMinY2Limit();
			maxLimit = getMaxY2Limit();
		}
		if(newX.x < minLimit) {
			newX.x = minLimit;
			newX.y = newX.x + diff;
		}
		if(newX.y > maxLimit) {
			newX.y = maxLimit;
			newX.x = newX.y - diff;
		}
	}

	private void zoom(final MotionEvent motionEvent) {
		final float oldDist = mDistX;
		final float newDist = getXDistance(motionEvent);
		// sign change! Fingers have crossed ;-)
		if(oldDist > 0 && newDist < 0 || oldDist < 0 && newDist > 0) {
			return;
		}
		mDistX = newDist;
		float scale = (oldDist / mDistX);
		// sanity check
		if(Float.isInfinite(scale) || Float.isNaN(scale) || scale > -0.001 && scale < 0.001) {
			return;
		}
		PointF newX = new PointF();
		if(mZoomHorizontally) {
			calculateZoom(scale, newX, Axes.X);
			setXRange(newX.x, newX.y);
		}
		if(mZoomVertically) {
			calculateZoom(scale, newX, Axes.Y1);
			setY1Range(newX.x, newX.y);
			calculateZoom(scale, newX, Axes.Y2);
			setY2Range(newX.x, newX.y);
		}
		redraw();
	}

	private void calculateZoom(float scale, PointF newX, Axes axe) {
		final float calcMax;
		final float span;
		if(axe==Axes.X) {
			calcMax = getLastMaxX();
			span = calcMax - getLastMinX();
		} else if(axe==Axes.Y1){
			calcMax = getLastMaxY1();
			span = calcMax - getLastMinY1();
		} else {
			calcMax = getLastMaxY2();
			span = calcMax - getLastMinY2();
		}
		final float midPoint = calcMax - (span / 2.0f);
		final float offset = span * scale / 2.0f;
		newX.x = midPoint - offset;
		newX.y = midPoint + offset;
		float minLimit, maxLimit;
		if(axe==Axes.X) {
			minLimit = getMinXLimit();
			maxLimit = getMaxXLimit();
		} else if(axe==Axes.Y1){
			minLimit = getMinY1Limit();
			maxLimit = getMaxY1Limit();
		} else {
			minLimit = getMinY2Limit();
			maxLimit = getMaxY2Limit();
		}
		if(newX.x < minLimit)
			newX.x = minLimit;
		if(newX.y > maxLimit)
			newX.y = maxLimit;
	}	
}
