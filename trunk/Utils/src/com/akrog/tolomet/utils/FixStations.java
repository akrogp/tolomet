package com.akrog.tolomet.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.providers.WindProviderType;

public class FixStations {
	public static void main(String[] args) throws IOException {
		logger.info("Started");
		List<Station> stations = loadStations();
		sortStations(stations);
		for( Station station : stations ) {
			fixName(station);
			saveStation(station);
		}
		logger.info(String.format("Finished: %d stations",stations.size()));
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
	
	private static List<Station> loadStations() {
		List<Station> stations = new ArrayList<Station>();
		Set<String> names = new HashSet<String>();
		Station station;
		DataInputStream dis = null;
		try {
			//dis = new DataInputStream(new FileInputStream("/home/gorka/MyProjects/Android/Tolomet/TolometCore/src/res/stations_ES.dat"));
			dis = new DataInputStream(new FileInputStream("/home/gorka/MyProjects/Android/Tolomet/Docs/stations_world.dat"));		
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
