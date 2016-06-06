package com.akrog.tolomet.providers;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gorka on 18/02/16.
 */
public class RedVigiaProviderTest extends BaseProviderTest {
    public RedVigiaProviderTest() {
        super(new RedVigiaProvider());
    }

    @Test
    public void downloadData() {
        assertTrue(testDownload("Santoña"));
    }
}