package com.ayberk.spacex.data.retrofit

import com.ayberk.spacex.common.Resource
import com.ayberk.spacex.data.models.rockets.FavoriteRockets
import com.ayberk.spacex.data.models.rockets.Rockets
import com.ayberk.spacex.data.room.SpaceRoomDAO
import com.ayberk.spacex.data.room.SpaceRoomDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RetrofitRepository @Inject constructor(
    private val retrofitServiceInstance: RetrofitServiceInstance,
    private val spaceRoomDB: SpaceRoomDB,
    private val spaceRoomDao: SpaceRoomDAO
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


    suspend fun upsertRocket(favoriteRockets: FavoriteRockets) {
        withContext(Dispatchers.IO) {
            spaceRoomDB.spaceRoomDAOInterface().addRocket(favoriteRockets)
        }
    }

    suspend fun clearRoom() {
        withContext(Dispatchers.IO) {
            spaceRoomDB.spaceRoomDAOInterface().clearRockets()
        }
    }

    suspend fun deleteRocket(deleteRocket: FavoriteRockets) {
        withContext(Dispatchers.IO) {
            spaceRoomDB.spaceRoomDAOInterface().deleteRocket(deleteRocket)
        }
    }

 /*   fun rocketFavori(): Resource<List<FavoriteRockets>> {
        return try {
            val response = spaceRoomDao.getAllRockets()

            if (response.isNullOrEmpty()) {
                Resource.Fail("Favorilerinizde Rocket bulunmamaktadır.")
            } else {
                Resource.Success(response)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    } */

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