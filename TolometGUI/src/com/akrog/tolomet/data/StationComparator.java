package com.akrog.tolomet.data;

import java.util.Comparator;

public class StationComparator implements Comparator<StationOld> {

	public int compare(StationOld lhs, StationOld rhs) {		
		return (int)(lhs.distance - rhs.distance);
	}

}
