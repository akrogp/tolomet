package com.akrog.tolomet.data.providers;

import java.util.Calendar;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.data.Downloader;
import com.akrog.tolomet.data.Station;
import com.akrog.tolomet.data.WindProvider;
import com.akrog.tolomet.view.MyCharts;

public class MeteocatProvider implements WindProvider {
	public MeteocatProvider( Tolomet tolomet ) {
		this.tolomet = tolomet;
	}
	
	@SuppressLint("DefaultLocale")
	public void download(Station station, Calendar past, Calendar now) {
		this.station = station;
		this.downloader = new Downloader(this.tolomet, this);
		//this.downloader.useLineBreak(true);
		this.downloader.setMethod("POST");
		this.downloader.setUrl("http://www.meteo.cat/xema/AppJava/Detall24Estacio.do");
		this.downloader.addParam("idEstacio", station.code);
		this.downloader.addParam("team", "ObservacioTeledeteccio");
		this.downloader.addParam("inputSource", "DadesActualsEstacio");
		this.downloader.execute();
	}
	
	public void cancelDownload() {
		if( this.downloader != null && this.downloader.getStatus() != AsyncTask.Status.FINISHED )
			this.downloader.cancel(true);
	}
	
	public void onCancelled() {
		this.tolomet.onCancelled();
	}

	public void onDownloaded(String result) {
		updateStation(result);
		this.tolomet.onDownloaded();
	}

	private void updateStation(String data) {
		Number date = null, val;
		String fields[] = data.split("<td");
		if( fields.length < 11 )			
			return;
		this.station.clear();
		for( int i = 1; i < fields.length; i += 10 ) {
			date = toEpoch(getContent(fields[i],2));
			val = Integer.parseInt(getContent(fields[i+6],4));
			this.station.listDirection.add(0,date);
			this.station.listDirection.add(1,val);
			val = Float.parseFloat(getContent(fields[i+6],2).replaceAll(" -", ""))*3.6F;
			this.station.listSpeedMed.add(0,date);
			this.station.listSpeedMed.add(1,val);
			val = Float.parseFloat(getContent(fields[i+7],2))*3.6F;
			this.station.listSpeedMax.add(0,date);
			this.station.listSpeedMax.add(1,val);
			val = MyCharts.convertHumidity(Integer.parseInt(getContent(fields[i+4],2)));
			this.station.listHumidity.add(0,date);
			this.station.listHumidity.add(1,val);
		}						
	}

	public int getRefresh() {
		return 30;
	}		
	
	private long toEpoch( String str ) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		String[] tmp = str.split(" ");
		String[] date = tmp[0].split("/");
		String[] time = tmp[1].split("-")[1].replace(")", "").split(":");
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(date[1])-1);
		cal.set(Calendar.YEAR, Integer.parseInt(date[2]));
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
		cal.set(Calendar.MINUTE, Integer.parseInt(time[1]));
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0 );
	    return cal.getTimeInMillis();
	}
	
	private String getContent( String td, int pos ) {
		String string = td.split(">")[pos].replaceAll("<.*", "").trim();
		return string;
	}
	
	public String getInfoUrl(String code) {
		return "http://www.meteo.cat/xema/AppJava/SeleccioPerComarca.do";
	}
	
	private Tolomet tolomet;
	private Downloader downloader;
	private Station station;
}
