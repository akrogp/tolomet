package com.akrog.tolomet.providers;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Created by gorka on 23/04/18.
 */

public class WeatherDisplayProviderTest extends BaseProviderTest {
    public WeatherDisplayProviderTest() {
        super(new WeatherDisplayProvider());
    }

    @Test
    public void downloadData() {
        assertTrue(testDownload("RCNL"));
    }
}
