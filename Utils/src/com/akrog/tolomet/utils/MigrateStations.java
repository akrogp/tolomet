package com.akrog.tolomet.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.akrog.tolomet.Manager;
import com.akrog.tolomet.Station;

public class MigrateStations {
	public static void main( String[] args ) throws IOException {
		Manager tolomet = new Manager();		
		List<Station> stations = new ArrayList<Station>(tolomet.getAllStations());
		stations.addAll(loadMetars());
		Collections.sort(stations, new Comparator<Station>() {
			@Override
			public int compare(Station o1, Station o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		DataOutputStream dos = new DataOutputStream(new FileOutputStream("/home/gorka/stations.dat"));
		for( Station station : stations ) {
			System.out.println(String.format("%s ... ", station.toString()));
			saveStation(dos,station);
		}
		System.out.println("finished!");
		dos.close();
	}
	
	private static List<Station> loadMetars() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("/home/gorka/MyProjects/Android/Tolomet/Docs/metar.txt"));
		List<Station> list = new ArrayList<Station>();
		br.close();
		return list;
	}
		
	private static void saveStation( DataOutputStream os, Station station ) throws IOException {
		os.writeUTF(station.getCode());
		os.writeUTF(station.getName());
		os.writeInt(station.getProviderType().ordinal());
		os.writeUTF(station.getCountry());
		os.writeUTF("ES");
		os.writeInt(station.getRegion());
		os.writeDouble(station.getLatitude());
		os.writeDouble(station.getLongitude());
	}
	
	//private static final Logger logger = Logger.getLogger(BrokenStations.class.getName());
}
