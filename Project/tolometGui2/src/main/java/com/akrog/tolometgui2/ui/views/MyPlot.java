package com.akrog.tolometgui2.ui.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import java.util.HashSet;
import java.util.Set;

@SuppressLint("ClickableViewAccessibility")
public class MyPlot extends PlotYY implements OnTouchListener {
	private static final float MIN_DIST_2_FING = 5f;

	public interface BoundaryListener {
		public void onBoundaryReached(long requestedDate);
	}

	// Definition of the touch states
	private enum State {
		NONE,
		ONE_FINGER_DRAG,
		TWO_FINGERS_DRAG
	}

	private State mode = State.NONE;
	private PointF firstFingerPos;
	private float mDistX;
	private boolean mZoomEnabled; //default is enabled
	private boolean mZoomVertically;
	private boolean mZoomHorizontally;
	private Set<BoundaryListener> boundaryListeners;
	
	private void init() {
		setZoomEnabled(true);
		mZoomHorizontally = true;
		mZoomVertically = false;
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

	public void addBoundaryListener( BoundaryListener boundaryListener ) {
		if( boundaryListeners == null )
			boundaryListeners = new HashSet<>();
		boundaryListeners.add(boundaryListener);
	}

    private void notifyBoundary( long date ) {
        if( boundaryListeners != null )
            for( BoundaryListener boundaryListener : boundaryListeners )
                boundaryListener.onBoundaryReached(date);
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
		return getXAxis().isZommed();
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
		if(mZoomHorizontally) {
			PointL newX = new PointL();
			calculatePan(oldFirstFinger, newX);
			getXAxis().zoom(newX.x, newX.y);
		}
		if(mZoomVertically) {
			PointF newX = new PointF();
			for( Axis axis : getYAxes() ) {
				calculatePan(oldFirstFinger, newX, axis);
				axis.zoom(newX.x, newX.y);
			}
		}
		redraw();
	}

	private void calculatePan(final PointF oldFirstFinger, PointF newX, Axis axis) {
		final float offset;
		// multiply the absolute finger movement for a factor.
		// the factor is dependent on the calculated min and max
		newX.x = axis.getMin().floatValue();
		newX.y = axis.getMax().floatValue();
		offset = -(oldFirstFinger.y - firstFingerPos.y) * ((newX.y - newX.x) / getHeight());		
		// move the calculated offset
		newX.x = newX.x + offset;
		newX.y = newX.y + offset;
		//get the distance between max and min
		final float diff = newX.y - newX.x;
		//check if we reached the limit of panning
		float minLimit = axis.getMinLimit().floatValue();
		float maxLimit = axis.getMaxLimit().floatValue();
		if(newX.x < minLimit) {
			newX.x = minLimit;
			newX.y = newX.x + diff;
		}
		if(newX.y > maxLimit) {
			newX.y = maxLimit;
			newX.x = newX.y - diff;
		}
	}
	
	private void calculatePan(final PointF oldFirstFinger, PointL newX) {
		newX.x = getXAxis().getMin().longValue();
		newX.y = getXAxis().getMax().longValue();
		long offset = Math.round((oldFirstFinger.x - firstFingerPos.x) * ((newX.y - newX.x) / (double)getWidth()));
		newX.x = newX.x + offset;
		newX.y = newX.y + offset;
		long diff = newX.y - newX.x;
		long minLimit = getXAxis().getMinLimit().longValue();
		long maxLimit = getXAxis().getMaxLimit().longValue();
		if(newX.x < minLimit) {
            notifyBoundary(newX.x);
			newX.x = minLimit;
			newX.y = newX.x + diff;
		}
		if(newX.y > maxLimit) {
			newX.y = maxLimit;
			newX.x = newX.y - diff;
		}
	}

	private void zoom(final MotionEvent motionEvent) {
		float oldDist = mDistX;
		float newDist = getXDistance(motionEvent);
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
		if(mZoomHorizontally) {
			PointL newX = new PointL();
			calculateZoom(scale, newX);
			getXAxis().zoom(newX.x, newX.y);
		}
		if(mZoomVertically) {
			PointF newX = new PointF();
			for( Axis axis : getYAxes() ) {
				calculateZoom(scale, newX, axis);
				axis.zoom(newX.x, newX.y);
			}
		}
		redraw();
	}

	private void calculateZoom(float scale, PointF newX, Axis axis) {
		float calcMax = axis.getMax().floatValue();
		float span = calcMax - axis.getMin().floatValue();
		float midPoint = calcMax - (span / 2.0f);
		float offset = span * scale / 2.0f;
		newX.x = midPoint - offset;
		newX.y = midPoint + offset;
		float minLimit = axis.getMinLimit().floatValue();
		float maxLimit = axis.getMaxLimit().floatValue();
		if(newX.x < minLimit)
            newX.x = minLimit;
		if(newX.y > maxLimit)
			newX.y = maxLimit;
	}
	
	private void calculateZoom(float scale, PointL newX) {
		long calcMax = getXAxis().getMax().longValue();
		long span = calcMax - getXAxis().getMin().longValue();		
		long midPoint = calcMax - span/2;
		long offset = Math.round((double)span/2.0 * scale);
		newX.x = midPoint - offset;
		newX.y = midPoint + offset;
		long minLimit = getXAxis().getMinLimit().longValue();
		long maxLimit = getXAxis().getMaxLimit().longValue();
		if(newX.x < minLimit)
			newX.x = minLimit;
		if(newX.y > maxLimit)
			newX.y = maxLimit;
	}
}
