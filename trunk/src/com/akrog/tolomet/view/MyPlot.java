package com.akrog.tolomet.view;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.XYPlot;

public class MyPlot extends XYPlot implements OnTouchListener {	
	private static final float MIN_DIST_2_FING = 5f;

    // Definition of the touch states
    private enum State
    {
        NONE,
        ONE_FINGER_DRAG,
        TWO_FINGERS_DRAG
    }

    private State mode = State.NONE;
    private float minXLimit = Float.MAX_VALUE;
    private float maxXLimit = Float.MAX_VALUE;
    private float minYLimit = Float.MAX_VALUE;
    private float maxYLimit = Float.MAX_VALUE;
    private float lastMinX = Float.MAX_VALUE;
    private float lastMaxX = Float.MAX_VALUE;
    private float lastMinY = Float.MAX_VALUE;
    private float lastMaxY = Float.MAX_VALUE;
    private PointF firstFingerPos;
    private float mDistX;
    private boolean mZoomEnabled; //default is enabled
    private boolean mZoomVertically;
    private boolean mZoomHorizontally;
    private boolean mZoomEnabledInit;
    private boolean mZoomVerticallyInit;
    private boolean mZoomHorizontallyInit;
    private boolean mZoomed = false;

    public MyPlot(Context context, String title, RenderMode mode) {
        super(context, title, mode);
        setZoomEnabled(true); //Default is ZoomEnabled if instantiated programmatically
    }

    public MyPlot(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        if(mZoomEnabled || !mZoomEnabledInit) {
            setZoomEnabled(true);
        }
        if(!mZoomHorizontallyInit) {
            mZoomHorizontally = true;
        }
        if(!mZoomVerticallyInit) {
            mZoomVertically = true;
        }
    }

    public MyPlot(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        if(mZoomEnabled || !mZoomEnabledInit) {
            setZoomEnabled(true);
        }
        if(!mZoomHorizontallyInit) {
            mZoomHorizontally = true;
        }
        if(!mZoomVerticallyInit) {
            mZoomVertically = true;
        }
    }

    public MyPlot(final Context context, final String title) {
        super(context, title);
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        if(l != this) {
            mZoomEnabled = false;
        }
        super.setOnTouchListener(l);
    }

    public boolean getZoomVertically() {
        return mZoomVertically;
    }

    public void setZoomVertically(boolean zoomVertically) {
        mZoomVertically = zoomVertically;
        mZoomVerticallyInit = true;
    }

    public boolean getZoomHorizontally() {
        return mZoomHorizontally;
    }

    public void setZoomHorizontally(boolean zoomHorizontally) {
        mZoomHorizontally = zoomHorizontally;
        mZoomHorizontallyInit = true;
    }

    public void setZoomEnabled(boolean enabled) {
        if(enabled) {
            setOnTouchListener(this);
        } else {
            setOnTouchListener(null);
        }
        mZoomEnabled = enabled;
        mZoomEnabledInit = true;
    }

    public boolean getZoomEnabled() {
        return mZoomEnabled;
    }
    
    public boolean getZoomed() {
        return mZoomed;
    }

    private float getMinXLimit() {
        if(minXLimit == Float.MAX_VALUE) {
            minXLimit = getCalculatedMinX().floatValue();
            lastMinX = minXLimit;
        }
        return minXLimit;
    }

    private float getMaxXLimit() {
        if(maxXLimit == Float.MAX_VALUE) {
            maxXLimit = getCalculatedMaxX().floatValue();
            lastMaxX = maxXLimit;
        }
        return maxXLimit;
    }

    private float getMinYLimit() {
        if(minYLimit == Float.MAX_VALUE) {
            minYLimit = getCalculatedMinY().floatValue();
            lastMinY = minYLimit;
        }
        return minYLimit;
    }

    private float getMaxYLimit() {
        if(maxYLimit == Float.MAX_VALUE) {
            maxYLimit = getCalculatedMaxY().floatValue();
            lastMaxY = maxYLimit;
        }
        return maxYLimit;
    }

    private float getLastMinX() {
        if(lastMinX == Float.MAX_VALUE) {
            lastMinX = getCalculatedMinX().floatValue();
        }
        return lastMinX;
    }

    private float getLastMaxX() {
        if(lastMaxX == Float.MAX_VALUE) {
            lastMaxX = getCalculatedMaxX().floatValue();
        }
        return lastMaxX;
    }

    private float getLastMinY() {
        if(lastMinY == Float.MAX_VALUE) {
            lastMinY = getCalculatedMinY().floatValue();
        }
        return lastMinY;
    }

    private float getLastMaxY() {
        if(lastMaxY == Float.MAX_VALUE) {
            lastMaxY = getCalculatedMaxY().floatValue();
        }
        return lastMaxY;
    }
    
    public void setDomainZoomLimits( final Number lowerBoundary, final Number upperBoundary ) {
    	minXLimit = lowerBoundary.floatValue();
    	maxXLimit = upperBoundary.floatValue();
    	if( getCalculatedMinX().floatValue() < minXLimit )
    		minXLimit = getCalculatedMinX().floatValue();
    	if( getCalculatedMaxX().floatValue() > maxXLimit )
    		maxXLimit = getCalculatedMaxX().floatValue();
    	lastMinX = Float.MAX_VALUE;
        lastMaxX = Float.MAX_VALUE;
        mZoomed = false;
    }
    
