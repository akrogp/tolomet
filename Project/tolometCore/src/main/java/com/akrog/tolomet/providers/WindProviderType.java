package com.akrog.tolomet.providers;


public enum WindProviderType {
	Euskalmet("EU", new EuskalmetProvider(), WindProviderQuality.Good, true),
	MeteoNavarra("GN", new MeteoNavarraProvider(), WindProviderQuality.Good, true),
	Aemet("AE", new AemetProvider(), WindProviderQuality.Poor, true),
	LaRioja("RI", new LaRiojaProvider(), WindProviderQuality.Good, true),
	MeteoGalicia("GA", new MeteoGaliciaProvider(), WindProviderQuality.Good, true),
	RedVigia("RV", new RedVigiaProvider(), WindProviderQuality.Poor, false),
	Meteocat("CA", new MeteocatProvider(), WindProviderQuality.Medium, true),
	CurrentVantage("RCNL", new CurrentVantageProvider(), WindProviderQuality.Poor, false),
	Metar("MA", new MetarProvider(), WindProviderQuality.Poor, true),
	Prades("PR", new PradesProvider(), WindProviderQuality.Medium, false),
	Holfuy("HO", new HolfuyProvider(), WindProviderQuality.Good, true),
	PiouPiou("PI", new PiouProvider(), WindProviderQuality.Medium, true),
	Ffvl("FFVL", new FfvlProvider(), WindProviderQuality.Good, true),
    MeteoFrance("MF", new MeteoFranceProvider(), WindProviderQuality.Poor, true),
	MeteoClimatic("MC", new MeteoClimaticProvider(), WindProviderQuality.Poor, true),
	WeatherUnderground("WU", new WeatherUndergroundProvider(), WindProviderQuality.Medium, false),
    WirelessWeatherStation("WWS", new WirelessWeatherStationProvider(), WindProviderQuality.Medium, false),
	WeatherDisplay("WD", new WeatherDisplayProvider(), WindProviderQuality.Poor, false),
	WeatherCloud("WC", new WeatherCloudProvider(), WindProviderQuality.Medium, false),
	FieldClimate("FC", new FieldClimateProvider(), WindProviderQuality.Good, false),
	Malloles("MA", new MallolesProvider(), WindProviderQuality.Good, false),
	Noromet("NORO", new NorometProvider(), WindProviderQuality.Medium, true);
	
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
