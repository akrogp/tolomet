package com.akrog.tolomet.providers;


public enum WindProviderType {
	Euskalmet("EU", new EuskalmetProvider()),
	MeteoNavarra("GN", new MeteoNavarraProvider()),
	Aemet("AE", new AemetProvider()),
	LaRioja("RI", new LaRiojaProvider()),
	MeteoGalicia("GA", new MeteoGaliciaProvider()),
	RedVigia("RV", new RedVigiaProvider()),
	Meteocat("CA", new MeteocatProvider()),
	CurrentVantage("RCNL", new CurrentVantageProvider()),
	Metar("MA", new MetarProvider()),
	Prades("PR", new PradesProvider());
	
	private final String code;
	private final WindProvider provider;
	
	private WindProviderType(String code, WindProvider provider) {
        this.code = code;
        this.provider = provider;        
    }
	
	public String getCode() {
		return code;
	}
	
	public WindProvider getProvider() {
		return provider;
	}
}
