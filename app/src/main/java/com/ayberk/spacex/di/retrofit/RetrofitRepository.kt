package com.ayberk.spacex.di.retrofit

import com.ayberk.spacex.common.Resource
import com.ayberk.spacex.presentation.models.rockets.Rockets
import javax.inject.Inject

class RetrofitRepository @Inject constructor(
    private val retrofitServiceInstance: RetrofitServiceInstance,
) {
    suspend fun getRockets(): Resource<Rockets> {
        return try {
            val response = retrofitServiceInstance.getRockets()

            if (response.isSuccessful) {
                val rocketsList = response.body()
                if (rocketsList != null) {
                    Resource.Success(rocketsList)
                } else {
                    Resource.Fail("Empty response body")
                }
            } else {
                Resource.Fail("HTTP error: ${response.code()}")
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}