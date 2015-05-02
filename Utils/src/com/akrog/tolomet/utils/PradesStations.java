package com.akrog.tolomet.utils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import uk.me.jstott.jcoord.LatLng;
import uk.me.jstott.jcoord.UTMRef;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;
import com.akrog.tolomet.providers.WindProviderType;

public class PradesStations {
	public static void main( String[] args ) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter("/home/gorka/prades.csv");
		for( PradesStation station : getLinks() ) {
			System.out.println(String.format("%s:%s:%f:%f", station.getCode(), station.getName(), station.getX(), station.getY()));
			pw.println(String.format("%s,%s",station.getCode(),station.getLink()));
		}
		pw.close();
	}
	
	public static List<Station> getStations() {
		List<Station> list = new ArrayList<Station>();
		for( PradesStation prades : getLinks() ) {
			Station station = new Station();
			station.setCode(prades.getCode());
			station.setName(prades.getName());
			station.setCountry("ES");
			station.setLatitude(prades.x);
			station.setLongitude(prades.y);
			station.setRegion(7);
			station.setProviderType(WindProviderType.Prades);
			if( station.getCode().equals("lafebro") )
				station.setName("La Febró");
			list.add(station);
		}
		return list;
	}

	private static List<PradesStation> getLinks() {
		Downloader downloader = new Downloader();
		downloader.setUrl("http://www.meteoprades.net/estacions/");
		String str = downloader.download();		 
		List<PradesStation> list = new ArrayList<PradesStations.PradesStation>();
		boolean first = true;
		for( String block : str.split("titolet") ) {
			if( first ) {
				first = false;
				continue;
			}
			String[] fields = block.split("[<>]");
			PradesStation station = new PradesStation();
			station.link = fields[2].split("\"")[1];
			station.name = fields[3];
			station.x = Double.parseDouble(fields[13]);
			station.y = Double.parseDouble(fields[17]);
			getCode(station);
			getLatLon(station);
			list.add(station);
		}
		return list;
	}
	
	private static void getLatLon(PradesStation station) {
		UTMRef utm = new UTMRef(station.x, station.y, 'T', 31);
		LatLng ll = utm.toLatLng();
		station.x = ll.getLat();
		station.y = ll.getLng();
	}
	
	private static void getCode(PradesStation station) {
		Downloader downloader = new Downloader();
		downloader.setUrl(station.link);
		String str = downloader.download();
		for(String line : str.split("\\n") )
			if( line.contains("estacio_tabs") ) {
				station.code = line.split("=")[3].split("\"")[0];
				break;
			}
	}
	
	public static class PradesStation {
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getLink() {
			return link;
		}
		public void setLink(String link) {
			this.link = link;
		}
		public double getX() {
			return x;
		}
		public void setX(double x) {
			this.x = x;
		}
		public double getY() {
			return y;
		}
		public void setY(double y) {
			this.y = y;
		}
		private String name, code, link;
		private double x, y;
	}
}
