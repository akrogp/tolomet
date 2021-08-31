package com.akrog.tolomet.providers;

import com.akrog.tolomet.Manager;
import com.akrog.tolomet.Spot;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DhvSpotProviderTest {
    @Test
    public void test() {
        Manager manager = new Manager();
        DhvSpotProvider dhv = new DhvSpotProvider();
        List<Spot> spots = dhv.downloadSpots();
        assertFalse(spots.isEmpty());
    }
}
