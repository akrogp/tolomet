package com.akrog.tolomet.providers;


public enum WindProviderType {
	Euskalmet("EU", new EuskalmetProvider(), WindProviderQuality.Good, true),
	MeteoNavarra("GN", new MeteoNavarraProvider(), WindProviderQuality.Good, false),
	Aemet("AE", new AemetProvider(), WindProviderQuality.Poor, true),
	LaRioja("RI", new LaRiojaProvider(), WindProviderQuality.Good, false),
	MeteoGalicia("GA", new MeteoGaliciaProvider(), WindProviderQuality.Good, false),
	RedVigia("RV", new RedVigiaProvider(), WindProviderQuality.Poor, false),
	Meteocat("CA", new MeteocatProvider(), WindProviderQuality.Medium, false),
	CurrentVantage("RCNL", new CurrentVantageProvider(), WindProviderQuality.Poor, false),
	Metar("MA", new MetarProvider(), WindProviderQuality.Poor, false),
	Prades("PR", new PradesProvider(), WindProviderQuality.Medium, false),
	Holfuy("HO", new HolfuyProvider(), WindProviderQuality.Good, false),
	PiouPiou("PI", new PiouProvider(), WindProviderQuality.Medium, false),
	Ffvl("FFVL", new FfvlProvider(), WindProviderQuality.Medium, false),
    MeteoFrance("MF", new MeteoFranceProvider(), WindProviderQuality.Poor, false),
	MeteoClimatic("MC", new MeteoClimaticProvider(), WindProviderQuality.Poor, false),
	WeatherUnderground("WU", new WeatherUndergroundProvider(), WindProviderQuality.Medium, false),
    WirelessWeatherStation("WWS", new WirelessWeatherStationProvider(), WindProviderQuality.Medium, false),
	WeatherDisplay("WD", new WeatherDisplayProvider(), WindProviderQuality.Medium, false);
	
	private final String code;
	private final WindProvider provider;
	private final WindProviderQuality quality;
	private final boolean dynamic;
	
	private WindProviderType(String code, WindProvider provider, WindProviderQuality quality, boolean dynamic) {
        this.code = code;
        this.provider = provider;
		this.quality = quality;
		this.dynamic = dynamic;
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

	public boolean isDynamic() {
		return dynamic;
	}
}
