package com.akrog.tolomet.utils;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import com.akrog.tolomet.Manager;
import com.akrog.tolomet.Station;

public class MigrateStations {
	public static void main( String[] args ) throws IOException {
		Manager tolomet = new Manager(Locale.getDefault().getLanguage());
		DataOutputStream dos = new DataOutputStream(new FileOutputStream("/home/gorka/stations.dat"));
		for( Station station : tolomet.getAllStations() ) {
			System.out.println(String.format("%s ... ", station.toString()));
			saveStation(dos,station);
		}
		System.out.println("finished!");
		dos.close();
	}
		
	private static void saveStation( DataOutputStream os, Station station ) throws IOException {
		os.writeUTF(station.getCode());
		os.writeUTF(station.getName());
		os.writeInt(station.getProviderType().ordinal());
		//os.writeUTF(station.getCountry());
		os.writeUTF("ES");
		os.writeInt(station.getRegion());
		os.writeDouble(station.getLatitude());
		os.writeDouble(station.getLongitude());
	}
	
	//private static final Logger logger = Logger.getLogger(BrokenStations.class.getName());
}
