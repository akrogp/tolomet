package com.akrog.tolomet.providers;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by gorka on 4/04/16.
 */
public class HolfuyJsonProvider extends BaseProvider {

    public HolfuyJsonProvider() {
        super(REFRESH);
    }

    @Override
    public String getInfoUrl(String code) {
        return "http://holfuy.com/en/camera/"+code;
    }

    @Override
    public String getUserUrl(String code) {
        return "http://holfuy.com/en/data/"+code;
    }

    @Override
    public void configureDownload(Downloader downloader, Station station) {
        downloader.setUrl("http://holfuy.com/en/takeit/gethistory.php");
        downloader.addParam("s", station.getCode());
        if( passwd == null )
            loadPasswd();
        String pw = passwd.get(station.getCode());
        if( pw != null )
            downloader.addParam("pw", pw);
        downloader.addParam("type", REFRESH_TYPE);
        Long stamp = station.getStamp();
        long cnt;
        if( stamp == null ) {
            Calendar midnight = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
            midnight.set(Calendar.HOUR_OF_DAY, 0);
            midnight.set(Calendar.MINUTE, 0);
            midnight.set(Calendar.SECOND, 0);
            midnight.set(Calendar.MILLISECOND, 0);
            cnt = midnight.getTimeInMillis();
        } else
            cnt = stamp;
        cnt = (System.currentTimeMillis() - cnt) / 1000 / 60 / REFRESH + 1;
        downloader.addParam("cnt", cnt);
        //downloader.addParam("su", "m/s");
    }

    @Override
    public void updateStation(Station station, String data) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
        try {
            JSONObject json = new JSONObject(data);
            JSONArray array = json.getJSONArray("measurements");
            for( int i = 0; i < array.length(); i++ ) {
                JSONObject item = array.getJSONObject(i);
                long date = df.parse(item.getString("dateTime")).getTime();
                JSONObject wind = item.getJSONObject("wind");
                try {
                    station.getMeteo().getAirTemperature().put(date,(float)item.getDouble("temperature"));
                } catch (Exception e ) {};
                try {
                    station.getMeteo().getWindDirection().put(date, (float)wind.getInt("direction"));
                } catch (Exception e ) {};
                try {
                    station.getMeteo().getWindSpeedMed().put(date, (float)wind.getDouble("speed")*3.6);
                } catch (Exception e ) {};
                try {
                    station.getMeteo().getWindSpeedMax().put(date, (float)wind.getDouble("gust")*3.6);
                } catch (Exception e ) {};
            }
        } catch (JSONException e) {
        } catch (ParseException e) {
        }
    }

    private void loadPasswd() {
        passwd = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/res/holfuy.csv")));
            String line;
            String[] fields;
            while( (line=br.readLine()) != null ) {
                fields = line.split(",");
                passwd.put(fields[0], fields[1]);
            }
            br.close();
        } catch( Exception e ) {
        }
    }

    /*private static final int REFRESH = 2;
    private static final int REFRESH_TYPE = 0;*/
    private static final int REFRESH = 15;
    private static final int REFRESH_TYPE = 1;
    /*private static final int REFRESH = 60;
    private static final int REFRESH_TYPE = 2;*/

    private Map<String,String> passwd;
}
