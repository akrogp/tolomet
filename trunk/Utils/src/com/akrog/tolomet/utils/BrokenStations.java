package com.akrog.tolomet.utils;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import com.akrog.tolomet.Manager;
import com.akrog.tolomet.Station;

public class BrokenStations {
	public static void main( String[] args ) throws IOException {
		Manager tolomet = new Manager(Locale.getDefault().getLanguage());
		DataOutputStream ok = new DataOutputStream(new FileOutputStream("stations.dat"));
		DataOutputStream ko = new DataOutputStream(new FileOutputStream("badstations.dat"));
		boolean broken;
		for( Station station : tolomet.getAllStations() ) {
			System.out.print(String.format("%s ... ", station.toString()));
			broken = checkBroken(station);
			System.out.println(broken?"KO":"OK");
			saveStation(broken?ko:ok, station);
		}
		ok.close();
		ko.close();
	}
	
	private static boolean checkBroken( Station station ) {
		boolean broken;
		int retries = 3;
		do {
			try {
				station.getProvider().refresh(station);
				broken = station.isEmpty();
			} catch( Exception e ) {
				broken = true;
			}
			if( broken && retries > 1 )
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
		} while(broken && --retries > 0);
		return broken;
	}
	
	private static void saveStation( DataOutputStream os, Station station ) throws IOException {
		os.writeUTF(station.getCode());
		os.writeUTF(station.getName());
		os.writeInt(station.getProviderType().ordinal());
		os.writeInt(station.getRegion());
		os.writeDouble(station.getLatitude());
		os.writeDouble(station.getLongitude());
	}
	
	//private static final Logger logger = Logger.getLogger(BrokenStations.class.getName());
}
