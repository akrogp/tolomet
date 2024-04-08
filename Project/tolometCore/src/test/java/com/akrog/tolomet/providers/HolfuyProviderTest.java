package com.akrog.tolomet.providers;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Created by gorka on 4/04/16.
 */
public class HolfuyProviderTest extends BaseProviderTest {
    public HolfuyProviderTest() {
        super(new HolfuyProvider());
    }

    @Test
    public void downloadData() {
        assertTrue(testDownload("s310"));
    }
}
