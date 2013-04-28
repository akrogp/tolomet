package com.akrog.tolomet.data.providers;

import java.util.Calendar;

import android.os.AsyncTask;

import com.akrog.tolomet.R;
import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.data.ExcelDownloader;
import com.akrog.tolomet.data.Station;
import com.akrog.tolomet.data.WindProvider;
import com.akrog.tolomet.view.MyCharts;

public class LaRiojaProvider implements WindProvider {	
	public LaRiojaProvider( Tolomet tolomet ) {
		this.tolomet = tolomet;
		this.step = 0;
		this.separator = '.';
	}
	
	public void download(Station station, Calendar past, Calendar now) {
		this.station = station;
		step = 1;
		download( station.code, now, 7 );
	}
	
	private void download(String code, Calendar now, int codigoP ) {
		this.now = now;
		String desc;
		switch( codigoP ) {
			case 7:	desc=this.tolomet.getString(R.string.Speed); break;
			case 8:	desc=this.tolomet.getString(R.string.Direction); break;
			case 2:	desc=this.tolomet.getString(R.string.Humidity); break;
			default: desc = ""; break;
		}
		this.downloader = new ExcelDownloader(this.tolomet, this, desc);
		this.downloader.setUrl("http://ias1.larioja.org/estaciones/estaciones/mapa/informes/ExportarDatosServlet");
		this.downloader.addParam("direccion", "/opt/tomcat/webapps/estaciones/estaciones");
		this.downloader.addParam("codOrg","1");
		this.downloader.addParam("codigo",code);
		this.downloader.addParam("codigoP",codigoP);
		this.downloader.addParam("Seleccion","D");
		this.downloader.addParam("Ano",now.get(Calendar.YEAR));
		this.downloader.addParam("Mes",now.get(Calendar.MONTH)+1);
		this.downloader.addParam("DiaD",now.get(Calendar.DAY_OF_MONTH));
		this.downloader.addParam("DiaH",now.get(Calendar.DAY_OF_MONTH));
		this.downloader.addParam("Informe","Y");
		this.downloader.addParam("extension","xls");
		this.downloader.execute();
	}

	public void cancelDownload() {
		if( this.downloader != null && this.downloader.getStatus() != AsyncTask.Status.FINISHED )
			this.downloader.cancel(true);
	}

	public int getRefresh() {
		return 15;
	}

	public String getInfoUrl(String code) {
		return "http://ias1.larioja.org/estaciones/estaciones/mapa/consulta/consulta.jsp?codOrg=1&codigo="+code;
	}

	public void onCancelled() {
		this.tolomet.onCancelled();
	}

	public void onDownloaded(String result) {
		updateStation(result);
		switch( this.step ) {
			case 1:
				this.step++;
				download(this.station.code, this.now, 8);
				break;
			case 2:
				this.step++;
				download(this.station.code, this.now, 2);
				break;
			case 3:	
				this.step = 0;
				this.tolomet.onDownloaded();
				break;
		}		
	}

	private void updateStation(String result) {
		String[] lines = result.split("\n");		
		if( lines.length < 9 )
			return;
		
		if( this.step == 1 )
			this.station.clear();
		
		String[] cols;
		for( int i = 8; i < lines.length; i++ ) {
			cols = lines[i].split("\\|");
			parseCols( cols );
		}
	}

	private void parseCols(String[] cols) {
		if( this.step < 1 || this.step > 3 )
			return;
		
		Number date, num;
		date = toEpoch(cols[0], cols[1]);		
		switch( this.step ) {
			case 1:	// Speed
				num = parseFloat(cols[2]);
				this.station.listSpeedMed.add(date);
				this.station.listSpeedMed.add(num);
				num = parseFloat(cols[4]);
				this.station.listSpeedMax.add(date);
				this.station.listSpeedMax.add(num);
				break;
			case 2:	// Direction
				num = Integer.parseInt(cols[2]);
				this.station.listDirection.add(date);
				this.station.listDirection.add(num);
				break;
			case 3:	// Humidity
				try {	// We can go on without humidity data
					num = MyCharts.convertHumidity(Integer.parseInt(cols[2]));
					this.station.listHumidity.add(date);
					this.station.listHumidity.add(num);
				} catch( Exception e ) {}
				break;
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

	private Tolomet tolomet;
	private ExcelDownloader downloader;
	private Station station;
	private Calendar now;
	private int step;
	private char separator;
}
