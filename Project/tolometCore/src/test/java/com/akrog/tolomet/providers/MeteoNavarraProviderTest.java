package com.akrog.tolomet.providers;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gorka on 18/02/16.
 */
public class MeteoNavarraProviderTest extends BaseProviderTest {
    public MeteoNavarraProviderTest() {
        super(new MeteoNavarraProvider());
    }

    @Test
    public void downloadData() {
        assertTrue(testDownload("GN33"));
    }
}