package com.akrog.tolomet.providers;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Created by gorka on 18/02/16.
 */
public class SmartyPlanetProviderTest extends BaseProviderTest {
    public SmartyPlanetProviderTest() {
        super(new SmartyPlanetProvider());
    }

    @Test
    public void downloadData() {
        assertTrue(testDownload("639"));
    }
}