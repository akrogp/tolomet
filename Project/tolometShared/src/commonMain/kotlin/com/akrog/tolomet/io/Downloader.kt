package com.akrog.tolomet.io

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.*
import io.ktor.http.*

class Downloader(
    private val timeout: Long = 15000,
    private val retries: Int = 2
) {
    private val client = HttpClient {
        install(HttpTimeout) {
            requestTimeoutMillis = timeout
        }
    }

    var url: String? = null
    var userAgent: String = "Tolomet"
    private val requestHeaders = mutableMapOf<String, String>()

    fun setHeader(name: String, value: String) {
        requestHeaders[name] = value
    }

    suspend fun download(charset: String = "UTF-8"): String? {
        val currentUrl = url ?: return null
        return try {
            val response: HttpResponse = client.get(currentUrl) {
                header("User-Agent", userAgent)
                requestHeaders.forEach { (name, value) ->
                    header(name, value)
                }
            }
            response.bodyAsText()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun downloadWithBody(body: String, charset: String = "UTF-8"): String? {
        val currentUrl = url ?: return null
        return try {
            val response: HttpResponse = client.post(currentUrl) {
                header("User-Agent", userAgent)
                requestHeaders.forEach { (name, value) ->
                    header(name, value)
                }
                contentType(ContentType.Application.FormUrlEncoded)
                setBody(body)
            }
            response.bodyAsText()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun cancel() {
        client.close()
    }
}
