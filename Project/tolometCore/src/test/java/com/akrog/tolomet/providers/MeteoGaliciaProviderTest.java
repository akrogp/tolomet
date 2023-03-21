package com.akrog.tolomet.providers;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Created by gorka on 18/02/16.
 */
public class MeteoGaliciaProviderTest extends BaseProviderTest {
    public MeteoGaliciaProviderTest() {
        super(new MeteoGaliciaProvider());
    }

    @Test
    public void downloadData() {
        assertTrue(testDownload("10045"));
    }
}