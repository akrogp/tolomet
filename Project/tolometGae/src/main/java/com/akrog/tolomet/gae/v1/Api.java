package com.akrog.tolomet.gae.v1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/motd")
public class Api {
	//private Logger logger = Logger.getLogger("com.akrog.tolomet.gae");

	@GET	
	@Produces(MediaType.TEXT_PLAIN)
	public String getMotd(@QueryParam("version") int version, @QueryParam("stamp") long stamp, @QueryParam("lang") String lang, @QueryParam("api") int api ) {
		/*Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(stamp);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		logger.info("MOTD petition: version="+version+", stamp="+dateFormat.format(cal.getTime()));*/
		
		// Version
		Motd motd = new Motd();

        if( api == 0 || api >= 14 ) {
            //changesv2(version, motd);
            //changesv3(version, lang, motd);
            changesv5(version, lang, motd);
        }
		else if( api < 9 )
            changesApi7(version, lang, motd);
        else
            changesApi9(version, lang, motd);

		// MOTD
		/*Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
        cal.set(2016,10-1,10,0,0);
        if( stamp < cal.getTimeInMillis() ) {
        	if( lang == null || isSpanish(lang) )
        		//motd.setMotd("En caso de que Tolomet falle y se cierre ¡da al botón de eviar reporte! Limpiar los datos de la aplicación o reinstalar debería solucionarlo.");
				motd.setMotd("El próximo jueves 13/oct se prevee que no funcionen los servidores de MeteoGalicia entre las 14:00 y las 18:00 por tareas de mantenimiento.");
        	else
        		//motd.setMotd("In case Tolomet crashes, click on send report! Cleaning app data or reinstalling should fix most issues.");
				motd.setMotd("Next tuesday oct/13 MeteoGalicia servers will not be available between 14:00 and 18:00 due to maintenance.");
        	motd.setStamp(cal.getTimeInMillis());
        }*/
        
		return motd.toString();
	}

    private void changesApi7(int version, String lang, Motd motd) {
        if( version < 413 ) {
            motd.setVersion("4.1.3");
            motd.addChange(tr(lang,"Mejorado modo vuelo", "Improved fly mode"));
            motd.addChange(tr(lang,"Solucionado fallo con Euskalmet", "Solved bug with Euskalmet"));
        }
    }

    private void changesApi9(int version, String lang, Motd motd) {
        if( version < 451 ) {
            motd.setVersion("4.5.1");
            motd.addChange(tr(lang,"Mejorado modo vuelo", "Improved fly mode"));
        }
    }

    private void changesv5(int version, String lang, Motd motd) {
    }

    private void changesv4(int version, String lang, Motd motd) {
		if( version < 400 ) {
			motd.setVersion("4.0");
			motd.addChange(tr(lang,"Nueva interfaz de usuario", "New user interface"));
			motd.addChange(tr(lang,"Opciones para compartir lecturas","Support for sharing readings"));
			motd.addChange(tr(lang,"Añadido modo vuelo","Added a flight mode"));
			motd.addChange(tr(lang,"Localización en mapa","Location in map"));
			motd.addChange(tr(lang,"Desplazarse por lecturas","Scroll of readings"));
		}
		if( version == 400 ) {
			motd.setVersion("4.0.2");
			motd.addChange(tr(lang,"Solucionado problema con estaciones de Meteocat", "Solved problem with Meteocat stations"));
		}
		if( version == 401 ) {
			motd.setVersion("4.0.2");
			motd.addChange(tr(lang,"Añadida gráfica de vel. máx. en Meteocat", "Included speed max. chart in Meteocat"));
		}
		if( version < 410 ) {
			motd.setVersion("4.1");
			motd.addChange(tr(lang, "Mejorado mapa", "Map improved"));
			motd.addChange(tr(lang,"Integrado navegador", "Web browser embedded"));
		}
		if( version == 414 ) {
			motd.setVersion("4.1.5");
			motd.addChange(tr(lang,"Solucionado problema con el mapa", "Map bug solved"));
			motd.addChange(tr(lang,"Recuperados iconos de la barra", "Recovered toolbar icons"));
		}
		if( version == 415 ) {
			motd.setVersion("4.1.6");
			motd.addChange(tr(lang,"Recuperada opción de reportar", "Report button recovered"));
		}
		if( version < 420 ) {
			motd.setVersion("4.2");
			motd.addChange(tr(lang, "Integrada web original", "Provider site access"));
			motd.addChange(tr(lang,"Pequeñas mejoras", "Small improvements"));
		}
		if( version < 430 ) {
			motd.setVersion("4.3");
			motd.addChange(tr(lang, "Incluidas estaciones Holfuy (ej: Aloña)", "Included Holfuy stations"));
			motd.addChange(tr(lang,"Reorganizadas lecturas del resumen", "Reordered summary readings"));
			motd.addChange(tr(lang,"Mejorado soporte internacional", "Improved international support"));
		}
		if( version < 440 ) {
			motd.setVersion("4.4");
			motd.addChange(tr(lang,"Incluidas estaciones PiouPiou", "Included PiouPiou stations"));
		}
		if( version == 440 ) {
			motd.setVersion("4.4.1");
			motd.addChange(tr(lang,"Incluida estación PiouPiou 229 (Navarra)", "Included PiouPiou station 229 (Navarra)"));
		}
		if( version == 441 ) {
			motd.setVersion("4.4.2");
			motd.addChange(tr(lang,"Incluida estación Holfuy de Udalaitz", "Included Udalaitz Holfuy station"));
			motd.addChange(tr(lang,"Actualizado al nuevo formato de Red Vigía", "Updated to the new Red Vigía format"));
		}
		if( version < 450 ) {
			motd.setVersion("4.5");
			motd.addChange(tr(lang,"Incluidas más estaciones de Francia (FFVL y MeteoFrance)", "Included more French stations (FFVL and MeteoFrance)"));
		}
	}

