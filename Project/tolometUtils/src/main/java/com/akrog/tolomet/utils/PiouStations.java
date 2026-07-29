package com.akrog.tolomet.utils;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.providers.WindProviderType;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by gorka on 8/04/16.
 */
public class PiouStations {
    public static void main( String[] args ) throws Exception {
        for( Station station : getStations() ) {
            ResourceManager.showStation(station);
            System.out.println("--------------");
        }
    }

    public static List<Station> getStations() throws Exception {
        GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyBeNcmTrrdsKHX45-QQVpn1eGehSaLrG3k");
        List<Station> result = new ArrayList<>();
        JSONObject json = new JSONObject(loadJson());
        JSONArray array = json.getJSONArray("data");
        for( int i = 0; i < array.length(); i++ ) {
        //for( int i = 0; i < 50; i++ ) {
            JSONObject item = array.getJSONObject(i);
            String id = item.getString("id");
            /*if( !id.equals("229") )
                continue;*/
            if( id.equals("null") )
                continue;
            JSONObject status = item.getJSONObject("status");
            if( status.getString("state").equalsIgnoreCase("off") )
                continue;
            if( !status.getString("date").startsWith("2016") )
                continue;
            JSONObject meta = item.getJSONObject("meta");
            JSONObject location = item.getJSONObject("location");
            Station station = new Station();
            station.setCode(id);
            station.setName(meta.getString("name"));
            station.setLatitude(location.getDouble("latitude"));
            station.setLongitude(location.getDouble("longitude"));
            if( station.getLatitude() == 0.0 && station.getLongitude() == 0.0 )
                continue;
            GeocodingResult[] geos = GeocodingApi.reverseGeocode(context, new LatLng(station.getLatitude(), station.getLongitude())).await();
            for( GeocodingResult geo : geos )
                for (AddressComponent address : geo.addressComponents)
                    for (AddressComponentType type : address.types)
                        if (type.name().equalsIgnoreCase("country")) {
                            station.setCountry(address.shortName);
                            break;
                        }
            station.setRegion(ResourceManager.selectRegion(station.getCountry()));
            station.setProviderType(WindProviderType.PiouPiou);
            result.add(station);
        }
        return result;
    }

    // http://api.pioupiou.fr/v1/live-with-meta/all
    private static String loadJson() throws Exception {
        try(Scanner scanner = new Scanner(PiouStations.class.getResourceAsStream("/res/piou-20180430.json"), "UTF-8")) {
            return scanner.useDelimiter("\\A").next();
        }
    }
}
