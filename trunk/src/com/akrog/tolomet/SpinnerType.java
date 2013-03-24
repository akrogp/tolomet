package com.akrog.tolomet;

public enum SpinnerType {
	StartMenu(100),
	AllStations(101),
	FavoriteStations(102),
	CloseStations(103),
	RegionStations(104),
	VowelSations(105),
	Regions(106),
	Vowels(107);
	
	private final int value;
	
	private SpinnerType(int value) {
        this.value = value;
    }
	
	public int getValue() {
        return value;
    }
}
