package com.akrog.tolomet.providers;

import com.akrog.tolomet.Measurement;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class RedVigiaProvider implements WindProvider {
    public RedVigiaProvider() {
        df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
    }

	@Override
	public void refresh(Station station) {
        download(station.getCode(), "Direccion-Media-Viento", station.getMeteo().getWindDirection(), 1.0F);
		download(station.getCode(), "Velocidad-Viento-Media", station.getMeteo().getWindSpeedMed(), 3.6F);
        download(station.getCode(), "Velocidad-Viento-Maxima", station.getMeteo().getWindSpeedMax(), 3.6F);
        download(station.getCode(), "Humedad-Relativa-Aire", station.getMeteo().getAirHumidity(), 1.0F);
        download(station.getCode(), "Presion-Atmosferica-Media", station.getMeteo().getAirPressure(), 1.0F);
        download(station.getCode(), "Temperatura-Media-Aire", station.getMeteo().getAirTemperature(), 1.0F);
	}

    @Override
    public boolean travel(Station station, long date) {
        return false;
    }

    private void download(String code, String page, Measurement data, float factor) {
        downloader = new Downloader();
        downloader.setBrowser(Downloader.FakeBrowser.MOZILLA);
        downloader.setUrl(String.format("http://www.redvigia.es/Boyas/Evolucion/%s/%s",code.replaceAll("ñ","%C3%B1"),page));
        String html = downloader.download("/tbody");
        String[] fields = html.split("<td>");
        for( int i = 1; i < fields.length; i+= 2 ) {
            int i1 = fields[i].indexOf(", ");
            if( i1 < 0 ) continue;
            int i2 = fields[i].indexOf("</td>");
            if( i2 < 0 ) continue;
            try {
                long stamp = df.parse(fields[i].substring(i1+2,i2)).getTime();
                i2 = fields[i+1].indexOf(' ');
                if( i2 < 0 ) continue;
                Number value = (float)(nf.parse(fields[i+1].substring(0,i2)).doubleValue()*factor);
                data.put(stamp, value);
            } catch (ParseException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

	@Override
	public void cancel() {
		if( downloader != null )
			downloader.cancel();
	}

	@Override
	public int getRefresh( String code ) {
		return 60;
	}		
	
	@Override
	public String getInfoUrl(Station sta) {
        return String.format("http://www.redvigia.es/Boyas/Detalle/%s#detalle_general", sta.getCode());
	}

	@Override
	public String getUserUrl(Station sta) {
        return String.format("http://www.redvigia.es/Boyas/Detalle/%s#detalle_meteorologia", sta.getCode());
	}

    @Override
    public List<Station> downloadStations() {
        return null;
    }

    private Downloader downloader;
    private final DateFormat df;
    private final NumberFormat nf = NumberFormat.getInstance(Locale.FRANCE);;
}
