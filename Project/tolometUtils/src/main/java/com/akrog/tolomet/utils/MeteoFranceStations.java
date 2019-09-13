package com.akrog.tolomet.utils;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.providers.WindProviderType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gorka on 18/07/16.
 */
public class MeteoFranceStations {
    public MeteoFranceStations() throws MalformedURLException {
        //url = new URL("http://www.vigimeteo.com/PREV/obs/obsd2i_meta.txt");
        url = new File("/home/gorka/MyProjects/Android/Tolomet/Project/tolometUtils/src/main/resources/data/vigimeteo.csv").toURI().toURL();
    }

    public static void main( String[] args ) throws Exception {
        System.out.println(new MeteoFranceStations().getStations().size());
    }

    public List<Station> getStations() throws Exception {
        List<Station> result = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(url.getFile()));
        String line;
        String[] fields;
        while( (line=br.readLine()) != null ) {
            fields = line.split(";");
            if( fields.length < 6 || fields[5].equals("0") )
                continue;
            Station station = new Station();
            station.setProviderType(WindProviderType.MeteoFrance);
            station.setCountry("FR");
            station.setCode(fields[0]);
            station.setName(fields[4]);
            station.setLongitude(Float.parseFloat(fields[1]));
            station.setLatitude(Float.parseFloat(fields[2]));
            result.add(station);
        }
        br.close();
        return result;
    }

    private final URL url;
}
