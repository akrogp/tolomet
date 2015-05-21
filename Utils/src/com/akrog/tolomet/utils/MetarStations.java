package com.akrog.tolomet.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.providers.WindProviderType;

public class MetarStations {
	public static void main( String[] args ) throws IOException {
		for( Station station : loadMetars() )
			System.out.println(String.format("%s ... ", station.toString()));
		System.out.println("finished!");
	}
	
	public static List<Station> loadMetars() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("/home/gorka/MyProjects/Android/Tolomet/Docs/metar.txt"));		
		List<Station> list = new ArrayList<Station>();
		Map<String, Set<String>> mapRegions = new HashMap<String, Set<String>>();
		String line, region = null, country, code;
		while( (line=br.readLine()) != null ) {
			if( line.isEmpty() || line.startsWith("!") || line.startsWith("CD") )
				continue;			
			if( line.length() < 83 ) {
				region = line.substring(0, 19).trim();
				System.out.println(String.format("Region '%s'", region));
				continue;
			}
			code = line.substring(20, 24).trim();
			if( code.isEmpty() )
				continue;
			country = line.substring(81, 83);
			Set<String> regions = mapRegions.get(country);
			if( regions == null ) {
				regions = new LinkedHashSet<String>();
				mapRegions.put(country, regions);
			}
			regions.add(region);
			Station station = new Station();
			station.setProviderType(WindProviderType.Metar);
			station.setCode(code);
			station.setCountry(country);
			station.setName(line.substring(3, 20).trim());
			station.setLatitude(parseLatitude(line));
			station.setLongitude(parseLongitude(line));
			list.add(station);
			station.setRegion(regions.size());			
		}
		br.close();
		saveRegions(mapRegions);		
		return list;
	}
		
	private static void saveRegions(Map<String, Set<String>> mapRegions) throws FileNotFoundException {
		for( Entry<String, Set<String>> entry : mapRegions.entrySet() ) {
			if( entry.getValue().size() < 2  )
				continue;
			PrintWriter pw = new PrintWriter(String.format("/home/gorka/regions_%s.csv", entry.getKey()));
			int count = 1;
			for( String region : entry.getValue() )
				pw.println(String.format("%s,%d", region, count++));
			pw.close();
		}		
	}

	private static double parseLongitude(String line) {
		double degrees = Integer.parseInt(line.substring(47, 50).trim());
		double minutes = Integer.parseInt(line.substring(51, 53).trim());
		double sig = line.charAt(53) == 'W' ? -1 : 1;
		return sig*(degrees+minutes/60);
	}

	private static double parseLatitude(String line) {
		double degrees = Integer.parseInt(line.substring(39, 41).trim());
		double minutes = Integer.parseInt(line.substring(42, 44).trim());
		double sig = line.charAt(44) == 'N' ? 1 : -1;
		return sig*(degrees+minutes/60);
	}
	
	//private static final Logger logger = Logger.getLogger(BrokenStations.class.getName());
}
