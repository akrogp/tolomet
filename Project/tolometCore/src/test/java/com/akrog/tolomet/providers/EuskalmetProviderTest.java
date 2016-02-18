package com.akrog.tolomet.providers;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gorka on 18/02/16.
 */
public class EuskalmetProviderTest extends BaseProviderTest {
    public EuskalmetProviderTest() {
        super(new EuskalmetProvider());
    }

    @Test
    public void downloadData() {
        assertTrue(testDownload("C042"));
    }
}