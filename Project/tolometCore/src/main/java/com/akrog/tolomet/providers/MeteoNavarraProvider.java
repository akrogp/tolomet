package com.akrog.tolomet.providers;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.utils.Utils;
import com.akrog.tolomet.io.Downloader;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import java.util.logging.Logger;

public class MeteoNavarraProvider implements WindProvider {
	// Create a logger for this class
	//private static final Logger LOGGER = Logger.getLogger(MeteoNavarraProvider.class.getName());


	public MeteoNavarraProvider() {
        df = new SimpleDateFormat("dd/MM/yyyyH:mm");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

	@Override
	public void refresh(Station station) {
		travel(station, System.currentTimeMillis());
	}

	@Override
	public boolean travel(Station station, long date) {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		now.setTimeInMillis(date);
		String time1 = String.format("%d/%d/%d", now.get(Calendar.DAY_OF_MONTH), now.get(Calendar.MONTH)+1, now.get(Calendar.YEAR) );
		String time2 = String.format("%d/%d/%d", now.get(Calendar.DAY_OF_MONTH)+1, now.get(Calendar.MONTH)+1, now.get(Calendar.YEAR) );
		downloader = new Downloader();
		downloader.useLineBreak(false);
		downloader.setUrl("https://meteo.navarra.es/estaciones/estacion_datos.cfm");
		downloader.addParam("IDEstacion",station.getCode().substring(2));
		downloader.addParam("p_10","1");
		downloader.addParam("p_10","2");
		downloader.addParam("p_10","6");
		downloader.addParam("p_10","7");
		downloader.addParam("p_10","9");
		downloader.addParam("fecha_desde",time1);
		downloader.addParam("fecha_hasta",time2);
		downloader.addParam("dl","csv");
		updateStation(station, downloader.download());
		return true;
	}

	@Override
	public void cancel() {
		if( downloader != null )
			downloader.cancel();
	}

	private static String htmlToCsv(String html) {

		if (html == null || html.isEmpty())
			return "";

		// 1️⃣ Extract only the data table
		int start = html.indexOf("<!--DATASTART-->");
		if (start == -1)
			return "";

		int tableStart = html.indexOf("<table", start);
		int tableEnd = html.indexOf("</table>", tableStart);
		if (tableStart == -1 || tableEnd == -1)
			return "";

		String table = html.substring(tableStart, tableEnd);

		StringBuilder csv = new StringBuilder();

		// 2️⃣ Split rows
		String[] rows = table.split("<tr");

		for (String row : rows) {

			if (!row.contains("<td") && !row.contains("<th"))
				continue;

			StringBuilder line = new StringBuilder();

			// 3️⃣ Split cells
			String[] cells = row.split("<td|<th");

			boolean firstCell = true;

			for (String cell : cells) {

				int close = cell.indexOf(">");
				if (close == -1)
					continue;

				String content = cell.substring(close + 1);

				int endCell = content.indexOf("</td>");
				if (endCell == -1)
					endCell = content.indexOf("</th>");

				if (endCell == -1)
					continue;

				content = content.substring(0, endCell);

				// 4️⃣ Remove inner HTML tags (like <font>)
				content = content.replaceAll("<[^>]*>", "");

				// Remove &nbsp;
				content = content.replace("&nbsp;", " ").trim();

				// Escape quotes
				content = content.replace("\"", "\"\"");

				if (!firstCell)
					line.append(";");

				line.append("\"").append(content).append("\"");

				firstCell = false;
			}

			if (line.length() > 0)
				csv.append(line).append("\n");
		}

		return csv.toString();
	}

	protected void updateStation(Station station, String data) {

		//LOGGER.info(data);
		String csvData = htmlToCsv(data);
		//LOGGER.info(csvData);
		if (csvData.isEmpty())
			return;

		String[] lines = csvData.split("\\r?\\n");

		if (lines.length < 3)
			return; // no hay datos reales

		// Saltamos:
		// línea 0 -> cabecera
		// línea 1 -> unidades
		for (int l = 2; l < lines.length; l++) {

			String line = lines[l].trim();
			if (line.isEmpty())
				continue;

			String[] cells = line.split(";");

			if (cells.length < 8)
				continue;

			try {

				long date = toEpoch(getContent(cells[0]));

				// Dirección viento (°)
				Number windDir = Integer.parseInt(getContent(cells[5]));
				station.getMeteo().getWindDirection().put(date, windDir);

				// Velocidad media viento (m/s)
				Number windSpeedMed = Float.parseFloat(getContent(cells[3]));
				station.getMeteo().getWindSpeedMed().put(date, windSpeedMed);

				// Racha máxima (m/s)
				Number windSpeedMax = Float.parseFloat(getContent(cells[6]));
				station.getMeteo().getWindSpeedMax().put(date, windSpeedMax);

				// Humedad (%)
				try {
					Number humidity = Float.parseFloat(getContent(cells[2]));
					station.getMeteo().getAirHumidity().put(date, humidity);
				} catch (Exception ignored) {}

				// Temperatura (°C)
				try {
					Number temp = Float.parseFloat(getContent(cells[1]));
					station.getMeteo().getAirTemperature().put(date, temp);
				} catch (Exception ignored) {}

			} catch (Exception e) {
				// Si una fila falla no detenemos todo el proceso
			}
		}
	}

	@Override
	public int getRefresh( String code ) {
		return 10;
	}
	
	@Override
	public String getInfoUrl(Station sta) {
		return "https://meteo.navarra.es/estaciones/estacion_detalle.cfm?idestacion=" + sta.getCode().substring(2);
	}

	@Override
	public String getUserUrl(Station sta) {
		return "https://meteo.navarra.es/estaciones/estacion.cfm?IDEstacion=" + sta.getCode().substring(2);
	}

	@Override
	public List<Station> downloadStations() {
		List<Station> stations = new ArrayList<>();
		Downloader dw = new Downloader();
		dw.setUrl("https://meteo.navarra.es/estaciones/mapadeestaciones.cfm");
		String data = dw.download("relieve");
		Matcher matcher = LAYER_PATTERN.matcher(data);
		while( matcher.find() ) {
			if( matcher.group(3).contains("MAN") )
				continue;
			if( !matcher.group(5).endsWith("GN") )
				continue;
			Station station = new Station();
			station.setName(matcher.group(5).replaceAll(" GN", ""));
			station.setCode("GN"+matcher.group(1));
			station.setCountry("ES");
			station.setRegion(182);
			station.setProviderType(WindProviderType.MeteoNavarra);
			if( !downloadCoords(station) )
				return null;
			Utils.utm2ll(station);
			stations.add(station);
		}
		return stations;
	}

	private boolean downloadCoords(Station station) {
    	Downloader dw = new Downloader();
    	dw.setUrl("https://meteo.navarra.es/estaciones/estacion.cfm");
    	dw.addParam("IDEstacion", station.getCode().substring(2));
    	String[] lines = dw.download("fotos").split("\n");
    	int fields = 2;
    	for( String line : lines ) {
    		if( line.contains("X:") ) {
				station.setLongitude(Integer.parseInt(line.split("X:")[1]));
				fields--;
			} else if (line.contains("Y:") ) {
    			StringBuilder sb = new StringBuilder();
    			String str = line.split("Y:")[1];
    			for( int i = 0; i < str.length() && Character.isDigit(str.charAt(i)); i++ )
    				sb.append(str.charAt(i));
    			station.setLatitude(Integer.parseInt(sb.toString()));
    			fields--;
			}
			if( fields <= 0 )
				break;
		}
		return fields == 0;
	}

	private long toEpoch( String str ) {
        try {
            return df.parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
	
	private String getContent( String cell ) {
		return cell.replaceAll("\"","").replace('.',this.separator);
	}

	private static final Pattern END_PATTERN = Pattern.compile("[a-zA-Z]");
    private static final Pattern LAYER_PATTERN = Pattern.compile("doLayer1\\((.*),(.*),'(.*)','(.*)','(.*)',(.*),(.*), ?(.*), ?(.*)\\);");
	private final char separator = '.';
	private Downloader downloader;
    private final DateFormat df;
}
