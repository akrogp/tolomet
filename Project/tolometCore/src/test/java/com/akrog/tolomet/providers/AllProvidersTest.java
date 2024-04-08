package com.akrog.tolomet.providers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by gorka on 18/02/16.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AemetProviderTest.class,
        //CurrentVantageProviderTest.class,
        DhvSpotProviderTest.class,
        EuskalmetProviderOrigTest.class,
        EuskalmetProviderNewTest.class,
        EuskalmetProviderTest.class,
        //HolfuyProviderTest.class,
        LaRiojaProviderTest.class,
        MetarProviderTest.class,
        MeteocatProviderTest.class,
        MeteoClimaticProviderTest.class,
        MeteoGaliciaProviderTest.class,
        MeteoNavarraProviderTest.class,
        PiouProviderTest.class,
        //PradesProviderTest.class,
        //RedVigiaProviderTest.class,
        SmartyPlanetProviderTest.class,
        WeatherDisplayProviderTest.class

})
public class AllProvidersTest {
}
