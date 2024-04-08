package com.akrog.tolomet.providers;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Created by gorka on 18/02/16.
 */
public class EuskalmetProviderNewTest extends BaseProviderTest {
    public EuskalmetProviderNewTest() {
        super(new EuskalmetProviderNew());
    }

    @Test
    public void downloadData() {
        assertTrue(testDownload("C042"));
    }
}
