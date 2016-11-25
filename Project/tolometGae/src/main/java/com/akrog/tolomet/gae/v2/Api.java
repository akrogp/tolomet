package com.akrog.tolomet.gae.v2;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Created by gorka on 10/11/16.
 */

@Path("/v2")
public class Api {
    @Path("/checkNotifications")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Notification checkNotifications(
            @QueryParam("apiLevel") Integer apiLevel,
            @QueryParam("appVersion") Integer appVersion, @QueryParam("dbVersion") Integer dbVersion,
            @QueryParam("stamp") Long stamp, @QueryParam("lang") String lang ) {
        Notification info;

        if( appVersion != null && (info=checkAppVersion(appVersion, lang)) != null )
            return info;
        if( dbVersion != null && (info=checkDbVersion(dbVersion)) != null )
            return info;
        if( stamp != null && (info=checkMotd(stamp, lang)) != null )
            return info;

        return null;
    }

    private Notification checkAppVersion(int appVersion, String lang) {
        if( appVersion >= 501 )
            return null;
        Notification info = new Notification();

        if( appVersion < 501 ) {
            info.setAppVersion("5.0.1");
            addImprovement(info, lang,
                    "Solucionados fallos reportados de la versión 5.0",
                    "Solved reported bug of version 5.0");
        }

        return info;
    }

    private Notification checkDbVersion(int dbVersion) {
        /*if( dbVersion >= 6 )
            return null;
        Notification info = new Notification();

        if( dbVersion < 6 ) {
            info.setDbVersion(6);
            addStation(info, "XXX", "Prueba2", "País Vasco", "ES", 0.0, 0.0, "Euskalmet", Station.Action.REMOVE);
        }

        return info;*/
        return null;
    }

    private Notification checkMotd(long stamp, String lang) {
        /*if( stamp >= d("08/11/2016") )
            return null;
        Notification info = new Notification();
        info.setStamp(d("08/11/2016"));
        info.setMotd(tr(lang,"¡Bienvenido a la versión 5.0 de Tolomet!","Welcome to Tolomet v5.0!"));
        return info;*/
        return null;
    }

    private String tr(String lang, String es, String en) {
        if( lang == null || isSpanish(lang) )
            return es != null ? es : en;
        return en != null ? en : es;
    }

    private long d( String format ) {
        try {
            return df.parse(format).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void addImprovement(Notification info, String lang, String es, String en) {
        List<String> list = info.getImprovements();
        if( list == null ) {
            list = new ArrayList<>();
            info.setImprovements(list);
        }
        list.add(tr(lang,es,en));
    }

    private boolean isSpanish( String lang ) {
        if( lang == null )
            return false;
        return lang.equalsIgnoreCase("es") || lang.equalsIgnoreCase("eu") || lang.equalsIgnoreCase("ca") || lang.equalsIgnoreCase("gl") || lang.equalsIgnoreCase("ast");
    }

    private void addStation(Notification info, String code, String name, String region, String country, double lat, double lon, String prov, Station.Action action) {
        List<Station> list = info.getStations();
        if( list == null ) {
            list = new ArrayList<>();
            info.setStations(list);
        }
        Station station = new Station();
        station.setCode(code);
        station.setName(name);
        station.setRegion(region);
        station.setCountry(country);
        station.setLatitude(lat);
        station.setLongitude(lon);
        station.setProvider(prov);
        station.setAction(action);
        list.add(station);
    }

    private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
}