package com.akrog.tolomet

class Meteo {
    val windDirection = Measurement(0f, 360f)
    val windSpeedMed = Measurement(0f, null)
    val windSpeedMax = Measurement(0f, null)
    val airHumidity = Measurement(0f, 100f)
    val airTemperature = Measurement(-100f, 100f)
    val airPressure = Measurement(0f, null)
    val irradiance = Measurement(0f, null)

    private val measurements = listOf(
        windDirection,
        windSpeedMed,
        windSpeedMax,
        airHumidity,
        airTemperature,
        airPressure,
        irradiance
    )

    fun clear() {
        measurements.forEach { it.clear() }
    }

    fun clear(fromStamp: Long) {
        measurements.forEach { it.clear(fromStamp) }
    }

    fun merge(other: Meteo) {
        measurements.zip(other.measurements).forEach { (m1, m2) ->
            m1.merge(m2)
        }
    }

    fun isEmpty(): Boolean = measurements.all { it.isEmpty() }

    fun getStamp(): Long? {
        return measurements.mapNotNull { it.getStamp() }.maxOrNull()
    }

    fun getBegin(): Long? {
        return measurements.mapNotNull { it.getBegin() }.minOrNull()
    }
}
