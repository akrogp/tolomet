package com.akrog.tolomet.providers;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;
import com.akrog.tolomet.io.ExcelDownloader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;


class ParaMeterData
{
	public String date;
	public String speed;
	public String speed_max;
	public String direction;

};

public class ParaMeterProvider implements WindProvider {
	private String token = "";
	@Override
	public void refresh(Station station) {
		getToken();
		travel(station, System.currentTimeMillis());
	}

	@Override
	public boolean travel(Station station, long date) {
		ParaMeterData[] pmd = DownloadData(station);

		if(pmd.length == 0)
			return false;

		String[] dates = new String[pmd.length];
		String[] speeds = new String[pmd.length];
		String[] speeds_max = new String[pmd.length];
		String[] directions = new String[pmd.length];


		for (int i=0; i<pmd.length; i++)
		{
			dates[i] = pmd[i].date;
			speeds[i] = pmd[i].speed;
			speeds_max[i] = pmd[i].speed_max;
			directions[i] = pmd[i].direction;
		}

		updateStation(station, dates, speeds, speeds_max, directions);
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
				// Now we create the station and add it with the previously stored name
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
				// While parsing the file, we find this before and then the name
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

	private ParaMeterData[] DownloadData(Station station) {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		now.getTimeInMillis();

		//date = toEpoch(cols[0], cols[1]);

		downloader = new Downloader(20,2);
		downloader.setUrl("http://adriancaton.com:80/api/plugins/telemetry/DEVICE/" + station.getCode() + "/values/timeseries");
		downloader.addParam("keys", "velocidad,velocidad_max,direccion");
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

		n=n/3; //There are 3 params now, that means 3 more timestamps per ParaMeterData

		//create and fill the array
		ParaMeterData[] data = new ParaMeterData[n];
		n=0;
		int current_param = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i].equals("velocidad"))
			{
				current_param = 0;
				n=0;
			}
			if (a[i].equals("velocidad_max")) {
				current_param = 1;
				n=0;
			}
			if (a[i].equals("direccion")) {
				current_param = 2;
				n=0;
			}
			if (a[i].equals("ts")) {
				if (current_param == 0)
				{
					data[n] = new ParaMeterData();
					data[n].date = a[i + 1];
					data[n].speed = a[i + 3];
				}
				if (current_param == 1) {
					data[n].speed_max = a[i + 3];
				}
				if (current_param == 2) {
					data[n].direction = a[i + 3];
				}
				n++;
			}

		}

		return data;
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

	private Downloader downloader;
}
