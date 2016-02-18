package com.akrog.tolomet.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import uk.me.jstott.jcoord.LatLng;
import uk.me.jstott.jcoord.UTMRef;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.providers.WindProviderType;

public class FixStations {
	public static void main(String[] args) throws IOException {
		logger.info("Started");
		String inPath = "/home/gorka/MyProjects/Android/Tolomet/Project/tolometCore/src/main/resources/res/stations_ES.dat";
		//String inPath = "/home/gorka/MyProjects/Android/Tolomet/Docs/stations_world.dat";
		List<Station> stations = loadStations(inPath);
		addNewStations(stations);
		sortStations(stations);
		for( Station station : stations ) {
			fixName(station);
			showStation(station);
			saveStation(station);
		}
		logger.info(String.format("Finished: %d stations",stations.size()));
	}		

	private static void addNewStations(List<Station> stations) {
		try {
			//stations.addAll(MetarStations.loadMetars());
			//stations.addAll(PradesStations.getStations());
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
		showStation(station);
		return askParameter("¿OK? (s/n)").equalsIgnoreCase("s") ? station : null;
	}
	
	private static void showStation(Station station) {
		System.out.println(station.getCode());
		System.out.println(station.getName());
		System.out.println(station.getProviderType());
		System.out.println(station.getCountry());
		System.out.println(station.getRegion());
		System.out.println(station.getLatitude());
		System.out.println(station.getLongitude());		
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
	
	private static List<Station> loadStations(String path) {
		List<Station> stations = new ArrayList<Station>();
		Set<String> names = new HashSet<String>();
		Station station;
		DataInputStream dis = null;
		try {
			dis = new DataInputStream(new FileInputStream(path));		
			while (true) {
				station = new Station();
				station.setCode(dis.readUTF());
				station.setName(dis.readUTF());
				station.setProviderType(WindProviderType.values()[dis.readInt()]);
				station.setCountry(dis.readUTF());
				station.setRegion(dis.readInt());
				station.setLatitude(dis.readDouble());
				station.setLongitude(dis.readDouble());				
				if( names.add(station.getCode()) )
					if( station.getLatitude() == 99.0+99.0/60.0 )
						logger.warning(String.format("Invalid coordinates for '%s'", station));
					else
						stations.add(station);
				else
					logger.warning(String.format("Filtered duplicated station '%s'", station));
			}
		} catch (Exception e) {
			if (dis != null)
				try {
					dis.close();
				} catch (IOException e1) {
				}
		}
		return stations;
	}

	private static void saveStation( Station station ) throws IOException {
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(String.format("/home/gorka/stations_%s.dat", station.getCountry()),true));
		dos.writeUTF(station.getCode());
		dos.writeUTF(station.getName());
		dos.writeInt(station.getProviderType().ordinal());
		dos.writeUTF(station.getCountry());
		dos.writeInt(station.getRegion());
		dos.writeDouble(station.getLatitude());
		dos.writeDouble(station.getLongitude());
		dos.close();
	}

	private final static Logger logger = Logger.getLogger(FixStations.class.getName());
}
