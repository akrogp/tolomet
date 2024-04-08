package com.akrog.tolomet.providers;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Created by gorka on 18/02/16.
 */
public class EuskalmetProviderOrigTest extends BaseProviderTest {
    public EuskalmetProviderOrigTest() {
        super(new EuskalmetProviderOrig());
    }

    @Test
    public void downloadData() {
        assertTrue(testDownload("C042"));
    }
}
