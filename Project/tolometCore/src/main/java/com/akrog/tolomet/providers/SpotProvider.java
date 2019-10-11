package com.akrog.tolomet.providers;

import com.akrog.tolomet.Spot;

import java.util.List;

public interface SpotProvider {
    List<Spot> downloadSpots();
    void cancel();
}
