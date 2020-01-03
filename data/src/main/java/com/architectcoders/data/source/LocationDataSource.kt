package com.architectcoders.data.source

interface LocationDataSource {

    suspend fun findLat(): String?
    suspend fun findLon(): String?
}