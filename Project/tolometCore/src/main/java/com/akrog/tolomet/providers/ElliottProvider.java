package com.akrog.tolomet.providers;

import com.akrog.tolomet.Spot;
import com.akrog.tolomet.SpotType;
import com.akrog.tolomet.io.Downloader;
import com.akrog.tolomet.io.ZipDownloader;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class ElliottProvider implements SpotProvider {
    @Override
    public List<Spot> downloadSpots() {
        List<Spot> spots = new ArrayList<>();
        dw = new ZipDownloader();
        dw.setUrl("https://www.google.com/maps/d/kml?mid=1AC_eEAVLOu__evAVQ85dvr6CCcX0ajla");
        String data = dw.download();
        try(BufferedReader br = new BufferedReader(new StringReader(data))) {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(br);
            Spot spot = null;
            while( reader.hasNext() ) {
                XMLEvent nextEvent = reader.nextEvent();
                if (nextEvent.isStartElement()) {
                    StartElement startElement = nextEvent.asStartElement();
                    String tag = startElement.getName().getLocalPart();
                    if( tag.equals("Placemark") ) {
                        spot = new Spot();
                    } else if( spot == null ) {
                        continue;
                    } else if( tag.equals("name") )
                        spot.setName(getNextValue(reader));
                    else if( tag.equals("description") )
                        spot.setDesc(getNextValue(reader));
                    else if( tag.equals("styleUrl") ) {
                        String value = getNextValue(reader);
                        if( value.contains("icon-503-DB4436") )
                            spot.setType(SpotType.LANDING);
                        else if (value.contains("icon-503-0BA9CC"))
                            spot.setType(SpotType.TAKEOFF);
                        else if (value.contains("icon-1369"))
                            spot.setType(SpotType.TREKKING);
                    }
                    else if( tag.equals("coordinates") ) {
                        String[] fields = getNextValue(reader).split(",");
                        spot.setLatitude(Double.parseDouble(fields[1]));
                        spot.setLongitude(Double.parseDouble(fields[0]));
                    }
                }
                if (nextEvent.isEndElement()) {
                    EndElement endElement = nextEvent.asEndElement();
                    if( endElement.getName().getLocalPart().equals("Placemark") ) {
                        if( spot != null && spot.getType() != null ) {
                            spot.setProvider(SpotProviderType.ElliottParagliding);
                            spot.setId(String.valueOf(new Double(spot.getLatitude()).hashCode()*37+new Double(spot.getLongitude()).hashCode()));
                            spots.add(spot);
                        }
                        spot = null;
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

    private String getNextValue(XMLEventReader reader) throws XMLStreamException {
        XMLEvent nextEvent = reader.nextEvent();
        String value = nextEvent.asCharacters().getData();
        return value;
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
