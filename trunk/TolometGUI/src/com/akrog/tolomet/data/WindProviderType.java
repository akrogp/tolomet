package com.akrog.tolomet.data;

public enum WindProviderType {
	Euskalmet(0), MeteoNavarra(1), Aemet(2), LaRioja(3), MeteoGalicia(4), RedVigia(5), Meteocat(6);
	
	private final int value;
	private final String codes[]={"EU", "GN", "AE", "RI", "GA", "RV", "CA" };
	
	private WindProviderType(int value) {
        this.value = value;
    }
	
	public int getValue() {
        return value;
    }
	
	public String getCode() {
		return codes[value];
	}
}
