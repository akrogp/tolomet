package com.akrog.tolomet.providers;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;
import com.akrog.tolomet.io.ExcelDownloader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class ParaMeterProvider implements WindProvider {
	private String token = "";
	@Override
	public void refresh(Station station) {
		getToken();
		travel(station, System.currentTimeMillis());
	}

	@Override
	public boolean travel(Station station, long date) {
		//TODO: Make sure dates are the same for all request.
		//TODO: Merge all in only one request

		String[] dates = downloadDates(station);
		if(dates.length == 0)
			return false;
		String[] speed = downloadSpeeds(station);
		if( speed.length == 0)
			return false;
		String[] speed_max = downloadSpeedMaxs(station);
		if( speed_max.length == 0)
			return false;
		String[] direction = downloadDirections(station);
		if( direction.length == 0)
			return false;
		updateStation(station, dates, speed, speed_max, direction);
		return true;
	}

	@Override
	public void cancel() {
		if( downloader != null )
			downloader.cancel();
	}

	@Override
	public int getRefresh(String code) {
		return 15;
	}
	
	@Override
	public String getInfoUrl(String code) {
		return "http://adriancaton.com/dashboard/013f9790-b614-11ee-9af8-d1c33caa606f?publicId=8fd7e4d0-e1f2-11ee-9af8-d1c33caa606f";
	}

	@Override
	public String getUserUrl(String code) {
		return "http://adriancaton.com/dashboard/013f9790-b614-11ee-9af8-d1c33caa606f?publicId=8fd7e4d0-e1f2-11ee-9af8-d1c33caa606f";
	}

	private void getToken() {
		downloader = new Downloader(20,2);
		downloader.setUrl("http://adriancaton.com:80/api/auth/login");
		downloader.setMethod("POST");
		downloader.setHeader("accept", "application/json");
		downloader.setHeader("Content-Type", "application/json");
		downloader.setQuery("{\"username\":\"tolomet@tolomet.org\",\"password\":\"tolomet\"}");

		String s = downloader.download();
		s=s.replace(" ","");
		s=s.replace("\"","");
		s=s.replace("{","");
		s=s.replace("[","");
		s=s.replace("}","");
		s=s.replace("]","");
		s=s.replace("\n","");
		String[] a=s.split("[\"\\\\,\\\\ \\\\:\\\\{\\\\}]");
		//find the token
		for (int i = 0; i < a.length; i++) {
			if (a[i].equals("token"))
			{
				this.token=a[i+1];
			}
		}
	}

	@Override
	public List<Station> downloadStations() {
		List<Station> stations = new ArrayList<>();

		getToken();

		downloader = new Downloader(20,2);
		downloader.setUrl("http://adriancaton.com:80/api/customer/42ecf180-37a9-11ef-ab1d-df34c59217b0/devices");
		downloader.addParam("pageSize", "100");
		downloader.addParam("page", "0");
		downloader.setHeader("X-Authorization", "Bearer " + this.token);
		downloader.setHeader("accept", "application/json");

		String s = downloader.download();
		s=s.replace(" ","");
		s=s.replace("\"","");
		s=s.replace("{","");
		s=s.replace("[","");
		s=s.replace("}","");
		s=s.replace("]","");
		s=s.replace("\n","");
		String[] a=s.split("[\"\\\\,\\\\ \\\\:\\\\{\\\\}]");
		//find the number of station names
		int n=0;
		for (int i = 0; i < a.length; i++) {
			if (a[i].equals("name"))
			{
				n++;
			}
		}
		//create and fill the array
		n=0;
		String tmp_station_code = "";
		for (int i = 0; i < a.length; i++) {
			if (a[i].equals("name")) {
				Station station = new Station();
				station.setCode(tmp_station_code);
				station.setName(a[i + 1]);

				station.setRegion(179);
				station.setCountry("ES");
				station.setProviderType(WindProviderType.ParaMeter);
				station.setLatitude(Double.parseDouble(downloadLat(station)));
				station.setLongitude(Double.parseDouble(downloadLon(station)));
				//Utils.utm2ll(station);
				stations.add(station);
			}

			if (a[i].equals("DEVICE"))
			{
				tmp_station_code=a[i+2];
			}
		}

		return stations;
	}

	private String downloadLat(Station station) {
		downloader = new Downloader(20,2);
		downloader.setUrl("http://adriancaton.com:80/api/plugins/telemetry/DEVICE/" + station.getCode() + "/values/timeseries");
		downloader.addParam("keys", "latitude");
		downloader.setHeader("X-Authorization", "Bearer " + this.token);
		downloader.setHeader("accept", "application/json");

		return downloader.download().split("\"")[7];
	}

	private String downloadLon(Station station) {
		downloader = new Downloader(20,2);
		downloader.setUrl("http://adriancaton.com:80/api/plugins/telemetry/DEVICE/" + station.getCode() + "/values/timeseries");
		downloader.addParam("keys", "longitude");
		downloader.setHeader("X-Authorization", "Bearer " + this.token);
		downloader.setHeader("accept", "application/json");

		return downloader.download().split("\"")[7];
	}

	private String[] downloadDates(Station station) {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		now.getTimeInMillis();

		//date = toEpoch(cols[0], cols[1]);

		downloader = new Downloader(20,2);
		downloader.setUrl("http://adriancaton.com:80/api/plugins/telemetry/DEVICE/" + station.getCode() + "/values/timeseries");
		downloader.addParam("keys", "velocidad");
		downloader.addParam("startTs", now.getTimeInMillis() - 6*60*60*1000);
		downloader.addParam("endTs", now.getTimeInMillis());
		downloader.setHeader("X-Authorization", "Bearer " + this.token);
		downloader.setHeader("accept", "application/json");

		String s = downloader.download();
		s=s.replace(" ","");
		s=s.replace("\"","");
		s=s.replace("{","");
		s=s.replace("[","");
		s=s.replace("}","");
		s=s.replace("]","");
		s=s.replace("\n","");
		String[] a=s.split("[\"\\\\,\\\\ \\\\:\\\\{\\\\}]");
		//find the number of speeds
		int n=0;
		for (int i = 0; i < a.length; i++) {
			if (a[i].equals("ts"))
			{
				n++;
			}
		}
		//create and fill the array
		String[] speeds = new String[n];
		n=0;
		for (int i = 0; i < a.length; i++) {
			if (a[i].equals("ts"))
			{
				speeds[n++]=a[i+1];
			}
		}

		return speeds;
	}

	private String[] downloadSpeeds(Station station) {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		now.getTimeInMillis();

		//date = toEpoch(cols[0], cols[1]);

		downloader = new Downloader(20,2);
		downloader.setUrl("http://adriancaton.com:80/api/plugins/telemetry/DEVICE/" + station.getCode() + "/values/timeseries");
		downloader.addParam("keys", "velocidad");
		downloader.addParam("startTs", now.getTimeInMillis() - 6*60*60*1000);
		downloader.addParam("endTs", now.getTimeInMillis());
		downloader.setHeader("X-Authorization", "Bearer " + this.token);
		downloader.setHeader("accept", "application/json");

		String s = downloader.download();
		s=s.replace(" ","");
		s=s.replace("\"","");
		s=s.replace("{","");
		s=s.replace("[","");
		s=s.replace("}","");
		s=s.replace("]","");
		s=s.replace("\n","");
		String[] a=s.split("[\"\\\\,\\\\ \\\\:\\\\{\\\\}]");
		//find the number of speeds
		int n=0;
		for (int i = 0; i < a.length; i++) {
			if (a[i].equals("value"))
			{
				n++;
			}
		}
		//create and fill the array
		String[] speeds = new String[n];
		n=0;
		for (int i = 0; i < a.length; i++) {
			if (a[i].equals("value"))
			{
				speeds[n++]=a[i+1];
			}
		}

		return speeds;
	}

	private String[] downloadSpeedMaxs(Station station) {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		now.getTimeInMillis();

		//date = toEpoch(cols[0], cols[1]);

		downloader = new Downloader(20,2);
		downloader.setUrl("http://adriancaton.com:80/api/plugins/telemetry/DEVICE/" + station.getCode() + "/values/timeseries");
		downloader.addParam("keys", "velocidad_max");
		downloader.addParam("startTs", now.getTimeInMillis() - 6*60*60*1000);
		downloader.addParam("endTs", now.getTimeInMillis());
		downloader.setHeader("X-Authorization", "Bearer " + this.token);
		downloader.setHeader("accept", "application/json");

		String s = downloader.download();
		s=s.replace(" ","");
		s=s.replace("\"","");
		s=s.replace("{","");
		s=s.replace("[","");
		s=s.replace("}","");
		s=s.replace("]","");
		s=s.replace("\n","");
		String[] a=s.split("[\"\\\\,\\\\ \\\\:\\\\{\\\\}]");
		//find the number of speeds
		int n=0;
		for (int i = 0; i < a.length; i++) {
			if (a[i].equals("value"))
			{
				n++;
			}
		}
		//create and fill the array
		String[] speeds = new String[n];
		n=0;
		for (int i = 0; i < a.length; i++) {
			if (a[i].equals("value"))
			{
				speeds[n++]=a[i+1];
			}
		}

		return speeds;
	}

	private String[] downloadDirections(Station station) {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		now.getTimeInMillis();

		//date = toEpoch(cols[0], cols[1]);

		downloader = new Downloader(20,2);
		downloader.setUrl("http://adriancaton.com:80/api/plugins/telemetry/DEVICE/" + station.getCode() + "/values/timeseries");
		downloader.addParam("keys", "direccion");
		downloader.addParam("startTs", now.getTimeInMillis() - 6*60*60*1000);
		downloader.addParam("endTs", now.getTimeInMillis());
		downloader.setHeader("X-Authorization", "Bearer " + this.token);
		downloader.setHeader("accept", "application/json");

		String s = downloader.download();
		s=s.replace(" ","");
		s=s.replace("\"","");
		s=s.replace("{","");
		s=s.replace("[","");
		s=s.replace("}","");
		s=s.replace("]","");
		s=s.replace("\n","");
		String[] a=s.split("[\"\\\\,\\\\ \\\\:\\\\{\\\\}]");
		//find the number of speeds
		int n=0;
		for (int i = 0; i < a.length; i++) {
			if (a[i].equals("value"))
			{
				n++;
			}
		}
		//create and fill the array
		String[] speeds = new String[n];
		n=0;
		for (int i = 0; i < a.length; i++) {
			if (a[i].equals("value"))
			{
				speeds[n++]=a[i+1];
			}
		}

		return speeds;
	}

	private String download(String code, Calendar now, int codigoP ) {
		downloader = new ExcelDownloader(20,2);
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

	protected void updateStation(Station station, String[] dates, String[] speeds, String[] speeds_max, String[] directions) {
		for( int i = 0; i < speeds.length; i++ ) {
			station.getMeteo().getWindSpeedMed().put(Long.parseLong(dates[i]), Float.parseFloat(speeds[i])*3.6);
			station.getMeteo().getWindSpeedMax().put(Long.parseLong(dates[i]), Float.parseFloat(speeds_max[i])*3.6);
		}
		
		for( int i = 0; i < directions.length; i++ ) {
			station.getMeteo().getWindDirection().put(Long.parseLong(dates[i]), Float.parseFloat(directions[i]));
		}
	}
	
	private Float parseFloat( String str ) {
		return Float.parseFloat(str.replace(',',this.separator));
	}
	
	private long toEpoch( String day, String time ) {
		String[] dayCols = day.split("-");
		String[] timeCols = time.split(":");
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dayCols[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(dayCols[1])-1);
		cal.set(Calendar.YEAR, Integer.parseInt(dayCols[2]));
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeCols[0]));
		cal.set(Calendar.MINUTE, Integer.parseInt(timeCols[1]));
		cal.set(Calendar.SECOND, Integer.parseInt(timeCols[2]));
		cal.set(Calendar.MILLISECOND, 0);
	    return cal.getTimeInMillis();
	}

	private static final Pattern COORDS_PATTERN = Pattern.compile(">([0-9\\.]*)/([0-9\\.]*)<");
	private final char separator = '.';
	private Downloader downloader;
}
