package com.akrog.tolomet.providers

import com.akrog.tolomet.Measurement
import com.akrog.tolomet.Station
import com.akrog.tolomet.io.Downloader
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class NorometProvider : WindProvider {
    private var downloader: Downloader? = null

    override fun getInfoUrl(code: String): String {
        return "https://noromet.org/index2.html?id_estacion=$code"
    }

    override fun getUserUrl(code: String): String = getInfoUrl(code)

    override suspend fun downloadStations(): List<Station> {
        try {
            val dw = Downloader()
            dw.url = "https://noromet.org/privado/datosInicioV2.json"
            downloader = dw
            val data = dw.download() ?: throw IllegalStateException("Noromet station request returned no content")
            ensureJsonPayload(data, "stations")

            val array = Json.parseToJsonElement(data).jsonArray
            return array.mapNotNull { element ->
                val json = element.jsonObject
                val code = json["id_estacion"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null
                val name = json["name"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null
                val latLng = json["latLng"]?.jsonArray ?: return@mapNotNull null
                if (latLng.size < 2) return@mapNotNull null

                val latitude = latLng[0].jsonPrimitive.contentOrNull?.toDoubleOrNull() ?: return@mapNotNull null
                val longitude = latLng[1].jsonPrimitive.contentOrNull?.toDoubleOrNull() ?: return@mapNotNull null

                Station(
                    name = name,
                    code = code,
                    region = 1,
                    providerType = WindProviderType.Noromet,
                    latitude = latitude,
                    longitude = longitude
                )
            }
        } finally {
            downloader = null
        }
    }

    override suspend fun refresh(station: Station) {
        travel(station, Clock.System.now().toEpochMilliseconds())
    }

    override suspend fun travel(station: Station, date: Long): Boolean {
        try {
            val dw = Downloader()
            dw.url = "https://noromet.org/privado/PostGetFiles/GetGraphicFiveMin.php"
            dw.setHeader("Content-Type", "application/x-www-form-urlencoded")
            downloader = dw
            val data = dw.downloadWithBody("id_estacion=${station.code}") ?: return false
            ensureJsonPayload(data, "measurements")
            applySeries(station, data, date)
            return true
        } finally {
            downloader = null
        }
    }

    override fun cancel() {
        downloader?.cancel()
    }

    override fun getRefresh(code: String): Int = 5

    private fun applySeries(station: Station, data: String, baseDate: Long) {
        try {
            val json = Json.parseToJsonElement(data).jsonObject
            val hours = json["hour"]?.jsonArray ?: return
            val stamps = buildStamps(hours, baseDate)

            saveSeries(station.meteo.airTemperature, json, "temp", stamps)
            saveSeries(station.meteo.airHumidity, json, "hum", stamps)
            saveSeries(station.meteo.airPressure, json, "bar", stamps)
            saveSeries(station.meteo.windDirection, json, "wind_dir", stamps)
            saveSeries(station.meteo.windSpeedMed, json, "wind", stamps)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun buildStamps(hours: JsonArray, baseDate: Long): List<Long> {
        if (hours.isEmpty()) return emptyList()

        val tz = TimeZone.of("CET")
        val base = Instant.fromEpochMilliseconds(baseDate).toLocalDateTime(tz)
        val dayStart = LocalDateTime(base.year, base.month, base.dayOfMonth, 0, 0)
            .toInstant(tz)
            .toEpochMilliseconds()
        var dayStartMs = dayStart

        val firstHour = parseHour(hours[0].jsonPrimitive.contentOrNull)
        val lastHour = parseHour(hours[hours.lastIndex].jsonPrimitive.contentOrNull)
        if (firstHour > lastHour) {
            dayStartMs -= DAY_MS
        }

        var previousHour = firstHour
        val result = ArrayList<Long>(hours.size)
        hours.forEach { item ->
            val raw = item.jsonPrimitive.contentOrNull
            val (hour, minute) = parseHourMinute(raw)
            if (hour < previousHour) {
                dayStartMs += DAY_MS
            }
            previousHour = hour

            result.add(dayStartMs + hour * HOUR_MS + minute * MINUTE_MS)
        }
        return result
    }

    private fun parseHour(value: String?): Int {
        if (value == null) return 0
        return value.substringBefore(':').toIntOrNull() ?: 0
    }

    private fun parseHourMinute(value: String?): Pair<Int, Int> {
        if (value == null) return 0 to 0
        val parts = value.split(':')
        val hour = parts.getOrNull(0)?.toIntOrNull() ?: 0
        val minute = parts.getOrNull(1)?.toIntOrNull() ?: 0
        return hour to minute
    }

    private fun saveSeries(
        measurement: Measurement,
        root: JsonObject,
        key: String,
        stamps: List<Long>
    ) {
        val series = root[key]?.jsonArray ?: return
        val count = minOf(series.size, stamps.size)
        for (i in 0 until count) {
            val value = series[i].jsonPrimitive.contentOrNull?.toDoubleOrNull() ?: continue
            if (!value.isFinite()) continue
            measurement.put(stamps[i], value)
        }
    }

    companion object {
        private const val MINUTE_MS = 60_000L
        private const val HOUR_MS = 60L * MINUTE_MS
        private const val DAY_MS = 24L * HOUR_MS
    }

    private fun ensureJsonPayload(data: String, phase: String) {
        val trimmed = data.trimStart()
        if (trimmed.startsWith("[") || trimmed.startsWith("{")) {
            return
        }
        if (trimmed.contains("Just a moment", ignoreCase = true) ||
            trimmed.contains("cloudflare", ignoreCase = true)
        ) {
            throw IllegalStateException("Noromet access blocked by Cloudflare challenge while loading $phase")
        }
        throw IllegalStateException("Unexpected Noromet response while loading $phase")
    }
}
