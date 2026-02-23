package com.akrog.tolomet.providers;

import com.akrog.tolomet.Meteo;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;
import com.akrog.tolomet.utils.DateUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Locale;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class FieldClimateProvider extends BaseProvider {
    private static SimpleDateFormat df;
    private static String key;
    private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("Europe/Madrid");

    public FieldClimateProvider() {
        super(15);
    }

    private static String getKey() {
        if( key == null )
            try(BufferedReader br = new BufferedReader(new InputStreamReader(FieldClimateProvider.class.getResourceAsStream("/keys/fieldclimate.txt")))) {
                key = br.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return key;
    }

    private String getDate() {
        Calendar calendar = Calendar.getInstance(TIME_ZONE);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(calendar.getTime());
    }

    // Static method that generates HmacSHA256 signiture as a hexademical string
    private static String generateHmacSHA256Signature(String data, String key) throws GeneralSecurityException {
        byte[] hmacData = null;
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKey);
            return bytesToHex(mac.doFinal(data.getBytes("UTF-8")));

        } catch (UnsupportedEncodingException e) {
            throw new GeneralSecurityException(e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes)
            formatter.format("%02x", b);
        return formatter.toString();
    }

    @Override
    public String getInfoUrl(Station sta) {
        return getUserUrl(sta);
    }

    @Override
    public String getUserUrl(Station sta) {
        return "https://ng.fieldclimate.com/dashboard";
    }

    @Override
    public void configureDownload(Downloader downloader, Station station) {
        long stamp;
        if( station.isEmpty() ) {
            Calendar cal = Calendar.getInstance(TIME_ZONE);
            DateUtils.resetDay(cal);
            stamp = cal.getTimeInMillis();
        } else
            stamp = station.getStamp();
        String publicKey = "d8715ce99399fe90eaa3cebe0174e35f450e010f1b876d2f";
        String privateKey = getKey();
        String method = "GET";
        String path = String.format("/data/%s/raw/from/%d", station.getCode(), stamp/1000);
        String dateStr = getDate();
        String contentToSign = method + path + dateStr + publicKey;
        String signature = null;
        try {
            signature = generateHmacSHA256Signature(contentToSign, privateKey);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        String authorizationString = "hmac " + publicKey + ":" + signature;
        downloader.setHeader("Accept", "application/json");
        downloader.setHeader("Authorization", authorizationString);
        downloader.setHeader("Date", dateStr);
        downloader.setUrl("https://api.fieldclimate.com/v2" + path);
    }

    @Override
    public boolean configureDownload(Downloader downloader, Station station, long stamp) {
        return false;
    }

    @Override
    public void updateStation(Station station, String str) throws Exception {
        int start = str.indexOf('{');
        if( start < 0 )
            return;
        JSONObject json = new JSONObject(str.substring(start));//.replace('\'', '"'));
        JSONArray dates = json.getJSONArray("dates");
        JSONArray data = json.getJSONArray("data");
        JSONArray dir = null, med = null, max = null, temp = null, hum = null, pres = null;
        for( int i = 0; i < data.length(); i++ ) {
            JSONObject obj = data.getJSONObject(i);
            String name = obj.optString("name_original", null);
            if( name == null )
                continue;
            if( name.equals("Wind direction") )
                dir = obj.getJSONObject("values").getJSONArray("avg");
            else if( name.equals("Wind speed") )
                med = obj.getJSONObject("values").getJSONArray("avg");
            else if( name.equals("Wind speed max") )
                max = obj.getJSONObject("values").getJSONArray("max");
            else if( name.equals("HC Air temperature") )
                temp = obj.getJSONObject("values").getJSONArray("avg");
            else if( name.equals("HC Relative humidity") )
                hum = obj.getJSONObject("values").getJSONArray("avg");
            else if( name.equals("VPD") )
                hum = obj.getJSONObject("values").getJSONArray("avg");
        }
        Meteo meteo = station.getMeteo();
        for( int i = 0; i < dates.length(); i++ ) {
            long stamp = getStamp(dates.getString(i));
            if( dir != null ) meteo.getWindDirection().put(stamp, dir.getInt(i));
            if( med != null ) meteo.getWindSpeedMed().put(stamp, med.getDouble(i));
            if( max != null ) meteo.getWindSpeedMax().put(stamp, max.getDouble(i));
            if( temp != null ) meteo.getAirTemperature().put(stamp, temp.getDouble(i));
            if( hum != null ) meteo.getAirHumidity().put(stamp, hum.getDouble(i));
            if( pres != null ) meteo.getAirPressure().put(stamp, pres.getDouble(i));
        }
    }

    private long getStamp(String str) throws ParseException {
        if( df == null ) {
             df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
             df.setTimeZone(TIME_ZONE);
        }
        return df.parse(str).getTime();
    }
}
