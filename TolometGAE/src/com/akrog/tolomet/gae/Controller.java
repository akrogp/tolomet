package com.akrog.tolomet.gae;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/motd")
public class Controller {
	//private Logger logger = Logger.getLogger("com.akrog.tolomet.gae");
	
	@GET	
	@Produces(MediaType.TEXT_PLAIN)
	public String getMotd(@QueryParam("version") int version, @QueryParam("stamp") long stamp ) {
		/*Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(stamp);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		logger.info("MOTD petition: version="+version+", stamp="+dateFormat.format(cal.getTime()));*/
		
		// Version
		Motd motd = new Motd();
		if( version < 17 ) {			
			motd.setVersion("0.17");
			if( version == 16 ) {
				motd.addChange("Mejorado soporte de AEMET");
			} else {
				motd.addChange("Incluidas estaciones de AEMET");
				motd.addChange("Incluido enlace a datos técnicos de la estación");
				motd.addChange("Posibilidad de buscar estaciones cercanas");
				motd.addChange("Posibilidad de buscar estaciones por categorías");
			}
		}
		if( version < 19 ) {
			motd.setVersion("0.19");
			motd.addChange("Añadida traducción a Euskera (gracias a Eneko Izquierdo y Fran Echarte)");
			motd.addChange("Añadida traducción a Inglés");
			motd.addChange("En el caso de AEMET (pocas lecturas) se muestran las últimas 24 horas");
		}
		if( version < 21 ) {
			motd.setVersion("0.21");
			if( version == 20 ) {
				motd.addChange("Ajustado intervalo de tiempo visualizado para La Rioja");
				motd.addChange("Actualizado enlace a datos de las estaciones de La Rioja");
			} else {
				motd.addChange("Añadidas estaciones del Gobierno de La Rioja (Raúl, me debes una caña ;P)");
				motd.addChange("Mejorado desplazamiento de ejes");
				motd.addChange("Arreglado fallo al rotar la pantalla mientras descarga");
			}
		}
		if( version < 23 ) {
			motd.setVersion("0.23");
			motd.addChange("Añadidas estaciones de MeteoGalicia (César, me debes una caña y una tapa ;P)");
			motd.addChange("Añadidas estaciones de Red Vigía (Santander y Santoña)");
		}
		if( version < 25 ) {
			motd.setVersion("0.25");
			if( version == 24 )
				motd.addChange("Solucionado fallo cuando meteocat no devuelve datos");
			else
				motd.addChange("Añadidas estaciones de Meteocat (otra caña/tapa que me deben ... ¿eh Jesus Tomas? ;P)");
		}
		if( version < 26 ) {
			motd.setVersion("0.26");
			motd.addChange("Soporte de zoom y scroll");
			motd.addChange("Permite ajustar los ejes de las gráficas");
		}
		/*if( version < 27 ) {
			motd.setVersion("0.27");
			motd.addChange("Añadida estación de Untzueta, perfecta para la zona de vuelo de Orozko. Eskerrik asko Kato!!");
		}*/
		if( version < 28 ) {
			motd.setVersion("0.28");
			motd.addChange("Solucionado fallo con la actualización de Euskalmet");
		}
		if( version < 200 ) {
			motd.setVersion("2.0");
			motd.addChange("Incluidas gráficas de temperatura y presión");
			motd.addChange("Incluidas nuevas estaciones");
			motd.addChange("Evitados saltos en gráfica de dirección (gracias Guille!)");
			motd.addChange("Ajustados tamaños según resolución de pantalla");
			motd.addChange("Incluidos modos simple y completo");
			motd.addChange("Incluida opción de actualización continua de los datos (gracias tocayo!)");
			motd.addChange("Incluido botón de ajustes cuando no está presente en el dispositivo");
			motd.addChange("Solucionados diferentes fallos");
		}
		if( version == 200 ) {
			motd.setVersion("2.0.1");
			motd.addChange("Solucionado fallo al migrar preferencias de velocidad a la versión 2.0");
		}
		
		// MOTD
		/*Calendar cal = Calendar.getInstance();        
        cal.set(2013,8-1,4,0,0);
        if( stamp < cal.getTimeInMillis() ) {
        	//motd.setMotd("Para que Tolomet funcione bien en todos los móviles, en caso de que os de algún fallo reportadlo junto con una descripción de cuándo ocurre");
        	//motd.setMotd("¡Usa la lista de favoritos, es más cómodo! Puedes añadir y quitar estaciones de favoritos tocando la estrella de arriba.");
        	motd.setMotd("Parece que AEMET tiene problemas con sus estaciones, esperemos que se solucione pronto");
        	motd.setStamp(cal.getTimeInMillis());
        }*/
        
		return motd.toString();
	}
}