    public void setRangeZoomLimits( final Number lowerBoundary, final Number upperBoundary ) {
    	minYLimit = lowerBoundary.floatValue();
    	maxYLimit = upperBoundary.floatValue();
    	if( getCalculatedMinY().floatValue() < minYLimit )
    		minYLimit = getCalculatedMinY().floatValue();
    	if( getCalculatedMaxY().floatValue() > maxYLimit )
    		maxYLimit = getCalculatedMaxY().floatValue();
    	lastMinY = Float.MAX_VALUE;
        lastMaxY = Float.MAX_VALUE;
        mZoomed = false;
    }
    
    public boolean onTouch(final View view, final MotionEvent event) {		
        switch (event.getAction() & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_DOWN: // start gesture
                firstFingerPos = new PointF(event.getX(), event.getY());
                mode = State.ONE_FINGER_DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN: // second finger
            {
                mDistX = getXDistance(event);
                // the distance check is done to avoid false alarms
                if(mDistX > MIN_DIST_2_FING || mDistX < -MIN_DIST_2_FING) {
                    mode = State.TWO_FINGERS_DRAG;
                }
                break;
            }
            case MotionEvent.ACTION_POINTER_UP: // end zoom
                mode = State.NONE;
                break;
            case MotionEvent.ACTION_MOVE:
            	mZoomed = true;
                if(mode == State.ONE_FINGER_DRAG) {
                    pan(event);
                } else if(mode == State.TWO_FINGERS_DRAG) {
                    zoom(event);
                }
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
            calculatePan(oldFirstFinger, newX, true);
            setDomainBoundaries(newX.x, newX.y, BoundaryMode.FIXED);
            lastMinX = newX.x;
            lastMaxX = newX.y;
        }
        if(mZoomVertically) {
            calculatePan(oldFirstFinger, newX, false);
            setRangeBoundaries(newX.x, newX.y, BoundaryMode.FIXED);            
            lastMinY = newX.x;
            lastMaxY = newX.y;
        }
        redraw();
    }

    private void calculatePan(final PointF oldFirstFinger, PointF newX, final boolean horizontal) {
        final float offset;
        // multiply the absolute finger movement for a factor.
        // the factor is dependent on the calculated min and max
        if(horizontal) {
            newX.x = getLastMinX();
            newX.y = getLastMaxX();
            offset = (oldFirstFinger.x - firstFingerPos.x) * ((newX.y - newX.x) / getWidth());
        } else {
            newX.x = getLastMinY();
            newX.y = getLastMaxY();
            offset = -(oldFirstFinger.y - firstFingerPos.y) * ((newX.y - newX.x) / getHeight());
        }
        // move the calculated offset
        newX.x = newX.x + offset;
        newX.y = newX.y + offset;
        //get the distance between max and min
        final float diff = newX.y - newX.x;
        //check if we reached the limit of panning
        if(horizontal) {
            if(newX.x < getMinXLimit()) {
                newX.x = getMinXLimit();
                newX.y = newX.x + diff;
            }
            if(newX.y > getMaxXLimit()) {
                newX.y = getMaxXLimit();
                newX.x = newX.y - diff;
            }
        } else {
            if(newX.x < getMinYLimit()) {
                newX.x = getMinYLimit();
                newX.y = newX.x + diff;
            }
            if(newX.y > getMaxYLimit()) {
                newX.y = getMaxYLimit();
                newX.x = newX.y - diff;
            }
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
            calculateZoom(scale, newX, true);
            setDomainBoundaries(newX.x, newX.y, BoundaryMode.FIXED);
            lastMinX = newX.x;
            lastMaxX = newX.y;
        }
        if(mZoomVertically) {
            calculateZoom(scale, newX, false);
            setRangeBoundaries(newX.x, newX.y, BoundaryMode.FIXED);
            lastMinY = newX.x;
            lastMaxY = newX.y;
        }
        redraw();
    }

    private void calculateZoom(float scale, PointF newX, final boolean horizontal) {
        final float calcMax;
        final float span;
        if(horizontal) {
            calcMax = getLastMaxX();
            span = calcMax - getLastMinX();
        } else {
            calcMax = getLastMaxY();
            span = calcMax - getLastMinY();
        }
        final float midPoint = calcMax - (span / 2.0f);
        final float offset = span * scale / 2.0f;
        newX.x = midPoint - offset;
        newX.y = midPoint + offset;
        if(horizontal) {
            if(newX.x < getMinXLimit()) {
                newX.x = getMinXLimit();
            }
            if(newX.y > getMaxXLimit()) {
                newX.y = getMaxXLimit();
            }
        } else {
            if(newX.x < getMinYLimit()) {
                newX.x = getMinYLimit();
            }
            if(newX.y > getMaxYLimit()) {
                newX.y = getMaxYLimit();
            }
        }
    }
}