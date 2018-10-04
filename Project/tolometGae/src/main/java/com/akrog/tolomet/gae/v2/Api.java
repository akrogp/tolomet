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
        if( appVersion >= 580 )
            return null;
        Notification info = new Notification();

        if( appVersion < 501 ) {
            info.setAppVersion("5.0.1");
            addImprovement(info, lang,
                    "Solucionados fallos reportados de la versión 5.0",
                    "Solved reported bug of version 5.0");
        }
        if( appVersion < 510 ) {
            info.setAppVersion("5.1");
            addImprovement(info, lang,
                    "Incluidas 996 estaciones de MeteoClimatic",
                    "Included 996 MeteoClimatic stations");
        }
        if( appVersion < 520 ) {
            info.setAppVersion("5.2");
            addImprovement(info, lang,
                    "Habilitada instalación en memoria externa",
                    "Enabled installation on external storage");
            addImprovement(info, lang,
                    "Solucionados algunos fallos",
                    "Solved several bugs");
        }
        if( appVersion < 530 ) {
            info.setAppVersion("5.3");
            addImprovement(info, lang,
                    "Traducción a francés (por Jean Lombart)",
                    "French translation (by Jean Lombart)");
        }
        if( appVersion == 530 ) {
            info.setAppVersion("5.3.1");
            addImprovement(info, lang,
                    "Solucionados algunos fallos",
                    "Solved several bugs");
        }
        if( appVersion == 530 || appVersion == 531 ) {
            info.setAppVersion("5.3.2");
            addImprovement(info, lang,
                    "Solucionado fallo de actualización de lecturas",
                    "Solved readings update bug");
            addImprovement(info, lang,
                    "Recuperada estación de Laredo (RCNL)",
                    "Recovered Laredo (RCNL) station");
        }
        if( appVersion < 540 ) {
            info.setAppVersion("5.4");
            addImprovement(info, lang,
                    "Recuperadas estaciones de MeteoFrance",
                    "Recovered MeteoFrance stations");
            addImprovement(info, lang,
                    "Añadida estación de Piedrahíta (Peñanegra)",
                    "Included Piedrahíta (Peñanegra) station");
            addImprovement(info, lang,
                    "Añadida estación de Arcones",
                    "Included Arcones station");
        }
        if( appVersion < 550 ) {
            info.setAppVersion("5.5");
            addImprovement(info, lang,
                    "Añadidas estaciones de Arkauti y Ernio",
                    "Included Arkauti and Ernio stations");
            addImprovement(info, lang,
                    "Posibilidad de compartir enlaces de Tolomet",
                    "Support for sharing Tolomet links");
        }
        if( appVersion == 550 ) {
            info.setAppVersion("5.5.1");
            addImprovement(info, lang,
                    "Solucionado fallo al abrir enlaces de Tolomet",
                    "Bugfix openning Tolomet links");
        }
        if( appVersion < 560 ) {
            info.setAppVersion("5.6");
            addImprovement(info, lang,
                    "Añadida estación amateur de Santander",
                    "Included amateur station at Santander");
        }
        if( appVersion < 570 ) {
            info.setAppVersion("5.7");
            addImprovement(info, lang,
                    "Añadida estación PiouPiou de Peyragudes",
                    "Included PiouPiou station at Peyragudes");
            addImprovement(info, lang,
                    "Solucionado problema con hora canaria en AEMET",
                    "Solved problem with canary AEMET stations");
        }
        if( appVersion == 570 ) {
            info.setAppVersion("5.7.1");
            addImprovement(info, lang,
                    "Añadida estación de La Covatilla",
                    "Included La Covatilla station");
            addImprovement(info, lang,
                    "Solucionados problemas con algunas estaciones",
                    "Solved problems with several stations");
        }
        if( appVersion < 580 ) {
            info.setAppVersion("5.8.0");
            addImprovement(info, lang,
                    "Solucionado problema con estaciones de MeteoNavarra",
                    "Solved problem with MeteoNavarra stations");
            addImprovement(info, lang,
                    "Información de permisos",
                    "Information about permissions");
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