package com.akrog.tolomet.providers;


public enum WindProviderType {
	Euskalmet("EU", new EuskalmetProvider(), WindProviderQuality.Good),
	MeteoNavarra("GN", new MeteoNavarraProvider(), WindProviderQuality.Good),
	Aemet("AE", new AemetProvider(), WindProviderQuality.Poor),
	LaRioja("RI", new LaRiojaProvider(), WindProviderQuality.Good),
	MeteoGalicia("GA", new MeteoGaliciaProvider(), WindProviderQuality.Good),
	RedVigia("RV", new RedVigiaProvider(), WindProviderQuality.Poor),
	Meteocat("CA", new MeteocatProvider(), WindProviderQuality.Medium),
	CurrentVantage("RCNL", new CurrentVantageProvider(), WindProviderQuality.Poor),
	Metar("MA", new MetarProvider(), WindProviderQuality.Poor),
	Prades("PR", new PradesProvider(), WindProviderQuality.Medium),
	Holfuy("HO", new HolfuyProvider(), WindProviderQuality.Good),
	PiouPiou("PI", new PiouProvider(), WindProviderQuality.Medium),
	Ffvl("FFVL", new FfvlProvider(), WindProviderQuality.Medium),
    MeteoFrance("MF", new MeteoFranceProvider(), WindProviderQuality.Poor),
	MeteoClimatic("MC", new MeteoClimaticProvider(), WindProviderQuality.Poor),
	WeatherUnderground("WU", new WeatherUndergroundProvider(), WindProviderQuality.Medium);
	
	private final String code;
	private final WindProvider provider;
	private final WindProviderQuality quality;
	
	private WindProviderType(String code, WindProvider provider, WindProviderQuality quality) {
        this.code = code;
        this.provider = provider;
		this.quality = quality;
    }
	
	public String getCode() {
		return code;
	}
	
	public WindProvider getProvider() {
		return provider;
	}

	public WindProviderQuality getQuality() {
		return quality;
	}
}
