package com.akrog.tolomet.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class PlotYY extends View {	
	private static final int DEFAULT_TITLE_SIZE = 15;
	private static final int DEFAULT_LABEL_SIZE = 15;
	private static final int TICK_SIZE = 3;
	private static final int TICK_MARGIN = 2;
	private static final int BORDER = 2;
	private final Paint paintBorder = new Paint();
	private final Paint paintTitle = new Paint();
	private final Paint paintY1 = new Paint();
	private final Paint paintY2 = new Paint();
	private final Paint paintX = new Paint();
	private final Paint paintChart = new Paint();
	private final Paint paintGrid = new Paint();
	private String title = "Title";
	private String y1label = "y1label";
	private String y2label = "y2label";
	private long minX=0, maxX=1000*60*60;
	private int stepsX=10, ticksPerStepX=1;
	private int minY1=0, maxY1=10, stepsY1=10, ticksPerStepY1=1;
	private int minY2=0, maxY2=100, stepsY2=10, ticksPerStepY2=1;
	private final List<Graph> graphs = new ArrayList<Graph>();
	
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
		paintBorder.setColor(Color.DKGRAY);
		paintBorder.setStyle(Style.FILL);
				
		paintChart.setColor(Color.WHITE);
		paintChart.setStyle(Style.FILL);
		
		paintGrid.setColor(Color.LTGRAY);
		
		paintTitle.setColor(Color.LTGRAY);
		paintTitle.setTextSize(DEFAULT_TITLE_SIZE);
		paintTitle.setTextAlign(Align.CENTER);
		
		paintY1.setColor(Color.LTGRAY);
		paintY1.setTextSize(DEFAULT_LABEL_SIZE);
		paintY1.setTextAlign(Align.RIGHT);
		
		paintY2.setColor(Color.LTGRAY);
		paintY2.setTextSize(DEFAULT_LABEL_SIZE);
		paintY2.setTextAlign(Align.LEFT);
		
		paintX.setColor(Color.LTGRAY);
		paintX.setTextSize(DEFAULT_LABEL_SIZE);
		paintX.setTextAlign(Align.CENTER);
	}
	
	public void addGraph(Graph graph) {
		graphs.add(graph);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		float canvasHeight = getHeight();
		float canvasWidth = getWidth();
		float chartTop = BORDER+paintTitle.getTextSize()+paintY1.getTextSize();
		float chartBottom = canvasHeight-1-BORDER-paintX.getTextSize()-TICK_SIZE-TICK_MARGIN;
		float chartLeft = 2*BORDER+paintTitle.getTextSize()+paintY1.measureText(maxY1+"")+TICK_SIZE+TICK_MARGIN;
		float chartRight = canvasWidth-1-2*BORDER-paintTitle.getTextSize()-paintY2.measureText(maxY2+"")-TICK_SIZE-TICK_MARGIN;
		float chartWidth = chartRight-chartLeft+1;
		float chartHeight = chartBottom-chartTop+1;
		
		canvas.drawRect(0, 0, canvasWidth-1, canvasHeight-1, paintBorder);
		canvas.drawRect(chartLeft, chartTop, chartRight, chartBottom, paintChart);
		canvas.drawText(title, canvasWidth/2, BORDER+paintTitle.getTextSize(), paintTitle);
		
		drawYTicks(canvas, chartLeft-TICK_SIZE-TICK_MARGIN, chartBottom, chartWidth, chartHeight, minY1, maxY1, stepsY1, ticksPerStepY1, paintY1);
		drawYTicks(canvas, chartRight+TICK_SIZE+TICK_MARGIN, chartBottom, -1, chartHeight, minY2, maxY2, stepsY2, ticksPerStepY2, paintY2);
		drawXTicks(canvas, chartLeft, canvasHeight-1-BORDER, chartBottom, chartWidth, chartHeight);
		
		canvas.save();
		canvas.rotate(-90,0,0);
		canvas.drawText(y1label, -canvasHeight/2, BORDER+paintTitle.getTextSize(), paintTitle);
		canvas.drawText(y2label, -canvasHeight/2, canvasWidth-1-BORDER-paintTitle.getTextSize()/3, paintTitle);
		canvas.restore();
		
		for( Graph graph : graphs )
			drawGraph(canvas,graph,chartLeft,chartBottom,chartWidth,chartHeight);
	}
	
	private void drawGraph(Canvas canvas, Graph graph, float x0, float y0, float w, float h) {		
		int len = graph.size();
		if( len == 0 )
			return;
		Paint linePaint = new Paint();
		linePaint.setColor(graph.getLineColor());
		linePaint.setStrokeWidth(2.0f);
		Paint pointPaint = new Paint();
		pointPaint.setColor(graph.getPointColor());
		pointPaint.setStrokeWidth(6.0f);
		float x1 = Float.MIN_VALUE;
		float y1 = Float.MIN_VALUE;
		float x2, y2;
		for( int i = 0; i < len; i++ ) {
			if( graph.getX(i) < getMinX() )
				continue;
			if( graph.getX(i) > getMaxX() )
				break;
			x2 = Math.round((double)x0+xpx(graph.getX(i),w));
			y2 = Math.round(y0-y1px(graph.getY(i),h));
			if( x1 == Float.MIN_VALUE ) {
				x1=x2;
				y1=y2;
			}
			canvas.drawLine(x1, y1, x2, y2, linePaint);
			canvas.drawPoint(x2, y2, pointPaint);
			x1=x2;
			y1=y2;
		}
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
	
	/*private float y2px(float n, float h) {
		return px(n, getMinY2(), getMaxY2(), h);
	}*/

	public void redraw() {
		invalidate();
	}
	
	private void drawYTicks(Canvas canvas, float x1, float y2, float width, float height, float min, float max, int steps, int ticksPerStep, Paint paint) {
		float step = ((float)(max-min))/steps, inter;
		if( step == 0 )
			return;
		float y, yy;
		for( float i = min; i <= max; i+= step ) {
			y = Math.round(y2-px(i,min,max,height));
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
		long step = (maxX-minX)/stepsX, inter;
		if( step == 0 )
			return;
		Calendar calendar = Calendar.getInstance();
		float x, xx;
		for( long i = minX; i <= maxX; i += step ) {
			calendar.setTimeInMillis(i);
			x = Math.round(x1+xpx(i, width));
			canvas.drawText(
				String.format("%02d:%02d",calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE)),
				x, bottom, paintX);
			canvas.drawLine(x, y2+TICK_SIZE, x, y2-height+1, paintGrid);
			inter = step/ticksPerStepX;
			if( i+inter < maxX )
				for( int j = 1; j < ticksPerStepX; j++ ) {
					xx = Math.round(x1+xpx(i+j*inter, width));
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
	
	public void addY1Marker(float y, String text) {		
	}
	
	public void addY2Marker(float y, String text) {		
	}
}
