package com.akrog.tolomet.providers;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gorka on 18/02/16.
 */
public class MetarProviderTest extends BaseProviderTest {
    public MetarProviderTest() {
        super(new MetarProvider());
    }

    @Test
    public void downloadData() {
        assertTrue(testDownload("LEBB"));
    }
}