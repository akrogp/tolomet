package com.akrog.tolomet.providers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by gorka on 18/02/16.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AemetProviderTest.class,
        EuskalmetProviderTest.class,
        CurrentVantageProviderTest.class,
        LaRiojaProviderTest.class,
        MetarProviderTest.class,
        MeteocatProviderTest.class,
        MeteoGaliciaProviderTest.class,
        MeteoNavarraProviderTest.class,
        PradesProviderTest.class,
        RedVigiaProviderTest.class
})
public class AllProvidersTest {
}
