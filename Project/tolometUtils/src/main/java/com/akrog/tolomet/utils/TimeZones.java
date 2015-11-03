package com.akrog.tolomet.utils;

import java.util.TimeZone;

public class TimeZones {

	public static void main(String[] args) {
		for( String id : TimeZone.getAvailableIDs() )
			System.out.println(id);
	}

}
