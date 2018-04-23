package com.akrog.tolomet.providers;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by gorka on 23/04/18.
 */

public class WeatherDisplayProviderTest extends BaseProviderTest {
    public WeatherDisplayProviderTest() {
        super(new WeatherDisplayProvider());
    }

    @Test
    public void downloadData() {
        assertTrue(testDownload("Santander"));
    }
}
