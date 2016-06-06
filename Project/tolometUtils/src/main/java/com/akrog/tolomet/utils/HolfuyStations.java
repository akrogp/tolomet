package com.akrog.tolomet.utils;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.providers.WindProviderType;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import hu.holfuy.Stations;

/**
 * Created by gorka on 5/04/16.
 */
public class HolfuyStations {
    public HolfuyStations() throws MalformedURLException {
        url = new URL("http://holfuy.hu/en/takeit/xml/dezso/stations.xml");
        //url = new File("/home/gorka/MyProjects/Android/Tolomet/Docs/holfuy.xml").toURI().toURL();
    }

    public static void main( String[] args ) throws Exception {
        new HolfuyStations().getStations();
    }

    public List<Station> getStations() throws Exception {
        BufferedReader keyb = new BufferedReader(new InputStreamReader(System.in));
        JAXBContext jaxbContext = JAXBContext.newInstance(Stations.class);
        Unmarshaller um = jaxbContext.createUnmarshaller();
        Stations stations = (Stations)um.unmarshal(url);
        List<Station> result = new ArrayList<>();
        for(Stations.Station holfuy : stations.getSTATION() ) {
            if( !holfuy.getID().equals("s351") )
                continue;
            Station station = new Station();
            station.setCode(holfuy.getID());
            station.setName(holfuy.getNAME());
            Stations.Station.LOCATION location = holfuy.getLOCATION();
            System.out.println(String.format(Locale.ENGLISH, "%s (%s-%s) @ %f %f", station.getName(), location.getCOUNTRYNAME(), location.getCOUNTRY(), location.getLAT(), location.getLONG()));
            if( location.getLAT() == location.getLONG() && location.getLAT() == 0.0 ) {
                System.out.println(String.format("Skipped %s, unknown location", station.getName()));
                continue;
            }
            if( location.getCOUNTRY().length() != 2 )
                if( location.getCOUNTRY().toLowerCase().contains("voss") )
                    location.setCOUNTRY("NO");
                else {
                    System.out.print("Enter country: ");
                    location.setCOUNTRY(keyb.readLine());
                }
            station.setCountry(location.getCOUNTRY().toUpperCase());
            station.setLatitude(location.getLAT());
            station.setLongitude(location.getLONG());
            station.setProviderType(WindProviderType.Holfuy);
            station.setRegion(ResourceManager.selectRegion(station.getCountry()));
            System.out.println(String.format("\t%s (%s) - %d", station.getName(), station.getCountry(), station.getRegion()));
            result.add(station);
        }
        return result;
    }

    private final URL url;
}
