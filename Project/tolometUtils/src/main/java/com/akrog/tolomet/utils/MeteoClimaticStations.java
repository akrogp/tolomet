package com.akrog.tolomet.utils;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;
import com.akrog.tolomet.providers.WindProviderType;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gorka on 28/11/16.
 */

public class MeteoClimaticStations {
    public static void main( String[] args ) throws IOException, XmlPullParserException, SQLException, ClassNotFoundException {
        List<Station> stations = new ArrayList<>();
        stations.addAll(downloadStations("ESAND",167));
        stations.addAll(downloadStations("ESARA",168));
        stations.addAll(downloadStations("ESAST",169));
        stations.addAll(downloadStations("ESCTB",170));
        stations.addAll(downloadStations("ESCYL",172));
        stations.addAll(downloadStations("ESCLM",171));
        stations.addAll(downloadStations("ESCAT",173));
        stations.addAll(downloadStations("ESMUR",181));
        stations.addAll(downloadStations("ESMAD",180));
        stations.addAll(downloadStations("ESEUS",183));
        stations.addAll(downloadStations("ESEXT",175));
        stations.addAll(downloadStations("ESGAL",176));
        stations.addAll(downloadStations("ESIBA",177));
        stations.addAll(downloadStations("ESICA",178));
        stations.addAll(downloadStations("ESLRI",179));
        stations.addAll(downloadStations("ESNAF",182));
        stations.addAll(downloadStations("ESPVA",174));
        stations.addAll(downloadStations("PTNOR",1));
        stations.addAll(downloadStations("PTCEN",1));
        stations.addAll(downloadStations("PTSUR",1));
        stations.addAll(downloadStations("PTAZR",1));
        stations.addAll(downloadStations("PTMAD",1));
        System.out.println(String.format("%d stations downloaded", stations.size()));
        DbManager.addStations(stations);
        System.out.println("Added to DB!");
    }

    public static List<Station> downloadStations(String regionCode, int dbRegion) throws XmlPullParserException, IOException {
        List<Station> result = new ArrayList<>();

        Downloader downloader = new Downloader();
        downloader.setUrl("http://meteoclimatic.com/feed/rss/"+regionCode);
        String data = downloader.download();

        XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(new StringReader(data));

        int event = parser.getEventType();
        String text = null;
        String name = null, code = null;
        Double lat = null, lon = null;
        while( event != XmlPullParser.END_DOCUMENT ) {
            String tag = parser.getName();
            switch (event) {
                case XmlPullParser.TEXT:
                    text = parser.getText();
                    break;
                case XmlPullParser.END_TAG:
                    if( tag.equals("title") )
                        name = text.replaceAll(" \\(.*", "");
                    else if( tag.equals("link") )
                        code = text.replaceAll(".*/","");
                    else if( tag.equals("geo:lat") )
                        lat = Double.parseDouble(text);
                    else if( tag.equals("geo:long") )
                        lon = Double.parseDouble(text);
                    else if( tag.equals("item") ) {
                        //System.out.println(String.format("%s (%s-%s) @ %.1f, %.1f", name, code, code.substring(0,2), lat, lon));
                        Station station = new Station();
                        station.setCode(code);
                        station.setName(name);
                        station.setCountry(code.substring(0,2));
                        station.setRegion(dbRegion);
                        station.setLatitude(lat);
                        station.setLongitude(lon);
                        station.setProviderType(WindProviderType.MeteoClimatic);
                        result.add(station);
                    }
                    break;
            }
            event = parser.next();
        }
        System.out.println(String.format("%s - %d stations", regionCode, result.size()));
        return result;
    }
}
