package com.akrog.tolomet;

public enum WindProviderType {
	Euskalmet(0), MeteoNavarra(1);
	
	private final int value;
	
	private WindProviderType(int value) {
        this.value = value;
    }
	
	public int getValue() {
        return value;
    }
}
