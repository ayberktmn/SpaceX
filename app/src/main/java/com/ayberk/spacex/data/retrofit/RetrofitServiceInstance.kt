package com.ayberk.spacex.data.retrofit

import com.ayberk.spacex.data.models.rockets.Rockets
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServiceInstance {
    @GET("v4/launches")
    suspend fun getRockets(): Response<com.ayberk.spacex.data.models.rockets.Rockets>

    @GET("v4/crew")
    suspend fun getCrew(): Response<com.ayberk.spacex.data.models.crew.Crew>

    @GET("v4/dragons")
    suspend fun getDragons(): Response<com.ayberk.spacex.data.models.dragons.Dragons>
    @GET("v4/launches")
    suspend fun getRocketLaunches(@Query("name") rocket: String): Response<Rockets>
}