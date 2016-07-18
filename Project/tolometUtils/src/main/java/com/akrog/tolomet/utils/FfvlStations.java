package com.akrog.tolomet.utils;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.providers.WindProviderType;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import fr.ffvl.Balises;

/**
 * Created by gorka on 18/07/16.
 */
public class FfvlStations {
    public FfvlStations() throws MalformedURLException {
        //url = new URL("http://data.ffvl.fr/xml/4D6F626942616C69736573/meteo/balise_list.xml");
        url = new File("/home/gorka/MyProjects/Android/Tolomet/Project/tolometUtils/src/main/resources/res/ffvl.xml").toURI().toURL();
    }

    public static void main( String[] args ) throws Exception {
        System.out.println(new FfvlStations().getStations().size());
    }

    public List<Station> getStations() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(Balises.class);
        Unmarshaller um = jaxbContext.createUnmarshaller();
        Balises balises = (Balises)um.unmarshal(url);
        List<Station> result = new ArrayList<>();
        for(Balises.Balise balise : balises.getBalise() ) {
            Station station = new Station();
            station.setProviderType(WindProviderType.Ffvl);
            station.setCountry("FR");
            station.setName(balise.getNom());
            station.setCode(balise.getIdBalise()+"");
            station.setLatitude(balise.getCoord().getLat());
            station.setLongitude(balise.getCoord().getLon());
            result.add(station);
        }
        return result;
    }

    private final URL url;
}
