package com.akrog.tolomet.providers;

import com.akrog.tolomet.Station;

/**
 * Created by gorka on 18/02/16.
 */
public class BaseProviderTest {
    public BaseProviderTest( WindProvider provider ) {
        this.provider = provider;
    }

    public boolean testDownload(String code) {
        Station station = new Station();
        station.setCode(code);
        provider.refresh(station);
        return !station.isEmpty();
    }

    private final WindProvider provider;
}
