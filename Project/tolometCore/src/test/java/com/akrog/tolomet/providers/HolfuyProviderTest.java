package com.akrog.tolomet.providers;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by gorka on 4/04/16.
 */
public class HolfuyProviderTest extends BaseProviderTest {
    public HolfuyProviderTest() {
        super(new HolfuyProvider());
    }

    @Test
    public void downloadData() {
        assertTrue(testDownload("310"));
    }
}
