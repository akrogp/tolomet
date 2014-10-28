package com.akrog.tolomet.providers;

import java.util.Calendar;
import java.util.TimeZone;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;
import com.akrog.tolomet.io.ExcelDownloader;

public class LaRiojaProvider implements WindProvider {
	@Override
	public void refresh(Station station) {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		String speed = download(station.getCode(), now, 7);
		if( speed == null )
			return;
		String direction = download(station.getCode(), now, 8);
		if( direction == null )
			return;
		String humidity = download(station.getCode(), now, 2);
		if( humidity == null )
			return;
		updateStation(station, speed, direction, humidity);
	}

	@Override
	public void cancel() {
		downloader.cancel();
	}

	@Override
	public int getRefresh(String code) {
		return 15;
	}
	
	@Override
	public String getInfoUrl(String code) {
		return "http://ias1.larioja.org/estaciones/estaciones/mapa/consulta/consulta.jsp?codOrg=1&codigo="+code;
	}
	
	private String download(String code, Calendar now, int codigoP ) {
		downloader = new ExcelDownloader();
		downloader.setUrl("http://ias1.larioja.org/estaciones/estaciones/mapa/informes/ExportarDatosServlet");
		downloader.addParam("direccion", "/opt/tomcat/webapps/estaciones/estaciones");
		downloader.addParam("codOrg","1");
		downloader.addParam("codigo",code);
		downloader.addParam("codigoP",codigoP);
		downloader.addParam("Seleccion","D");
		downloader.addParam("Ano",now.get(Calendar.YEAR));
		downloader.addParam("Mes",now.get(Calendar.MONTH)+1);
		downloader.addParam("DiaD",now.get(Calendar.DAY_OF_MONTH));
		downloader.addParam("DiaH",now.get(Calendar.DAY_OF_MONTH));
		downloader.addParam("Informe","Y");
		downloader.addParam("extension","xls");
		return downloader.download();
	}

	protected void updateStation(Station station, String speed, String direction, String humidity) {
		String[] speedLines = speed.split("\n");
		String[] directionLines = direction.split("\n");
		String[] humidityLines = humidity.split("\n");
		if( speedLines.length < 9 || directionLines.length < 9 )
			return;
		
		station.clear();
		
		String[] cols;
		long date;
		Number num;
		
		for( int i = 8; i < speedLines.length; i++ ) {
			cols = speedLines[i].split("\\|");
			date = toEpoch(cols[0], cols[1]);
			num = parseFloat(cols[2]);
			station.getMeteo().getWindSpeedMed().put(date, num);
			num = parseFloat(cols[4]);
			station.getMeteo().getWindSpeedMax().put(date, num);
		}
		
		for( int i = 8; i < directionLines.length; i++ ) {
			cols = directionLines[i].split("\\|");
			date = toEpoch(cols[0], cols[1]);
			num = Integer.parseInt(cols[2]);
			station.getMeteo().getWindDirection().put(date, num);
		}
		
		for( int i = 8; i < humidityLines.length; i++ ) {
			cols = humidityLines[i].split("\\|");
			date = toEpoch(cols[0], cols[1]);
			try {	// We can go on without humidity data
				num = (float)Integer.parseInt(cols[2]);
				station.getMeteo().getAirHumidity().put(date, num);
			} catch( Exception e ) {}
		}
	}
	
	private Float parseFloat( String str ) {
		return Float.parseFloat(str.replace(',',this.separator));
	}
	
	private long toEpoch( String day, String time ) {
		String[] dayCols = day.split("-");
		String[] timeCols = time.split(":");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dayCols[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(dayCols[1])-1);
		cal.set(Calendar.YEAR, Integer.parseInt(dayCols[2]));
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeCols[0]));
		cal.set(Calendar.MINUTE, Integer.parseInt(timeCols[1]));
		cal.set(Calendar.SECOND, Integer.parseInt(timeCols[2]));
		cal.set(Calendar.MILLISECOND, 0);
	    return cal.getTimeInMillis();
	}

	private final char separator = '.';
	private Downloader downloader;
}
