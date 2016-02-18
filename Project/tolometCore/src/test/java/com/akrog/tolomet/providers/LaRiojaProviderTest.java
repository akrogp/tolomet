package com.akrog.tolomet.providers;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gorka on 18/02/16.
 */
public class LaRiojaProviderTest extends BaseProviderTest {
    public LaRiojaProviderTest() {
        super(new LaRiojaProvider());
    }

    @Test
    public void downloadData() {
        assertTrue(testDownload("1"));
    }
}