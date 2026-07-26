package com.akrog.tolomet.providers

import com.akrog.tolomet.Station

interface WindProvider {
    suspend fun refresh(station: Station)
    suspend fun travel(station: Station, date: Long): Boolean
    fun cancel()
    fun getRefresh(code: String): Int
    fun getInfoUrl(code: String): String
    fun getUserUrl(code: String): String
    suspend fun downloadStations(): List<Station>
}
