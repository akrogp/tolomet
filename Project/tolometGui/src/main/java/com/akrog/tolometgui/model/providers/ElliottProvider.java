package com.akrog.tolometgui.model.providers;

import com.akrog.tolomet.io.Downloader;
import com.akrog.tolomet.io.XmlParser;
import com.akrog.tolomet.io.ZipDownloader;
import com.akrog.tolometgui.model.db.SpotEntity;
import com.akrog.tolometgui.model.db.SpotProviderType;
import com.akrog.tolometgui.model.db.SpotType;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class ElliottProvider implements SpotProvider {
    @Override
    public List<SpotEntity> downloadSpots() {
        List<SpotEntity> spots = new ArrayList<>();
        dw = new ZipDownloader();
        dw.setUrl("https://www.google.com/maps/d/kml?mid=1TuFqknQn4vtKYr7VYwpn1BUJjQw");
        String data = dw.download();
        try(BufferedReader br = new BufferedReader(new StringReader(data))) {
            String line;
            SpotEntity spot = null;
            int coordsCount = -1;
            boolean skip = false;
            while( (line = br.readLine()) != null ) {
                line = line.trim();
                if( line.equals("<Placemark>") ) {
                    spot = new SpotEntity();
                    skip = false;
                } else if( spot == null )
                    continue;
                else if( line.startsWith("<name>") )
                    spot.setName(XmlParser.getValue(line));
                else if( line.startsWith("<description>") )
                    spot.setDesc(XmlParser.getExpandedValue(line));
                else if( line.startsWith("<styleUrl>") ) {
                    if( line.contains("icon-503-DB4436") )
                        spot.setType(SpotType.LANDING);
                    else if (line.contains("icon-503-0BA9CC"))
                        spot.setType(SpotType.TAKEOFF);
                }
                else if( line.equals("<coordinates>") )
                    coordsCount = 0;
                else if( line.equals("</coordinates>") )
                    coordsCount = -1;
                else if( coordsCount == 0 ) {
                    String[] fields = line.split(",");
                    spot.setLatitude(Double.parseDouble(fields[1]));
                    spot.setLongitude(Double.parseDouble(fields[0]));
                    coordsCount++;
                } else if( coordsCount > 1 )
                    skip = true;
                else if( line.equals("</Placemark>") ) {
                    if( !skip && spot.getType() != null ) {
                        spot.setProvider(SpotProviderType.ElliottParagliding);
                        spot.setId(String.valueOf(new Double(spot.getLatitude()).hashCode()*37+new Double(spot.getLongitude()).hashCode()));
                        spots.add(spot);
                    }
                    skip = false;
                    spot = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dw = null;
        }
        return spots;
    }

    @Override
    public void cancel() {
        if( dw != null ) {
            dw.cancel();
            dw = null;
        }
    }

    private Downloader dw;
}
