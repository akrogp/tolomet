package com.akrog.tolomet.providers

import com.akrog.tolomet.Measurement
import com.akrog.tolomet.Station
import com.akrog.tolomet.io.Downloader
import kotlinx.datetime.*
import kotlinx.serialization.json.*

class EuskalmetProvider : WindProvider {
    private var downloader: Downloader? = null
    private val timeZone = TimeZone.of("CET")

    override fun getInfoUrl(code: String): String = "https://www.euskalmet.euskadi.eus/observacion/datos-de-estaciones"
    override fun getUserUrl(code: String): String = "https://www.euskalmet.euskadi.eus/observacion/datos-de-estaciones"

    override suspend fun downloadStations(): List<Station> {
        val dw = Downloader()
        dw.userAgent = "Wget/1.21"
        dw.url = "https://www.euskalmet.euskadi.eus/vamet/stations/stationList/webmet00-stationList.json"
        val data = dw.download("ISO-8859-1") ?: return emptyList()
        
        return try {
            val array = Json.parseToJsonElement(data).jsonArray
            array.map { element ->
                val json = element.jsonObject
                Station(
                    name = json["name"]?.jsonPrimitive?.content ?: "",
                    code = json["id"]?.jsonPrimitive?.content ?: "",
                    region = 183,
                    providerType = WindProviderType.Euskalmet,
                    latitude = json["y"]?.jsonPrimitive?.double ?: 0.0,
                    longitude = json["x"]?.jsonPrimitive?.double ?: 0.0
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun refresh(station: Station) {
        travel(station, Clock.System.now().toEpochMilliseconds())
    }

    override suspend fun travel(station: Station, date: Long): Boolean {
        val instant = Instant.fromEpochMilliseconds(date)
        val ldt = instant.toLocalDateTime(timeZone)
        
        val url = "https://www.euskalmet.euskadi.eus/vamet/stations/readings/${station.code}/${ldt.year}/${ldt.monthNumber.toString().padStart(2, '0')}/${ldt.dayOfMonth.toString().padStart(2, '0')}/webmet00-readingsData.json"
        
        val dw = Downloader()
        dw.userAgent = "Wget/1.21"
        dw.url = url
        downloader = dw
        val data = dw.download()
        if (data != null) {
            updateStation(station, data, date)
        }
        return true
    }

    override fun cancel() {
        downloader?.cancel()
    }

    override fun getRefresh(code: String): Int = 10

    private fun updateStation(station: Station, data: String, hist: Long) {
        try {
            val json = Json.parseToJsonElement(data).jsonObject
            json.keys.forEach { key ->
                val sensor = json[key]?.jsonObject ?: return@forEach
                val type = sensor["type"]?.jsonPrimitive?.content
                val name = sensor["name"]?.jsonPrimitive?.content
                
                when (type) {
                    "measuresForWind" -> when (name) {
                        "mean_speed" -> updateMeasurement(station.meteo.windSpeedMed, sensor, hist, 3.6)
                        "mean_direction" -> updateMeasurement(station.meteo.windDirection, sensor, hist, -1.0)
                        "max_speed" -> updateMeasurement(station.meteo.windSpeedMax, sensor, hist, 3.6)
                    }
                    "measuresForAir" -> when (name) {
                        "temperature" -> updateMeasurement(station.meteo.airTemperature, sensor, hist, 1.0)
                        "humidity" -> updateMeasurement(station.meteo.airHumidity, sensor, hist, -1.0)
                        "pressure" -> updateMeasurement(station.meteo.airPressure, sensor, hist, 1.0)
                    }
                    "measuresForSun" -> if (name == "irradiance") {
                        updateMeasurement(station.meteo.irradiance, sensor, hist, 1.0)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateMeasurement(meas: Measurement, sensor: JsonObject, hist: Long, factor: Double) {
        val dataObj = sensor["data"]?.jsonObject ?: return
        val firstKey = dataObj.keys.firstOrNull() ?: return
        val readings = dataObj[firstKey]?.jsonObject ?: return
        
        readings.keys.forEach { timeStr ->
            val date = toEpoch(timeStr, hist)
            val rawVal = readings[timeStr]?.jsonPrimitive?.double ?: return@forEach
            val value = if (factor < 0) {
                kotlin.math.round(rawVal).toDouble()
            } else {
                rawVal * factor
            }
            meas.put(date, value)
        }
    }

    private fun toEpoch(str: String, baseDate: Long): Long {
        val fields = str.split(":")
        val hours = fields[0].toInt()
        val minutes = fields[1].toInt()
        
        val baseInstant = Instant.fromEpochMilliseconds(baseDate)
        val ldt = baseInstant.toLocalDateTime(timeZone)
        
        val newLdt = LocalDateTime(ldt.year, ldt.month, ldt.dayOfMonth, hours, minutes, 0, 0)
        return newLdt.toInstant(timeZone).toEpochMilliseconds()
    }
}
