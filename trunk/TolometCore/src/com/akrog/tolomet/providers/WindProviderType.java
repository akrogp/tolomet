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
        if( code.equals("EU") )
        	provider = new EuskalmetProvider();
        else if( code.equals("GN") )
        	provider = new MeteoNavarraProvider();
        else if( code.equals("AE") )
        	provider = new AemetProvider();
        else if( code.equals("RI") )
        	provider = new LaRiojaProvider();
        else if( code.equals("GA") )
        	provider = new MeteoGaliciaProvider();
        else if( code.equals("RV") )
        	provider = new RedVigiaProvider();
        else if( code.equals("CA") )
        	provider = new MeteocatProvider();
        else
        	provider=null;
    }
	
	public String getCode() {
		return code;
	}
	
	public WindProvider getProvider() {
		return provider;
	}
}
