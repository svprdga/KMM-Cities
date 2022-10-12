package com.example.kmmcities

import apiKey
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class GetCitiesResponse(val data: List<City>)

@Serializable
data class City(val id: Int, val city: String, val country: String)

class ApiClient {
    private val client = HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getCities(): List<City> {
        val response =
            client.get("https://wft-geo-db.p.rapidapi.com/v1/geo/cities") {
                headers {
                    append("X-RapidAPI-Key", apiKey)
                    append("X-RapidAPI-Host", "wft-geo-db.p.rapidapi.com")
                }
                url {
                    parameters.append("limit", "10")
                }
            }.body<GetCitiesResponse>()

        return response.data
    }
}