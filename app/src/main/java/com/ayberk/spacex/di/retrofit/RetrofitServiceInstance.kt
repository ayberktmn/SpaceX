package com.ayberk.spacex.di.retrofit

import com.ayberk.spacex.presentation.models.rockets.Rockets
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitServiceInstance {
    @GET("v4/launches")
    suspend fun getRockets() : Response<Rockets>
}