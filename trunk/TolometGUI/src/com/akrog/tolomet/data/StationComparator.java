package com.akrog.tolomet.data;

import java.util.Comparator;

public class StationComparator implements Comparator<Station> {

	public int compare(Station lhs, Station rhs) {		
		return (int)(lhs.distance - rhs.distance);
	}

}
