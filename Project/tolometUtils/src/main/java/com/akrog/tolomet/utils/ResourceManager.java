package com.akrog.tolomet.utils;

import com.akrog.tolomet.Region;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.providers.WindProviderType;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by gorka on 7/04/16.
 */
public class ResourceManager {
    public static void saveStation( Station station, String path ) throws IOException {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(path,true));
        dos.writeUTF(station.getCode());
        dos.writeUTF(station.getName());
        dos.writeInt(station.getProviderType().ordinal());
        dos.writeUTF(station.getCountry());
        dos.writeInt(station.getRegion());
        dos.writeDouble(station.getLatitude());
        dos.writeDouble(station.getLongitude());
        dos.close();
    }

    public static void saveStation( Station station ) throws IOException {
        saveStation(station, String.format("/home/gorka/stations_%s.dat", station.getCountry()));
    }

    public static List<Station> loadAllStations() {
        File dir = new File(path);
        String[] files = dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith("stations_");
            }
        });
        List<Station> stations = new ArrayList<Station>();
        for( String file : files )
            stations.addAll(loadStations(new File(path,file).getAbsolutePath()));
        return stations;
    }

    public static List<Station> loadCountryStations(String country) {
        return loadStations(String.format("%s/stations_%s.dat", path, country));
    }

    public static List<Station> loadStations(String path) {
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

    public static void showStation(Station station) {
        System.out.println(station.getCode());
        System.out.println(station.getName());
        System.out.println(station.getProviderType());
        System.out.println(station.getCountry());
        System.out.println(station.getRegion());
        System.out.println(station.getLatitude());
        System.out.println(station.getLongitude());
    }

    public static int selectRegion(String country) throws IOException {
        List<Region> regions = null;
        try {
            regions = loadRegions(country);
        } catch (Exception e ) {
            return 0;
        }
        System.out.println("Selecciona región:");
        for( Region region : regions )
            System.out.println(String.format("%d - %s", region.getCode(), region.getName()));
        int result;
        BufferedReader keyb = new BufferedReader(new InputStreamReader(System.in));
        do {
            result = Integer.parseInt(keyb.readLine());
        } while( result < 0 || result > regions.size() );
        return result;
    }

    public static List<Region> loadRegions(String country) throws IOException {
        List<Region> regions = new ArrayList<>();
        String line;
        String[] fields;
        try( BufferedReader rd = new BufferedReader(new FileReader(String.format("%s/regions_%s.csv",path,country))); ) {
            while ((line = rd.readLine()) != null) {
                fields = line.split(",");
                Region region = new Region();
                region.setName(fields[0]);
                region.setCode(Integer.parseInt(fields[1]));
                regions.add(region);
            }
        }
        return regions;
    }

    private static final Logger logger = Logger.getLogger(ResourceManager.class.getName());
    private static final String path = "/home/gorka/MyProjects/Android/Tolomet/Project/tolometCore/src/main/resources/res";
}
