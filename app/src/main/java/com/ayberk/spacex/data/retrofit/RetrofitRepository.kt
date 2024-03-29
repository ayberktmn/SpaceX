package com.ayberk.spacex.data.retrofit

import com.ayberk.spacex.common.Resource
import com.ayberk.spacex.data.models.crew.Crew
import com.ayberk.spacex.data.models.dragons.Dragons
import com.ayberk.spacex.data.models.rockets.Rockets
import javax.inject.Inject

class RetrofitRepository @Inject constructor(
    private val retrofitServiceInstance: RetrofitServiceInstance,
) {
    suspend fun getRockets(): Resource<com.ayberk.spacex.data.models.rockets.Rockets> {
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

    suspend fun getCrew(): Resource<com.ayberk.spacex.data.models.crew.Crew> {
        return try {
            val response = retrofitServiceInstance.getCrew()

            if (response.isSuccessful) {
                val crewList = response.body()
                if (crewList != null) {
                    Resource.Success(crewList)
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

    suspend fun getDragons(): Resource<com.ayberk.spacex.data.models.dragons.Dragons> {
        return try {
            val response = retrofitServiceInstance.getDragons()

            if (response.isSuccessful) {
                val dragonsList = response.body()
                if (dragonsList != null) {
                    Resource.Success(dragonsList)
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