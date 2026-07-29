package com.akrog.tolomet.providers

import com.akrog.tolomet.Station
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class AemetProviderTest {
    @Test
    fun parseStationsData_mapsAemetStationFields() {
        val provider = AemetProvider()
        val data = """
            [
              {"ubi":"SIERRA DEL CID","lat":38.500,"lon":-0.300,"idema":"A123"},
              {"ubi":"Invalid Missing Code","lat":40.0,"lon":-3.0}
            ]
        """.trimIndent()

        val stations = provider.parseStationsData(data)

        assertEquals(1, stations.size)
        val station = stations.first()
        assertEquals("Sierra Del Cid", station.name)
        assertEquals("A123", station.code)
        assertEquals(WindProviderType.Aemet, station.providerType)
        assertEquals(38.5, station.latitude)
        assertEquals(-0.3, station.longitude)
    }

    @Test
    fun applyObservationsData_updatesMeteoAndConvertsWindSpeed() {
        val provider = AemetProvider()
        val station = Station(code = "A123", providerType = WindProviderType.Aemet)
        val data = """
            [
              {
                "fint":"2026-07-25T12:30:00",
                "dv":270,
                "vv":5,
                "vmax":8,
                "ta":26.4,
                "hr":55,
                "pres":1012.3
              }
            ]
        """.trimIndent()

        provider.applyObservationsData(station, data)

        assertFalse(station.meteo.isEmpty())

        val stamp = station.meteo.windDirection.getTimes().single()
        assertEquals(270.0, station.meteo.windDirection.get(stamp)?.toDouble())
        assertEquals(18.0, station.meteo.windSpeedMed.get(stamp)?.toDouble())
        assertEquals(28.8, station.meteo.windSpeedMax.get(stamp)?.toDouble())
        assertEquals(26.4, station.meteo.airTemperature.get(stamp)?.toDouble())
        assertEquals(55.0, station.meteo.airHumidity.get(stamp)?.toDouble())
        assertEquals(1012.3, station.meteo.airPressure.get(stamp)?.toDouble())
    }
}
