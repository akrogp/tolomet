package com.akrog.tolomet.providers;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gorka on 18/02/16.
 */
public class CurrentVantageProviderTest extends BaseProviderTest {
    public CurrentVantageProviderTest() {
        super(new CurrentVantageProvider());
    }

    @Test
    public void downloadData() {
        assertTrue(testDownload("RCNL"));
    }
}