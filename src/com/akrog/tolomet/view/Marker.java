package com.akrog.tolomet.view;

public class Marker {
	private float pos;
	private String label;
	
	public Marker( float pos, String label ) {
		this.setPos(pos);
		this.setLabel(label);
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
