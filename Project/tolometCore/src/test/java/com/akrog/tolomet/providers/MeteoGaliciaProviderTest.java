package com.akrog.tolomet.providers;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gorka on 18/02/16.
 */
public class MeteoGaliciaProviderTest extends BaseProviderTest {
    public MeteoGaliciaProviderTest() {
        super(new MeteoGaliciaProvider());
    }

    @Test
    public void downloadData() {
        assertTrue(testDownload("19070"));
    }
}