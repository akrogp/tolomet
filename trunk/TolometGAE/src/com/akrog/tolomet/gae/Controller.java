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
