package com.akrog.tolomet.data;

public enum WindProviderType {
	Euskalmet(0), MeteoNavarra(1), Aemet(2);
	
	private final int value;
	
	private WindProviderType(int value) {
        this.value = value;
    }
	
	public int getValue() {
        return value;
    }
}
