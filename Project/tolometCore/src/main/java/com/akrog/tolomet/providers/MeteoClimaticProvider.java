package com.akrog.tolomet.providers;

import com.akrog.tolomet.Meteo;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gorka on 28/11/16.
 */

public class MeteoClimaticProvider extends BaseProvider {
    public MeteoClimaticProvider() {
        super(REFRESH);
    }

    @Override
    public void configureDownload(Downloader downloader, Station station) {
        downloader.setUrl(String.format("https://www.meteoclimatic.net/feed/rss/%s",station.getCode()));
    }

    @Override
    public boolean configureDownload(Downloader downloader, Station station, long date) {
        return false;
    }

    @Override
    public List<Station> downloadStations() {
        try {
            List<Station> result = new ArrayList<>();
            result.addAll(downloadStations("ES"));
            result.addAll(downloadStations("PT"));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            downloader = null;
        }
        return null;
    }

    private List<Station> downloadStations(String country) throws Exception {
        downloader = new Downloader();
        downloader.setUrl("https://www.meteoclimatic.com/feed/rss/" + country);
        String data = downloader.download();
        List<Station> result = new ArrayList<>();

        XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(new StringReader(data));

        int event = parser.getEventType();
        String text = null;
        String name = null, code = null;
        Double lat = null, lon = null;
        while( event != XmlPullParser.END_DOCUMENT ) {
            String tag = parser.getName();
            switch (event) {
                case XmlPullParser.TEXT:
                    text = parser.getText();
                    break;
                case XmlPullParser.END_TAG:
                    if( tag.equals("title") )
                        name = text.replaceAll(" \\(.*", "");
                    else if( tag.equals("link") )
                        code = text.replaceAll(".*/","");
                    else if( tag.equals("geo:lat") )
                        lat = Double.parseDouble(text);
                    else if( tag.equals("geo:long") )
                        lon = Double.parseDouble(text);
                    else if( tag.equals("item") ) {
                        Station station = new Station();
                        station.setCode(code);
                        station.setName(name);
                        station.setLatitude(lat);
                        station.setLongitude(lon);
                        station.setProviderType(WindProviderType.MeteoClimatic);
                        if( station.isFilled() )
                            result.add(station);
                    }
                    break;
            }
            event = parser.next();
        }
        return result;
    }

    @Override
    public void updateStation(Station station, String data) throws Exception {
        int begin = data.indexOf("Actualizado:");
        if( begin < 0 ) return;
        int end = data.indexOf("</li>",begin);
        if( end < 0 ) return;
        long stamp = df.parse(data.substring(begin+12,end)).getTime();

        begin = data.indexOf(String.format("[[<%s;", station.getCode()));
        if( begin < 0 ) return;
        end = data.indexOf("]]",begin);
        if( end < 0 ) return;
        String[] fields = data.substring(begin,end).replaceAll(",",".").split("\\)?;\\(");
        String[] subfields;
        Meteo meteo = station.getMeteo();
        double value;

        subfields = fields[1].split(";");
        meteo.getAirTemperature().put(stamp,Double.parseDouble(subfields[0]));

        subfields = fields[2].split(";");
        if( (value=Double.parseDouble(subfields[0])) < 0.0 )
            return;
        meteo.getAirHumidity().put(stamp,value);

        subfields = fields[3].split(";");
        if( (value=Double.parseDouble(subfields[0])) < 0.0 )
            return;
        meteo.getAirPressure().put(stamp,value);

        subfields = fields[4].split(";");
        if( (value = Double.parseDouble(subfields[0])) < 0.0 )
            return;
        meteo.getWindSpeedMed().put(stamp,value);
        //meteo.getWindSpeedMax().put(stamp,Double.parseDouble(subfields[1]));
        meteo.getWindDirection().put(stamp,Double.parseDouble(subfields[2]));
    }

    @Override
    public String getInfoUrl(String code) {
        return String.format("https://www.meteoclimatic.net/perfil/%s#toggle",code);
    }

    @Override
    public String getUserUrl(String code) {
        return String.format("https://www.meteoclimatic.net/perfil/%s#content",code);
    }

    private static final int REFRESH = 15;
    private static final DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm z");
}
