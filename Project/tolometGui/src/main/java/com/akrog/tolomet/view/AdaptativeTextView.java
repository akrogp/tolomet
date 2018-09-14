package com.akrog.tolomet.view;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;

// See: http://stackoverflow.com/questions/2617266/how-to-adjust-text-font-size-to-fit-textview
public class AdaptativeTextView extends AppCompatTextView {

	public AdaptativeTextView(Context context) {
		super(context);
		initialize();
	}

	public AdaptativeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {
        testPaint = new Paint();
        testPaint.set(getPaint());
        maxSize = getTextSize();
    }
    
    private void refitText(String text, int textWidth) { 
        if (textWidth <= 0)
            return;
        int targetWidth = textWidth - getPaddingLeft() - getPaddingRight();
        float hi = 100;
        float lo = 2;
        final float threshold = 0.5f; // How close we have to be

        testPaint.set(getPaint());

        while( (hi - lo) > threshold && lo < maxSize ) {
            float size = (hi+lo)/2;
            testPaint.setTextSize(size);
            if(testPaint.measureText(text) >= targetWidth) 
                hi = size; // too big
            else
                lo = size; // too small
        }
        // Use lo so that we undershoot rather than overshoot
        if( lo > maxSize )
        	lo = maxSize;
        setTextSize(TypedValue.COMPLEX_UNIT_PX, lo);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int height = getMeasuredHeight();
        refitText(getText().toString(), parentWidth);
        setMeasuredDimension(parentWidth, height);
    }

    @Override
    protected void onTextChanged(final CharSequence text, final int start, final int before, final int after) {
        refitText(text.toString(), getWidth());
    }

    @Override
    protected void onSizeChanged (int w, int h, int oldw, int oldh) {
        if (w != oldw)
            refitText(getText().toString(), w);
    }
    
    private Paint testPaint;
    private float maxSize;
}