	private void changesv3(int version, String lang, Motd motd) {
		if( version < 300 ) {
			motd.setVersion("3.0");
			motd.addChange(tr(lang,"Incluida opción para cambiar de país","Included option to change country"));
			motd.addChange(tr(lang,"Incluidos más de 9000 aeropuertos de todo el mundo (gracias a Ale)","Included more than 9000 wolrd-wide airports (thanks Ale)"));
			motd.addChange(tr(lang,"Incluidas estaciones de MeteoPrades (gracias a Eduard y Mario)","Included MeteoPrades (Spain) stations (thanks Eduard & Mario)"));
		}
		if( version < 301 ) {
			motd.setVersion("3.0.1");
			motd.addChange(tr(lang,"Configurada zona horaria canaria en AEMET","Configured canary time zone in AEMET"));
		}
		if( version < 302 ) {
			motd.setVersion("3.0.2");
			motd.addChange(tr(lang,"Solucionados fallos reportados por usuarios","Minor fixes reported by users"));
		}
		if( version < 303 ) {
			motd.setVersion("3.0.3");
			motd.addChange(tr(lang,"Incluida estación de Euskalmet para Kanpezu","Included Euskalmet station for Kanpezu"));
		}
		if( version < 304 ) {
			motd.setVersion("3.0.4");
			motd.addChange(tr(lang,"Incluida estación de Euskalmet para Ilarduia","Included Euskalmet station for Ilarduia"));
		}
	}

	private void changesv2(int version, Motd motd) {
		if( version < 202 ) {
			motd.setVersion("2.0.2");
			motd.addChange("Incluidas gráficas de temperatura y presión");
			motd.addChange("Incluidas nuevas estaciones");
			motd.addChange("Evitados saltos en gráfica de dirección (gracias Guille!)");
			motd.addChange("Ajustados tamaños según resolución de pantalla");
			motd.addChange("Incluidos modos simple y completo");
			motd.addChange("Incluida opción de actualización continua de los datos (gracias tocayo!)");
			motd.addChange("Incluido botón de ajustes cuando no está presente en el dispositivo");
			motd.addChange("Solucionados diferentes fallos");
		}
		if( version < 211 ) {
			motd.setVersion("2.1.1");
			motd.addChange("Recuperadas estaciones de meteocat");
			motd.addChange("Incluida estación del club naútico de Laredo");
			motd.addChange("Ajustado dinámicamente tamaño de fuente en últimas lecturas");
			motd.addChange("Arreglados algunos fallos");
		}
		if( version < 213 ) {
			motd.setVersion("2.1.3");
			motd.addChange("Solucionado con estaciones de AEMET que dan lecturas parciales");
		}
		if( version < 214 ) {
			motd.setVersion("2.1.4");
			motd.addChange("Solucionado fallo en algunos móviles al acceder a Euskalmet");
		}
	}
	
	private String tr(String lang, String es, String en) {
		if( lang == null || isSpanish(lang) )
			return es != null ? es : en;
		return en != null ? en : es;
	}
	
	private boolean isSpanish( String lang ) {
		if( lang == null )
			return false;
		return lang.equalsIgnoreCase("es") || lang.equalsIgnoreCase("eu") || lang.equalsIgnoreCase("ca") || lang.equalsIgnoreCase("gl") || lang.equalsIgnoreCase("ast");
	}
}
