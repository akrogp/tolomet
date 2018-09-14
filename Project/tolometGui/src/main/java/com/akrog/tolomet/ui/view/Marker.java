package com.akrog.tolomet.ui.view;

public class Marker {
	private float pos;
	private String label;
	int color;
	
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public Marker( float pos, String label, int color ) {
		this.setPos(pos);
		this.setLabel(label);
		this.color = color;
	}

	public float getPos() {
		return pos;
	}

	public void setPos(float pos) {
		this.pos = pos;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
