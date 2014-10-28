package com.akrog.tolomet.providers;


public enum WindProviderType {
	Euskalmet("EU"),
	MeteoNavarra("GN"),
	Aemet("AE"),
	LaRioja("RI"),
	MeteoGalicia("GA"),
	RedVigia("RV"),
	Meteocat("CA");
	
	private final String code;
	private final WindProvider provider;
	
	private WindProviderType(String code) {
        this.code = code;
        switch( code ) {
        	case "EU": provider = new EuskalmetProvider(); break;
        	case "GN": provider = new MeteoNavarraProvider(); break;
        	case "AE": provider = new AemetProvider(); break;
        	case "RI": provider = new LaRiojaProvider(); break;
        	case "GA": provider = new MeteoGaliciaProvider(); break;
        	case "RV": provider = new RedVigiaProvider(); break;
        	case "CA": provider = new MeteocatProvider(); break;
        	default: provider=null;
        }
    }
	
	public String getCode() {
		return code;
	}
	
	public WindProvider getProvider() {
		return provider;
	}
}
