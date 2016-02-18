package com.akrog.tolomet.providers;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gorka on 18/02/16.
 */
public class PradesProviderTest extends BaseProviderTest {
    public PradesProviderTest() {
        super(new PradesProvider());
    }

    @Test
    public void downloadData() {
        assertTrue(testDownload("montblanc"));
    }
}