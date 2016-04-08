package com.akrog.tolomet.providers;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by gorka on 8/04/16.
 */
public class PiouProviderTest extends BaseProviderTest {
    public PiouProviderTest() {
        super(new PiouProvider());
    }

    @Test
    public void downloadData() {
        assertTrue(testDownload("220"));
    }
}
