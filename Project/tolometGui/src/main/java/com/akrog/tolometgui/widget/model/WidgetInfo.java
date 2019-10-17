package com.akrog.tolometgui.widget.model;

import com.akrog.tolometgui.model.FlySpot;

public class WidgetInfo {
    private int widgetId;
    private int widgetSize;
    private FlySpot flySpot;

    public int getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(int widgetId) {
        this.widgetId = widgetId;
    }

    public int getWidgetSize() {
        return widgetSize;
    }

    public void setWidgetSize(int widgetSize) {
        this.widgetSize = widgetSize;
    }

    public FlySpot getFlySpot() {
        return flySpot;
    }

    public void setFlySpot(FlySpot flySpot) {
        this.flySpot = flySpot;
    }
}
