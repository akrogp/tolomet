package com.akrog.tolomet.providers;

import com.akrog.tolomet.Station;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gorka on 18/02/16.
 */
public class AemetProviderTest extends BaseProviderTest {
    public AemetProviderTest() {
        super(new AemetProvider());
    }

    @Test
    public void downloadData() {
        assertTrue(testDownload("1059X"));
    }
}