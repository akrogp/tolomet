package com.akrog.tolomet.gae;

import com.akrog.tolomet.io.Downloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GaeClient {
	private static final String URL = "http://tolomet-gae.appspot.com/rest/v2/";
    //private static final String URL = "http://10.0.2.2:8080/rest/v2/";
    private Downloader downloader;

    public Notification checkNotifications(
            Integer apiLevel, Integer appVersion, Integer dbVersion, Long stamp, String lang) throws Exception {
        downloader = new Downloader();
        downloader.setUrl(URL+"checkNotifications");
        if( apiLevel != null )
            downloader.addParam("apiLevel", apiLevel);
        if( appVersion != null )
            downloader.addParam("appVersion", appVersion);
        if( dbVersion != null )
            downloader.addParam("dbVersion", dbVersion);
        if( stamp != null )
            downloader.addParam("stamp", stamp);
        if( lang != null )
            downloader.addParam("lang", lang);
        String json = downloader.download();
        if( downloader.getError() != null )
            throw new Exception(downloader.getError());
        return parseNotification(json);
    }

    private Notification parseNotification(String str) throws JSONException {
        if( str == null || str.isEmpty() )
            return null;
        JSONObject json = new JSONObject(str);
        Notification info = new Notification();
        if( json.optString("appVersion",null) != null )
            parseApp(info, json);
        else if( json.optInt("dbVersion") != 0 )
            parseDb(info, json);
        else if( json.optString("motd",null) != null )
            parseMotd(info, json);
        else
            return null;
        return info;
    }

    private void parseApp(Notification info, JSONObject json) throws JSONException {
        info.setAppVersion(json.getString("appVersion"));
        List<String> list = new ArrayList<>();
        Object o = json.get("improvements");
        if( o instanceof JSONArray ) {
            JSONArray array = (JSONArray)o;
            for (int i = 0; i < array.length(); i++)
                list.add(array.getString(i));
        } else
            list.add(o.toString());
        info.setImprovements(list);
    }

    private void parseDb(Notification info, JSONObject json) throws JSONException {
        info.setDbVersion(json.getInt("dbVersion"));
        List<Station> list = new ArrayList<>();
        Object o = json.get("stations");
        if( o instanceof JSONArray ) {
            JSONArray array = (JSONArray)o;
            for( int i = 0; i < array.length(); i++ )
                list.add(parseStation(array.getJSONObject(i)));
        } else
            list.add(parseStation((JSONObject)o));
        info.setStations(list);
    }

    private Station parseStation(JSONObject json) throws JSONException {
        Station station = new Station();
        station.setAction(Station.Action.valueOf(json.getString("action")));
        station.setCode(json.optString("code",null));
        station.setName(json.optString("name",null));
        station.setProvider(json.optString("provider",null));
        station.setRegion(json.optString("region",null));
        station.setCountry(json.optString("country",null));
        if( json.optDouble("latitude",-1) >= 0 )
            station.setLatitude(json.getDouble("latitude"));
        if( json.optDouble("longitude",-1) >= 0 )
            station.setLongitude(json.getDouble("longitude"));
        return station;
    }

    private void parseMotd(Notification info, JSONObject json) throws JSONException {
        info.setMotd(json.getString("motd"));
        info.setStamp(json.optLong("stamp"));
    }

    public void cancel() {
        if( downloader != null )
            downloader.cancel();
    }
}
