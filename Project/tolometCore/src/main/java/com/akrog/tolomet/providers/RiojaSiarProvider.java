package com.akrog.tolomet.providers;

import com.akrog.tolomet.Measurement;
import com.akrog.tolomet.Meteo;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;
import com.akrog.tolomet.utils.DateUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class RiojaSiarProvider extends BaseProvider {
    private static final int REFRESH = 30;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public RiojaSiarProvider() {
        super(REFRESH);
    }

    @Override
    protected Downloader newDownloader() {
        return new Downloader(30, 2);
    }

    @Override
    public List<Station> downloadStations() {
        try {
            downloader = new Downloader();
            downloader.setUrl("https://ias1.larioja.org/apiSiar/servicios/v2/estaciones");
            String raw = downloader.download();
            JSONObject json = new JSONObject(raw);
            JSONArray stations = json.getJSONArray("estaciones");
            List<Station> result = new ArrayList<>(stations.length());
            for( int i = 0; i < stations.length(); i++ ) {
                JSONObject item = stations.getJSONObject(i);
                Station station = new Station();
                station.setCode(item.getString("codigo_estacion"));
                station.setName(item.getString("nombre"));
                station.setCountry("ES");
                station.setProviderType(WindProviderType.RiojaSiar);
                station.setLatitude(Double.parseDouble(item.getString("latitud")));
                station.setLongitude(Double.parseDouble(item.getString("longitud")));
                result.add(station);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            downloader = null;
        }
        return null;
    }

    @Override
    public void configureDownload(Downloader downloader, Station station) {
        Long stamp = station.getStamp();
        if( stamp == null ) {
            Calendar cal = Calendar.getInstance();
            DateUtils.resetDay(cal);
            stamp = cal.getTimeInMillis();
        }
        configureDownload(downloader, station, stamp);
    }

    @Override
    public boolean configureDownload(Downloader downloader, Station station, long date) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
        cal.setTimeInMillis(date);
        downloader.setUrl(String.format("https://ias1.larioja.org/apiSiar/servicios/v2/datos-climaticos/%s/T", station.getCode()));
        downloader.addParam("fecha_inicio", sdf.format(cal.getTime()));
        DateUtils.endDay(cal);
        downloader.addParam("fecha_fin", sdf.format(cal.getTime()));
        downloader.addParam("parametro", "DV");
        downloader.addParam("parametro", "VV");
        downloader.addParam("parametro", "T");
        downloader.addParam("parametro", "Hr");
        downloader.addParam("parametro", "Rginst");
        downloader.addParam("funcion", "Med");
        downloader.addParam("funcion", "Max");
        downloader.addParam("funcion", "Valor");
        return true;
    }

    @Override
    public void updateStation(Station station, String data) throws Exception {
        JSONObject json = new JSONObject(data);
        JSONArray result = json.getJSONArray("datos");
        for(int i = 0; i < result.length(); i++) {
            JSONObject item = result.getJSONObject(i);
            String param = item.getString("parametro");
            String aggr = item.getString("funcion_agregacion");
            Meteo meteo = station.getMeteo();
            Measurement meas = null;
            double factor = 1.0;
            long stamp = sdf.parse(item.getString("fecha")).getTime();
            if( param.equals("DV") && aggr.equals("Med"))
                meas = meteo.getWindDirection();
            else if( param.equals("VV") ) {
                factor = 3.6;
                if( aggr.equals("Med") )
                    meas = meteo.getWindSpeedMed();
                else if( aggr.equals("Max") )
                    meas = meteo.getWindSpeedMax();
            } else if( param.equals("T") && aggr.equals("Med") )
                meas = meteo.getAirTemperature();
            else if( param.equals("Hr") && aggr.equals("Med") )
                meas = meteo.getAirHumidity();
            else if( param.equals("Rginst") && aggr.equals("Med") )
                meas = meteo.getIrradiance();
            if( meas != null ) {
                double value = Double.parseDouble(item.getString("valor"));
                meas.put(stamp, value * factor);
            }
        }
    }

    @Override
    public String getInfoUrl(Station sta) {
        return getUserUrl(sta);
    }

    @Override
    public String getUserUrl(Station sta) {
        return "https://www.larioja.org/agricultura/es/informacion-agroclimatica/red-estaciones-agroclimaticas-siar/detalle-estacion?homepage=" + sta.getCode();
    }
}
