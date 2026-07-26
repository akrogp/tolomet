package com.akrog.tolomet

import com.akrog.tolomet.providers.WindProviderType
import kotlinx.datetime.Instant

class Station(
    var name: String = "none",
    var code: String = "none",
    var country: String = "none",
    var region: Int = 1,
    var favorite: Boolean = false,
    var providerType: WindProviderType = WindProviderType.Aemet,
    var latitude: Double = 0.0,
    var longitude: Double = 0.0
) {
    val meteo = Meteo()
    var updated: Long? = null
    var special: Int = -1
    var distance: Float = -1.0f
    var extra: Any? = null

    fun getId(): String {
        return "${providerType.code}-$code"
    }

    fun clear() {
        meteo.clear()
    }

    fun isEmpty(): Boolean {
        return meteo.isEmpty()
    }

    override fun toString(): String {
        if (special != -1) return name
        var str = "$name (${providerType.code})"
        if (distance > 0.0f) {
            str = "$str @ ${distance / 1000.0f} km"
        }
        return str
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Station) return false
        return getId() == other.getId()
    }

    override fun hashCode(): Int {
        return getId().hashCode()
    }

    fun clone(): Station {
        val station = Station(
            name, code, country, region, favorite, providerType, latitude, longitude
        )
        station.updated = updated
        station.special = special
        station.distance = distance
        station.meteo.merge(meteo)
        return station
    }
}
