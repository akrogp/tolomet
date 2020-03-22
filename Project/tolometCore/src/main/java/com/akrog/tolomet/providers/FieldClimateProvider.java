package com.akrog.tolomet.providers;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class FieldClimateProvider extends BaseProvider {
    private static String key;
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

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
        Calendar calendar = Calendar.getInstance();
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
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    @Override
    public String getInfoUrl(String code) {
        return getUserUrl(code);
    }

    @Override
    public String getUserUrl(String code) {
        return "https://ng.fieldclimate.com/dashboard";
    }

    @Override
    public void configureDownload(Downloader downloader, Station station) {
        String publicKey = "d8715ce99399fe90eaa3cebe0174e35f450e010f1b876d2f";
        String privateKey = getKey();
        String method = "GET";
        //String path = String.format("https://api.fieldclimate.com/v1/data/optimized/%s/raw/last/24h", station.getCode());
        String path = String.format("https://api.fieldclimate.com/v1/station/" + station.getCode());
        String date = getDate();
        String contentToSign = method + path + date + publicKey;
        String signature = null;
        try {
            signature = generateHmacSHA256Signature(contentToSign, privateKey);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        String authorizationString = "hmac " + publicKey + ":" + signature;
        downloader.setHeader("Accept", "application/json");
        downloader.setHeader("Authorization", authorizationString);
        downloader.setHeader("Date", date);
        downloader.setUrl(path);
    }

    @Override
    public boolean configureDownload(Downloader downloader, Station station, long date) {
        return false;
    }

    @Override
    public void updateStation(Station station, String data) throws Exception {
        System.out.println(data);
    }
}
