package com.akrog.tolomet.utils;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.providers.WindProviderType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import uk.me.jstott.jcoord.LatLng;
import uk.me.jstott.jcoord.UTMRef;

public class FixStations {
	public static void main(String[] args) throws IOException {
		logger.info("Started");
		//List<Station> stations = ResourceManager.loadCountryStations("ES");
        List<Station> stations = ResourceManager.loadCountryStations("FR");
		//List<Station> stations = ResourceManager.loadAllStations();
		addNewStations(stations);
		sortStations(stations);
		for( Station station : stations ) {
			fixName(station);
            fixMetarCountry(station);
			/*if( station.getProviderType().equals(WindProviderType.RedVigia) )
				continue;*/
			if( station.getLatitude() == 0.0 )
				ResourceManager.showStation(station);
			else
            	ResourceManager.saveStation(station);
		}
		logger.info(String.format("Finished: %d stations",stations.size()));
	}

    private static void fixMetarCountry( Station station ) {
        if( station.getName().equals("Temuco") || station.getName().equals("Monopulli") )
            station.setCountry("CL");
        if( station.getName().equals("St Pierre-France"))
            station.setCountry("CA");
        if( station.getName().startsWith("Enewetak") || station.getName().startsWith("Ujae Atoll") )
            station.setCode("MH");
    }

	private static void addNewStations(List<Station> stations) {
		try {
			//stations.addAll(MetarStations.loadMetars());
			//stations.addAll(PradesStations.getStations());
            //stations.addAll(new HolfuyStations().getStations());
			//stations.addAll(PiouStations.getStations());
			stations.addAll(new FfvlStations().getStations());
			Station station;
			while( (station=askNew()) != null )
				stations.add(station);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	private static Station askNew() throws IOException {
		if( !askParameter("¿Introducir nueva estación? (s/n)").equalsIgnoreCase("s") )			
			return null;
		Station station = new Station();
		station.setCode(askParameter("Código"));
		station.setName(askParameter("Nombre"));
		station.setProviderType(WindProviderType.valueOf(askParameter("Proveedor", (Object[])WindProviderType.values())));
		station.setCountry(askParameter("Código país").toUpperCase());
		station.setRegion(Integer.parseInt(askParameter("Región")));
		if( askParameter("Formato coordenadas (ll/utm)").equalsIgnoreCase("ll") ) {
			station.setLatitude(Double.parseDouble(askParameter("Latitud")));
			station.setLongitude(Double.parseDouble(askParameter("Longitud")));
		} else {
			double x = Double.parseDouble(askParameter("Easting"));
			double y = Double.parseDouble(askParameter("Northing"));
			System.out.println("http://whatutmzoneamiin.blogspot.com.es/p/map.html");
			UTMRef utm = new UTMRef(
				x, y,
				Character.toUpperCase(askParameter("latZone").charAt(0)),
				Integer.parseInt(askParameter("lngZone")));
			LatLng ll = utm.toLatLng();
			station.setLatitude(ll.getLat());
			station.setLongitude(ll.getLng());
		}
		ResourceManager.showStation(station);
		return askParameter("¿OK? (s/n)").equalsIgnoreCase("s") ? station : null;
	}

	private static String askParameter( String msg, Object... opts ) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		if( opts.length == 0 ) {
			System.out.print(String.format("%s: ", msg));
			return br.readLine();
		}
		System.out.println(String.format("%s:", msg));
		int i = 0;
		for( Object opt : opts )
			System.out.println(String.format("%d) %s", i++, opt));
		do {
			try {
				System.out.print("Elección -> ");
				i = Integer.parseInt(br.readLine());
			} catch(NumberFormatException e ) {
				i = -1;
			}
		} while(i < 0 || i >= opts.length);
		return opts[i].toString();
	}

	private static void sortStations(List<Station> stations) {
		final Collator collator = Collator.getInstance();
		collator.setStrength(Collator.PRIMARY);
		Collections.sort(stations, new Comparator<Station>() {
			@Override
			public int compare(Station o1, Station o2) {
				return collator.compare(o1.getName(), o2.getName());
			}
		});
	}

	private static void fixName(Station station) {
        if( !station.getName().toUpperCase().equals(station.getName()) )
            return;
		char[] name = station.getName().toCharArray();
		boolean toUpper = true;
		for( int i = 0; i < name.length; i++ ) {
			if( !Character.isLetter(name[i]) )
				toUpper = true;
			else if( toUpper ) {
				name[i] = Character.toUpperCase(name[i]);
				toUpper = false;
			} else
				name[i] = Character.toLowerCase(name[i]);
		}
		String newName = new String(name);
		if( !station.getName().equals(newName) )
			logger.warning(String.format("Station '%s' renamed to '%s'", station.getName(), newName));
		station.setName(newName);
	}

	private final static Logger logger = Logger.getLogger(FixStations.class.getName());
}
