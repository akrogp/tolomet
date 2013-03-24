package com.akrog.tolomet;

public enum SpinnerType {
	StartMenu(100), AllStations(101), FavoriteStations(102), CloseStations(103), Regions(104), Index(105);
	
	private final int value;
	
	private SpinnerType(int value) {
        this.value = value;
    }
	
	public int getValue() {
        return value;
    }
}
