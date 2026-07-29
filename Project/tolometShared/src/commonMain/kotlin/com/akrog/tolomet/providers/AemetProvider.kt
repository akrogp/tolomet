package com.akrog.tolomet.providers

import com.akrog.tolomet.Station
import com.akrog.tolomet.io.Downloader
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class AemetProvider(
    private val apiKeyProvider: (() -> String?)? = null
) : WindProvider {
    private var downloader: Downloader? = null

    override fun getInfoUrl(code: String): String {
        return "http://www.aemet.es/es/eltiempo/observacion/ultimosdatos?l=$code&datos=det&w=0"
    }

    override fun getUserUrl(code: String): String = getInfoUrl(code)

    override suspend fun downloadStations(): List<Station> {
        val data = downloadAemetData("https://opendata.aemet.es/opendata/api/observacion/convencional/todas")
            ?: return emptyList()

        return parseStationsData(data)
    }

    override suspend fun refresh(station: Station) {
        val data = downloadAemetData(
            "https://opendata.aemet.es/opendata/api/observacion/convencional/datos/estacion/${station.code}"
        ) ?: return

        applyObservationsData(station, data)
    }

    override suspend fun travel(station: Station, date: Long): Boolean = false

    override fun cancel() {
        downloader?.cancel()
    }

    override fun getRefresh(code: String): Int = 60

    internal fun parseStationsData(data: String): List<Station> {
        return try {
            val array = Json.parseToJsonElement(data) as JsonArray
            array.mapNotNull { element ->
                val json = element.jsonObject
                val name = json["ubi"]?.jsonPrimitive?.contentOrNull?.let(::reCapitalize) ?: return@mapNotNull null
                val latitude = json["lat"]?.jsonPrimitive?.doubleOrNull ?: return@mapNotNull null
                val longitude = json["lon"]?.jsonPrimitive?.doubleOrNull ?: return@mapNotNull null
                val code = json["idema"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null

                Station(
                    name = name,
                    code = code,
                    region = 1,
                    providerType = WindProviderType.Aemet,
                    latitude = latitude,
                    longitude = longitude
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    internal fun applyObservationsData(station: Station, data: String) {
        try {
            val array = Json.parseToJsonElement(data) as JsonArray
            array.forEach { element ->
                val json = element.jsonObject
                val date = parseUtcDate(json["fint"]?.jsonPrimitive?.contentOrNull) ?: return@forEach

                putIfPresent(json, "dv") { value -> station.meteo.windDirection.put(date, value) }
                putIfPresent(json, "vv") { value -> station.meteo.windSpeedMed.put(date, value * 3.6) }
                putIfPresent(json, "vmax") { value -> station.meteo.windSpeedMax.put(date, value * 3.6) }

                putIfPresent(json, "ta") { value -> station.meteo.airTemperature.put(date, value) }
                putIfPresent(json, "hr") { value -> station.meteo.airHumidity.put(date, value) }
                putIfPresent(json, "pres") { value -> station.meteo.airPressure.put(date, value) }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun downloadAemetData(url: String): String? {
        val key = apiKeyProvider?.invoke()?.trim().orEmpty()
        if (key.isEmpty()) {
            return null
        }

        return try {
            val endpointDownloader = Downloader()
            endpointDownloader.url = url
            endpointDownloader.setHeader("api_key", key)
            downloader = endpointDownloader

            val endpointData = endpointDownloader.download() ?: return null
            val endpointJson = Json.parseToJsonElement(endpointData).jsonObject
            val dataUrl = endpointJson["datos"]?.jsonPrimitive?.contentOrNull ?: return null

            val dataDownloader = Downloader()
            dataDownloader.url = dataUrl
            downloader = dataDownloader
            dataDownloader.download("ISO-8859-1")
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            downloader = null
        }
    }

    private fun parseUtcDate(value: String?): Long? {
        if (value.isNullOrBlank()) return null
        return try {
            LocalDateTime.parse(value).toInstant(TimeZone.UTC).toEpochMilliseconds()
        } catch (_: Exception) {
            null
        }
    }

    private fun putIfPresent(json: JsonObject, key: String, put: (Double) -> Unit) {
        val value = json[key]?.jsonPrimitive?.doubleOrNull ?: return
        if (!value.isFinite()) return
        put(value)
    }

    private fun reCapitalize(str: String): String {
        val sb = StringBuilder(str.length)
        var prev = ' '
        str.forEach { ch ->
            if (!prev.isLetter()) {
                sb.append(ch.uppercaseChar())
            } else {
                sb.append(ch.lowercaseChar())
            }
            prev = ch
        }
        return sb.toString()
    }
}