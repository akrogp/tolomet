package com.akrog.tolomet.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.akrog.tolomet.io.Downloader;
import com.akrog.tolomet.providers.MeteoGaliciaProvider;

public class GaliciaParams {
	public static void main( String[] args ) {
		InputStream inputStream = MeteoGaliciaProvider.class.getResourceAsStream("/res/meteogalicia.csv");
		InputStreamReader in = new InputStreamReader(inputStream);
		BufferedReader rd = new BufferedReader(in);
		String line;
		String[] fields;
		try {
			while( (line=rd.readLine()) != null ) {
				fields = line.split(":");
				System.out.println(String.format("%s:%s:%s:%s",fields[0],getParams(fields[0]),fields[2],fields[3]));
			}
			rd.close();
		} catch( Exception e ) {			
		}
	}
	
	private static String getParams( String est ) {				
		Downloader downloader = new Downloader();
		downloader.setUrl("http://www2.meteogalicia.es/galego/observacion/estacions/contidos/sensor_periodo.asp");
		downloader.addParam("Nest", est);
		downloader.addParam("periodo", 1);
		String str = downloader.download();
		String[] items = str.split("<td class=\"esq2\">");
		
		StringBuilder result = new StringBuilder();
		for( int i = 1; i < items.length; i++ ) {
			for( String param : params )
				if( items[i].contains(param) ) {
					if( result.length() != 0 )
						result.append(',');
					result.append(items[i].split("\"")[17]);
				}
		}
		
		return result.toString();
	}
		
	private final static String[] params = {						
		">Direcci%F3n+do+Vento<",
		">Velocidade+do+Vento<",
		">Refacho<",
		">Humidade+relativa+media<",
		">Temperatura+media<",
		">Presi%F3n+Barom%E9trica<"
	};
}
