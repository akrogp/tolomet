package com.akrog.tolomet.providers;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;
import com.akrog.tolomet.io.XmlElement;
import com.akrog.tolomet.io.XmlParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by gorka on 7/04/16.
 */
public class HolfuyProvider extends BaseProvider {
    public HolfuyProvider() {
        super(REFRESH);
    }

    @Override
    public String getInfoUrl(Station sta) {
        return "http://holfuy.com/en/camera/"+sta.getCode().substring(1);
    }

    @Override
    public String getUserUrl(Station sta) {
        return "http://holfuy.com/en/data/"+sta.getCode().substring(1);
        //return "http://m.holfuy.com/"+code.substring(1);
    }

    @Override
    public List<Station> downloadStations() {
        try {
            downloader = new Downloader();
            downloader.setUrl("https://holfuy.com/puget/mkrs.php");
            String data = downloader.download().replaceAll("\"\"", "\"0\"");
            XmlElement xml = XmlParser.load(new StringReader(data));
            List<Station> result = new ArrayList<>(xml.getSubElements().size());
            for (XmlElement marker : xml.getSubElements()) {
                Station station = new Station();
                station.setProviderType(WindProviderType.Holfuy);
                station.setCode("s"+marker.getAttribute("station"));
                station.setName(marker.getAttribute("place"));
                try {
                    station.setLatitude(Double.parseDouble(marker.getAttribute("lat")));
                    station.setLongitude(Double.parseDouble(marker.getAttribute("lng")));
                    if (station.isFilled())
                        result.add(station);
                } catch (Exception e) {
                    e.printStackTrace();
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
        downloader.setUrl("http://holfuy.hu/en/takeit/xml/dezso/data.php");
        downloader.addParam("station", station.getCode());
        Long stamp = station.getStamp();
        long cnt;
        if( stamp == null ) {
            Calendar midnight = Calendar.getInstance(TIME_ZONE);
            midnight.set(Calendar.HOUR_OF_DAY, 0);
            midnight.set(Calendar.MINUTE, 0);
            midnight.set(Calendar.SECOND, 0);
            midnight.set(Calendar.MILLISECOND, 0);
            cnt = midnight.getTimeInMillis();
        } else
            cnt = stamp;
        cnt = (System.currentTimeMillis() - cnt) / 1000 / 60 / REFRESH + 1;
        downloader.addParam("cnt", cnt);
    }

    @Override
    public boolean configureDownload(Downloader downloader, Station station, long date) {
        return false;
    }

    @Override
    public void updateStation(Station station, String data) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss", Locale.ENGLISH);
            df.setTimeZone(TIME_ZONE);

            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new StringReader(data));

            int event = parser.getEventType();
            String date = null;
            Long stamp = null;
            String text = null;
            while( event != XmlPullParser.END_DOCUMENT ) {
                String tag = parser.getName();
                switch( event ) {
                    case XmlPullParser.START_TAG:
                        if( tag.equals("WeatherMeasurement") ) {
                            stamp = null;
                            text = null;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if( tag.equals("date") )
                            date = text;
                        else if( tag.equals("time") )
                            stamp = df.parse(String.format("%s %s",date,text)).getTime();
                        else if( tag.equals("dir") )
                            station.getMeteo().getWindDirection().put(stamp,Float.parseFloat(text));
                        else if( tag.equals("gust") )
                            station.getMeteo().getWindSpeedMax().put(stamp, Float.parseFloat(text));
                        else if( tag.equals("speed") )
                            station.getMeteo().getWindSpeedMed().put(stamp, Float.parseFloat(text));
                        else if( tag.equals("temp") )
                            station.getMeteo().getAirTemperature().put(stamp, Float.parseFloat(text));
                        break;
                }
                event = parser.next();
            }
        } catch (XmlPullParserException | IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static final int REFRESH = 2;
    private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("CET");
}
