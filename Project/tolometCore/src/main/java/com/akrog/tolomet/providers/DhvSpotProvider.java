package com.akrog.tolomet.providers;

import com.akrog.tolomet.Manager;
import com.akrog.tolomet.Spot;
import com.akrog.tolomet.SpotType;
import com.akrog.tolomet.io.Downloader;
import com.akrog.tolomet.io.XmlParser;
import com.akrog.tolomet.io.ZipDownloader;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class DhvSpotProvider implements SpotProvider {
    private static final String[] REGIONS = {"alle", "al", "ad", "by", "be", "ba", "bg", "de", "dk", "ee", "fi", "fr", "gr", "ie", "is", "il", "it", "xk", "hr", "lv", "li", "lt", "lu", "mt", "mk", "md", "me", "nl", "no", "pl", "pt", "ro", "ru", "se", "ch", "rs", "sk", "si", "es", "cz", "tr", "ua", "hu", "gb", "cy", "at"};

    @Override
    public List<Spot> downloadSpots() {
        List<Spot> spots = new ArrayList<>();
        for( String region : REGIONS ) {
            List<Spot> tmp = downloadSpots(region);
            spots.addAll(tmp);
        }
        return spots;
    }

    private List<Spot> downloadSpots(String region) {
        List<Spot> spots = new ArrayList<>();
        dw = new ZipDownloader();
        dw.setUrl(String.format("https://service.dhv.de/dbfiles/managed/gelaendedaten/dhvxml/dhvgelaende_dhvxml_%s.zip?format=dhvxml", region));
        String data = dw.download();
        try(BufferedReader br = new BufferedReader(new StringReader(data))) {
            String line;
            Spot spot = null;
            String name = null;
            String alt = null;
            String dirs = null;
            while( (line = br.readLine()) != null ) {
                line = line.trim();
                if( line.equals("<FlyingSite>") ) {
                    spot = null;
                    name = null;
                    alt = null;
                    dirs = null;
                } else if( line.startsWith("<SiteName>") )
                    name = XmlParser.getExpandedValue(line);
                else if( line.equals("<Location>") )
                    spot = new Spot();
                else if( spot != null ) {
                    if( line.startsWith("<Coordinates>") ) {
                        String[] coords = XmlParser.getValue(line).split(",");
                        spot.setLatitude(Double.parseDouble(coords[1]));
                        spot.setLongitude(Double.parseDouble(coords[0]));
                    } else if( line.startsWith("<LocationType>") ) {
                        String type = XmlParser.getValue(line);
                        if( type.equals("1") )
                            spot.setType(SpotType.TAKEOFF);
                        else if( type.equals("2") )
                            spot.setType(SpotType.LANDING);
                        else
                            spot = null;
                    } else if( line.startsWith("<Directions>") )
                        dirs = parseDirection(XmlParser.getValue(line));
                    else if( line.startsWith("<Altitude>") )
                        alt = XmlParser.getValue(line);
                    else if( line.startsWith("<LocationID>") )
                        spot.setId(XmlParser.getValue(line));
                    else if( line.equals("</Location>") ) {
                        spot.setProvider(SpotProviderType.DhvDatabase);
                        spot.setName(String.format("%s (DHV - %s)", name, spot.getType() == SpotType.LANDING ? "aterrizaje" : "despegue"));
                        StringBuilder desc = new StringBuilder();
                        if( alt != null ) {
                            desc.append(alt);
                            desc.append('m');
                        } if( dirs != null ) {
                            if( alt != null )
                                desc.append('\n');
                            desc.append(dirs);
                        }
                        spot.setDesc(desc.toString());
                        spots.add(spot);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dw = null;
        }
        return spots;
    }

    private String parseDirection(String str) {
        String[] directions = Manager.getDirections();
        if( areConsecutive(str) )
            return directions[getIndex(str.charAt(0))] + "-" + directions[getIndex(str.charAt(str.length()-1))];
        StringBuilder sb = new StringBuilder();
        for( int i = 0; i < str.length(); i++ ) {
            if( i != 0 )
                sb.append(',');
            sb.append(directions[getIndex(str.charAt(i))]);
        }
        return sb.toString();
    }

    private boolean areConsecutive(String directions) {
        if( directions.length() < 2 )
            return false;
        int prev = parseDir(directions.charAt(0));
        for( int i = 1; i < directions.length(); i++ ) {
            int curr = parseDir(directions.charAt(i));
            if( curr - prev != 1 )
                return false;
            prev = curr;
        }
        return true;
    }

    private int parseDir(char dir) {
        if( Character.isDigit(dir) )
            return dir-'1'+2;
        return dir-'A'+11;
    }

    private int getIndex(char dir) {
        int i = parseDir(dir);
        return i < 16 ? i : i - 16;
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
