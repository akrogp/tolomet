package com.akrog.tolomet.providers;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MeteoClimaticProviderTest extends BaseProviderTest {
    public MeteoClimaticProviderTest() {
        super(new MeteoClimaticProvider());
    }

    @Test
    public void downloadData() {
        //assertTrue(testDownload("ESCTB3900000039776A"));
        assertTrue(testDownload("ESCAT0800000008014C"));
    }
}
