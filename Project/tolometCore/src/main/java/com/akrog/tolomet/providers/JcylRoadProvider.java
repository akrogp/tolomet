package com.akrog.tolomet.providers;

import com.akrog.tolomet.Measurement;
import com.akrog.tolomet.Meteo;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;
import com.akrog.tolomet.io.XlsxDownloader;
import com.akrog.tolomet.utils.DateUtils;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public abstract class JcylRoadProvider extends BaseProvider {
    private static final int REFRESH = 10;

    public JcylRoadProvider() {
        super(REFRESH);
    }

    @Override
    public List<Station> downloadStations() {
        try {
            downloader = new XlsxDownloader();
            downloader.setUrl("https://datosabiertos.jcyl.es/web/jcyl/binarios/507/453/CDA_Carreteras_unificado.xlsx");
            String csv = downloader.download();
            List<Station> result = new ArrayList<>();
            try(BufferedReader br = new BufferedReader(new StringReader(csv))) {
                String line;
                while( (line = br.readLine()) != null ) {
                    if( line.startsWith("entity") )
                        continue;
                    String[] cols = line.split("\\|", -1);
                    Station station = new Station();
                    station.setCode(cols[0]);
                    station.setName(cols[3]);
                    station.setCountry("ES");
                    if( "SaltSilo".equals(cols[1]) )
                        station.setProviderType(WindProviderType.JcylSaltProvider);
                    else if( "RoadFrostSensor".equals(cols[1]) )
                        station.setProviderType(WindProviderType.JcylFrostProvider);
                    else
                        throw new IOException("Unkown JCyL road station type: " + cols[1]);
                    String[] coords = cols[2].split(",", -1);
                    station.setLatitude(Double.parseDouble(coords[1]));
                    station.setLongitude(Double.parseDouble(coords[0]));
                    result.add(station);
                }
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
        //cal.set(Calendar.MONTH, 6);
        //cal.set(Calendar.DAY_OF_MONTH, 3);
        downloader.setHeader("Authorization", "Basic " + new String(Base64.encodeBase64("sc_jcyl_publico:sc_jcyl_publico".getBytes())));
        downloader.setUrl("https://bi.territoriointeligente.jcyl.es/pentaho/plugin/cda/api/doQuery");
        downloader.addParam("path", "/public/sc_jcyl/verticals/sql/carreteras.cda");
        downloader.addParam("dataAccessId", station.getProviderType() == WindProviderType.JcylSaltProvider ? "SaltsiloRaw" : "RoadfrostsensorRaw");
        downloader.addParam( "paramentityid", station.getCode());
        downloader.addParam( "paramstart", "%04d-%02d-%02d %02d:%02d:%02d",
            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH),
            cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND)
        );
        downloader.addParam( "output", "csv");
        return true;
    }

    @Override
    public void updateStation(Station station, String data) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        JSONObject json = new JSONObject(data);
        JSONArray meta = json.getJSONArray("metadata");
        JSONArray result = json.getJSONArray("resultset");
        Map<String,Integer> map = new HashMap<>();
        for( int i = 0; i < meta.length(); i++ ) {
            JSONObject col = meta.getJSONObject(i);
            map.put(col.getString("colName"), col.getInt("colIndex"));
        }
        for( int i = 0; i < result.length(); i++ ) {
            JSONArray meas = result.getJSONArray(i);
            Integer idx = map.get("timeinstant");
            long stamp = sdf.parse(meas.getString(idx)).getTime();
            Meteo meteo = station.getMeteo();
            parse("winddirection", map, meas, stamp, meteo.getWindDirection());
            parse("windspeed", map, meas, stamp, meteo.getWindSpeedMed());
            parse("windgust", map, meas, stamp, meteo.getWindSpeedMax());
            parse("temperature", map, meas, stamp, meteo.getAirTemperature());
            parse("relativehumidity", map, meas, stamp, meteo.getAirHumidity());
            parse("solarradiation", map, meas, stamp, meteo.getIrradiance());
        }
    }

    private void parse(String name, Map<String, Integer> map, JSONArray meas, long stamp, Measurement output) {
        Integer idx = map.get(name);
        if( idx == null )
            return;
        try {
            double value = meas.getDouble(idx);
            output.put(stamp, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getInfoUrl(String code) {
        return "https://datosabiertos.jcyl.es/web/jcyl/set/es/ciencia-tecnologia/cosultas-estaciones-meteorologicas-carreteras/1285325194636";
    }

    @Override
    public String getUserUrl(String code) {
        return getInfoUrl(code);
    }
}
