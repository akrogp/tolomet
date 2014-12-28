package com.akrog.tolomet.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class PlotYY extends View { 
	private float DEFAULT_TITLE_SIZE = 11;	// sp
	private float DEFAULT_LABEL_SIZE = 9;	// sp
	private float DEFAULT_MARKER_SIZE = 9;	// sp
	private float DEFAULT_LEGEND_SIZE = 8;	// sp
	private float TICK_SIZE = 3;			// dip
	private float TICK_MARGIN = 2;			// dip
	private float BORDER = 2;				// dip
	private float LINE_SIZE = 1.5f;			// dip
	private float POINT_SIZE = 5;			// dip
	private final Paint paintBorder = new Paint();
	private final Paint paintTitle = new Paint();
	private final Paint paintY1 = new Paint();
	private final Paint paintY2 = new Paint();
	private final Paint paintX = new Paint();
	private final Paint paintChart = new Paint();
	private final Paint paintGrid = new Paint();
	private final Paint paintMarker = new Paint();
	private final Paint paintLegend = new Paint();
	private String title = "Title";
	private String y1label = "y1label";
	private String y2label = "y2label";
	private String xlabel = "xlabel";
	private long minX=0, maxX=1000*60*60;
	private int stepsX=10, ticksPerStepX=1;
	private int minY1=0, maxY1=10, stepsY1=10, ticksPerStepY1=1;
	private int minY2=0, maxY2=100, stepsY2=10, ticksPerStepY2=1;
	private int minY3=0, maxY3=100;
	private final List<Graph> graphs = new ArrayList<Graph>();
	private Bitmap bgBitmap;
	private final Canvas bgCanvas = new Canvas();
	private List<Marker> y1Markers = new ArrayList<Marker>();
	private List<Marker> y2Markers = new ArrayList<Marker>();
	private List<Marker> y3Markers = new ArrayList<Marker>();
	
	public PlotYY(Context context) {
		super(context);
		init();
	}
	
	public PlotYY(Context context, AttributeSet attrs) {
		super(context,attrs);
		init();
	}
	
	public PlotYY(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context,attrs,defStyleAttr);
		init();
	}
	
	private void init() {
		DEFAULT_TITLE_SIZE = sp2px(DEFAULT_TITLE_SIZE);
		DEFAULT_LABEL_SIZE = sp2px(DEFAULT_LABEL_SIZE);
		DEFAULT_MARKER_SIZE = sp2px(DEFAULT_MARKER_SIZE);
		DEFAULT_LEGEND_SIZE = sp2px(DEFAULT_LEGEND_SIZE);
		TICK_SIZE = dip2px(TICK_SIZE);
		TICK_MARGIN = dip2px(TICK_MARGIN);
		BORDER = dip2px(BORDER);
		LINE_SIZE = dip2px(LINE_SIZE);
		POINT_SIZE = dip2px(POINT_SIZE);
		
		paintBorder.setColor(Color.DKGRAY);
		paintBorder.setStyle(Style.FILL);
				
		paintChart.setColor(Color.WHITE);
		paintChart.setStyle(Style.FILL);
		
		paintGrid.setColor(Color.LTGRAY);
		
		paintTitle.setColor(Color.LTGRAY);
		paintTitle.setTextSize(DEFAULT_TITLE_SIZE);
		paintTitle.setTextAlign(Align.CENTER);
		//paintTitle.setTypeface(Typeface.DEFAULT_BOLD);
		
		paintY1.setColor(Color.LTGRAY);
		paintY1.setTextSize(DEFAULT_LABEL_SIZE);
		paintY1.setTextAlign(Align.RIGHT);
		
		paintY2.setColor(Color.LTGRAY);
		paintY2.setTextSize(DEFAULT_LABEL_SIZE);
		paintY2.setTextAlign(Align.LEFT);
		
		paintX.setColor(Color.LTGRAY);
		paintX.setTextSize(DEFAULT_LABEL_SIZE);
		paintX.setTextAlign(Align.CENTER);
		
		paintMarker.setColor(Color.BLACK);
		paintMarker.setTextSize(DEFAULT_MARKER_SIZE);
		paintMarker.setTextAlign(Align.LEFT);		
		//paintMarker.setPathEffect(new DashPathEffect(new float[] {10,20}, 0));
		
		paintLegend.setColor(Color.LTGRAY);
		paintLegend.setTextSize(DEFAULT_LEGEND_SIZE);
		paintLegend.setTextAlign(Align.LEFT);
	}
	
	public void addY1Graph(Graph graph) {
		graph.setyAxis(0);
		graphs.add(graph);
	}
	
	public void addY2Graph(Graph graph) {
		graph.setyAxis(1);
		graphs.add(graph);
	}
	
	public void addY3Graph(Graph graph) {
		graph.setyAxis(2);
		graphs.add(graph);
	}
	
	public void clearGraphs() {
		graphs.clear();
	}
	
	public void clear() {
		clearGraphs();
		clearMarkers();
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		RectF rect = getChartPosition(w, h);
		createBackgroundBuffer(rect.width(), rect.height());
		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	private void createBackgroundBuffer(float w, float h) {		
		bgBitmap = Bitmap.createBitmap(Math.round(w), Math.round(h), Bitmap.Config.ARGB_4444);
		bgCanvas.setBitmap(bgBitmap);
	}
	
	private RectF getChartPosition( float w, float h ) {
		RectF rect = new RectF();
		rect.top = BORDER+paintTitle.getTextSize()+paintY1.getTextSize();
		rect.bottom = h-1-BORDER-paintX.getTextSize()-TICK_SIZE-2*TICK_MARGIN-paintLegend.getTextSize();
		rect.left = 2*BORDER+paintTitle.getTextSize()+paintY1.measureText(maxY1+"")+TICK_SIZE+TICK_MARGIN;
		rect.right = w-1-2*BORDER-paintTitle.getTextSize()-paintY2.measureText(maxY2+"")-TICK_SIZE-TICK_MARGIN;
		return rect;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		float canvasHeight = getHeight();
		float canvasWidth = getWidth();
		RectF rect = getChartPosition(canvasWidth, canvasHeight);
		float chartTop = rect.top;
		float chartBottom = rect.bottom;
		float chartLeft = rect.left;
		float chartRight = rect.right;
		float chartWidth = rect.width();
		float chartHeight = rect.height();
		
		canvas.drawRect(0, 0, canvasWidth-1, canvasHeight-1, paintBorder);
		canvas.drawRect(chartLeft, chartTop, chartRight, chartBottom, paintChart);
		canvas.drawText(title, canvasWidth/2, BORDER+paintTitle.getTextSize(), paintTitle);
		
		drawYTicks(canvas, chartLeft-TICK_SIZE-TICK_MARGIN, chartBottom, chartWidth, chartHeight, minY1, maxY1, stepsY1, ticksPerStepY1, paintY1);
		drawYTicks(canvas, chartRight+TICK_SIZE+TICK_MARGIN, chartBottom, -1, chartHeight, minY2, maxY2, stepsY2, ticksPerStepY2, paintY2);
		drawXTicks(canvas, chartLeft, chartBottom+TICK_SIZE+TICK_MARGIN+paintX.getTextSize(), chartBottom, chartWidth, chartHeight);
		drawY1Markers(canvas, chartLeft, chartBottom, chartWidth, chartHeight);
		drawY2Markers(canvas, chartLeft, chartBottom, chartWidth, chartHeight);
		drawY3Markers(canvas, chartLeft, chartBottom, chartWidth, chartHeight);
		
		canvas.save();
		canvas.rotate(-90,0,0);
		//canvas.drawText(y1label, -canvasHeight/2, BORDER+paintTitle.getTextSize(), paintTitle);
		canvas.drawText(y1label, -canvasHeight/2, paintTitle.getTextSize(), paintTitle);
		canvas.drawText(y2label, -canvasHeight/2, canvasWidth-1-BORDER-paintTitle.getTextSize()/3, paintTitle);		
		canvas.restore();
		canvas.drawText(xlabel, chartLeft, chartBottom+TICK_SIZE+TICK_MARGIN+paintX.getTextSize()*2, paintTitle);
		
		createBackgroundBuffer(chartWidth, chartHeight);
		for( int i = 0; i < graphs.size(); i++ ) {
			Graph graph = graphs.get(i);
			drawGraph(graph);		
			drawLegend(canvas,canvasWidth/(graphs.size()+1)*(i+1),canvasHeight-BORDER-1,graph);
		}
		canvas.drawBitmap(bgBitmap,chartLeft,chartTop,null);
	}

	private void drawLegend(Canvas canvas, float x1, float y2, Graph graph) {
		float w = paintLegend.getTextSize();
		float x2 = x1+w-1;
		float y1 = y2-w+1;
		
		Paint linePaint = new Paint();
		linePaint.setColor(graph.getLineColor());
		linePaint.setStrokeWidth(LINE_SIZE);
		Paint pointPaint = new Paint();
		pointPaint.setColor(graph.getPointColor());
		pointPaint.setStrokeWidth(POINT_SIZE);
		
		canvas.drawRect(x1, y1, x2, y2, paintChart);
		canvas.drawText(graph.getTitle(), x1+w, y2, paintLegend);
		canvas.drawLine(x1+1, y2-1, x2-1, y1+1, linePaint);
		canvas.drawPoint((x1+x2)/2, (y1+y2)/2, pointPaint);
	}

	private void drawY1Markers(Canvas canvas, float x, float bottom, float w, float h) {
		paintMarker.setTextAlign(Align.LEFT);
		for( Marker marker : y1Markers ) {
			float y = Math.round(bottom-y1px(marker.getPos(),h));
			paintMarker.setColor(marker.getColor());
			canvas.drawLine(x, y, x+w-1, y, paintMarker);
			String text = marker.getLabel();
			if( text == null )
				text = String.format("%s", marker.getPos());
			canvas.drawText(text, x+TICK_MARGIN, y-TICK_MARGIN, paintMarker);
		}
	}
	
	private void drawY2Markers(Canvas canvas, float x, float bottom, float w, float h) {
		paintMarker.setTextAlign(Align.RIGHT);
		for( Marker marker : y2Markers ) {
			float y = Math.round(bottom-y2px(marker.getPos(),h));
			paintMarker.setColor(marker.getColor());
			canvas.drawLine(x, y, x+w-1, y, paintMarker);
			String text = marker.getLabel();
			if( text == null )
				text = String.format("%s", marker.getPos());
			canvas.drawText(text, x+w-1-TICK_MARGIN, y-TICK_MARGIN, paintMarker);
		}
	}
	
	private void drawY3Markers(Canvas canvas, float x, float bottom, float w, float h) {
		paintMarker.setTextAlign(Align.LEFT);
		for( Marker marker : y3Markers ) {
			float y = Math.round(bottom-y3px(marker.getPos(),h));
			paintMarker.setColor(marker.getColor());
			canvas.drawLine(x, y, x+w-1, y, paintMarker);
			String text = marker.getLabel();
			if( text == null )
				text = String.format("%s", marker.getPos());
			canvas.drawText(text, x+TICK_MARGIN, y-TICK_MARGIN, paintMarker);
		}
	}

	private void drawGraph(Graph graph) {		
		int len = graph.size();
		if( len == 0 )
			return;
				
		Paint linePaint = new Paint();
		linePaint.setColor(graph.getLineColor());
		linePaint.setStrokeWidth(LINE_SIZE);
		Paint pointPaint = new Paint();
		pointPaint.setColor(graph.getPointColor());
		pointPaint.setStrokeWidth(POINT_SIZE);
		
		long[] x = graph.getX();
		float[] y = graph.getY();
		float xg1 = Float.MIN_VALUE, yg1 = Float.MIN_VALUE, y1=Float.MIN_VALUE, xg2, yg2, y2;
		long x2;
		for( int i = 0; i < len; i++ ) {
			x2=x[i];
			y2=y[i];
			xg2 = xgpx(x2);
			yg2 = ygpx(y2,graph.getyAxis());
			if( xg1 == Float.MIN_VALUE ) {
				xg1=xg2;
				yg1=yg2;
				y1=y2;
			} else if( graph.getWrap() > 0 && Math.abs(y2-y1) > graph.getWrap2() ) {
				float wy = y2 > y1 ? y2-graph.getWrap() : y2+graph.getWrap();
				bgCanvas.drawLine(xg1, yg1, xg2, ygpx(wy, graph.getyAxis()), linePaint);
				wy = y2 > y1 ? y1+graph.getWrap() : y1-graph.getWrap();
				yg1 = ygpx(wy, graph.getyAxis());
			}
			bgCanvas.drawLine(xg1, yg1, xg2, yg2, linePaint);
			xg1=xg2;
			yg1=yg2;
			y1=y2;
		}
		for( int i = 0; i < len; i++ )
			bgCanvas.drawPoint(xgpx(x[i]), ygpx(y[i],graph.getyAxis()), pointPaint);
	}
	
	private float ygpx(float y, int axis) {
		int h = bgCanvas.getHeight();
		switch( axis ) {
			case 0: y = y1px(y,h); break;
			case 1: y = y2px(y,h); break;
			case 2: y = y3px(y,h); break;
		}
		return Math.round(bgCanvas.getHeight()-1-y);
	}

	private float xgpx(long x) {
		return Math.round((double)xpx(x,bgCanvas.getWidth()));
	}

	private float px(float n, float min, float max, float pixels) {
		return (n-min)/(max-min)*(pixels-1);
	}
	
	private float xpx(long n, float w) {
		return ((float)(n-getMinX()))/(getMaxX()-getMinX())*(w-1);
	}
	
	private float y1px(float n, float h) {
		return px(n, getMinY1(), getMaxY1(), h);
	}
	
	private float y2px(float n, float h) {
		return px(n, getMinY2(), getMaxY2(), h);
	}
	
	private float y3px(float n, float h) {
		return px(n, getMinY3(), getMaxY3(), h);
	}

	public void redraw() {
		invalidate();
	}
	
	private void drawYTicks(Canvas canvas, float x1, float y2, float width, float height, float min, float max, int steps, int ticksPerStep, Paint paint) {
		float step = ((float)(max-min))/steps, inter;
		if( step == 0 )
			return;
		float y, yy;
		for( float i = min; i <= max; i+= step ) {
			y = Math.round(y2-1-px(i,min,max,height));
			canvas.drawText((int)i+"", x1, y, paint);
			if( width > 0 ) {
				canvas.drawLine(x1+TICK_MARGIN, y, x1+TICK_MARGIN+2*TICK_SIZE+width-1, y, paintGrid);
				inter = step/ticksPerStep;
				if( i+inter < max )
					for( float j = 1; j < ticksPerStep; j++ ) {
						yy = Math.round(y2-px(i+j*inter,min,max,height));
						canvas.drawLine(x1+TICK_MARGIN+TICK_SIZE, yy, x1+TICK_MARGIN+TICK_SIZE+width-1, yy, paintGrid);
					}
			}
		}
	}
	
	private void drawXTicks(Canvas canvas, float x1, float bottom, float y2, float width, float height) {
		double step = ((double)(maxX-minX))/stepsX; 
		long inter, stamp;
		if( step == 0 )
			return;
		Calendar calendar = Calendar.getInstance();
		float x, xx;
		for( double i = minX; i <= maxX; i += step ) {
			stamp = Math.round(i);
			calendar.setTimeInMillis(stamp);
			x = Math.round(x1+xpx(stamp, width));
			canvas.drawText(
				String.format("%02d:%02d",calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE)),
				x, bottom, paintX);
			canvas.drawLine(x, y2+TICK_SIZE, x, y2-height+1, paintGrid);
			inter = Math.round(step/ticksPerStepX);
			if( stamp+inter < maxX )
				for( int j = 1; j < ticksPerStepX; j++ ) {
					xx = Math.round(x1+xpx(stamp+j*inter, width));
					canvas.drawLine(xx, y2, xx, y2-height+1, paintGrid);
				}
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setXLabel(String xlabel) {
		this.xlabel = xlabel;
	}
	
	public String getXLabel() {
		return xlabel;
	}
	
	public String getY1Label() {
		return y1label;
	}

	public void setY1Label(String y1label) {
		this.y1label = y1label;
	}

	public String getY2Label() {
		return y2label;
	}

	public void setY2Label(String y2label) {
		this.y2label = y2label;
	}

	public void setXRange(float minX, float maxX) {
		setMinX(Math.round((double)minX));
		setMaxX(Math.round((double)maxX));
	};

	public void setY1Range(float minY, float maxY) {
		setMinY1(Math.round(minY));
		setMaxY1(Math.round(maxY));
	};
	
	public void setY2Range(float minY, float maxY) {
		setMinY2(Math.round(minY));
		setMaxY2(Math.round(maxY));
	};
	
	public void setY3Range(float minY, float maxY) {
		setMinY3(Math.round(minY));
		setMaxY3(Math.round(maxY));
	};
	
	public long getMinX() {
		return minX;
	}

	public void setMinX(long minX) {
		this.minX = minX;
	}

	public long getMaxX() {
		return maxX;
	}

	public void setMaxX(long maxX) {
		this.maxX = maxX;
	}

	public int getStepsX() {
		return stepsX;
	}

	public void setStepsX(int ticksX) {
		this.stepsX = ticksX;
	}

	public int getMinY1() {
		return minY1;
	}

	public void setMinY1(int minY1) {
		this.minY1 = minY1;
	}

	public int getMaxY1() {
		return maxY1;
	}

	public void setMaxY1(int maxY1) {
		this.maxY1 = maxY1;
	}

	public int getStepsY1() {
		return stepsY1;
	}

	public void setStepsY1(int ticksY1) {
		this.stepsY1 = ticksY1;
	}

	public int getMinY2() {
		return minY2;
	}

	public void setMinY2(int minY2) {
		this.minY2 = minY2;
	}

	public int getMaxY2() {
		return maxY2;
	}

	public void setMaxY2(int maxY2) {
		this.maxY2 = maxY2;
	}

	public int getStepsY2() {
		return stepsY2;
	}

	public void setStepsY2(int ticksY2) {
		this.stepsY2 = ticksY2;
	}
	
	public int getMinY3() {
		return minY3;
	}

	public void setMinY3(int minY3) {
		this.minY3 = minY3;
	}

	public int getMaxY3() {
		return maxY3;
	}

	public void setMaxY3(int maxY3) {
		this.maxY3 = maxY3;
	}
	
	public int getTicksPerStepX() {
		return ticksPerStepX;
	}

	public void setTicksPerStepX(int ticksPerStepX) {
		this.ticksPerStepX = ticksPerStepX;
	}

	public int getTicksPerStepY1() {
		return ticksPerStepY1;
	}

	public void setTicksPerStepY1(int ticksPerStepY1) {
		this.ticksPerStepY1 = ticksPerStepY1;
	}

	public int getTicksPerStepY2() {
		return ticksPerStepY2;
	}

	public void setTicksPerStepY2(int ticksPerStepY2) {
		this.ticksPerStepY2 = ticksPerStepY2;
	}
	
	public void addY1Marker(Marker marker) {
		y1Markers.add(marker);
	}
	
	public void addY2Marker(Marker marker) {
		y2Markers.add(marker);
	}
	
	public void addY3Marker(Marker marker) {
		y3Markers.add(marker);
	}
	
	public void clearMarkers() {
		y1Markers.clear();
		y2Markers.clear();
		y3Markers.clear();
	}
	
	public float dip2px( float dip ) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getContext().getResources().getDisplayMetrics());
	}
	
	public float sp2px( float sp ) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getContext().getResources().getDisplayMetrics());
	}
	
	public float pt2px( float pt ) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, pt, getContext().getResources().getDisplayMetrics());
	}
}
