package com.akrog.tolomet.utils;

import com.akrog.tolomet.Manager;
import com.akrog.tolomet.Region;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.providers.WindProviderType;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class FixRegions {
	public static void main( String[] args ) throws IOException {
		Manager tolomet = new Manager();		
		List<Station> stations = null;//tolomet.getAllStations();
		List<Region> regions = null;//tolomet.getRegions();
		DataOutputStream dos = new DataOutputStream(new FileOutputStream("/home/gorka/stations.dat"));
		for( Station station : stations ) {
			System.out.println(String.format("%s ... ", station.toString()));
			fixStation(station, regions);
			saveStation(dos,station);
		}
		System.out.println("finished!");
		dos.close();
	}

	private static void fixStation(Station station, List<Region> regions) throws NumberFormatException, IOException {
		if( !station.getCountry().equals("ES") || !station.getProviderType().equals(WindProviderType.Metar) )
			return;
		for( Region region : regions )
			System.out.println(String.format("%d\t%s", region.getCode(), region.getName()));
		System.out.print(String.format("Select region for station '%s': ", station.getName()));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int code = Integer.parseInt(br.readLine());
		System.out.println(String.format("Using %d ...", code));
		station.setRegion(code);
	}

	private static void saveStation( DataOutputStream os, Station station ) throws IOException {
		os.writeUTF(station.getCode());
		os.writeUTF(station.getName());
		os.writeInt(station.getProviderType().ordinal());
		os.writeUTF(station.getCountry());
		//os.writeUTF("ES");
		os.writeInt(station.getRegion());
		os.writeDouble(station.getLatitude());
		os.writeDouble(station.getLongitude());
	}
}
