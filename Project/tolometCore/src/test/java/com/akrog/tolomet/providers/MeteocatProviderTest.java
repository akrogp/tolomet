package com.akrog.tolomet.providers;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gorka on 18/02/16.
 */
public class MeteocatProviderTest extends BaseProviderTest {
    public MeteocatProviderTest() {
        super(new MeteocatProvider());
    }

    @Test
    public void downloadData() {
        assertTrue(testDownload("WM"));
    }
}