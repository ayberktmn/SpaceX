package com.ayberk.spacex.usecase

import com.ayberk.spacex.data.models.rockets.FavoriteRockets
import com.ayberk.spacex.data.retrofit.RetrofitRepository

class UpsertRocket(
    private val retrofitRepository: RetrofitRepository
) {
    suspend operator fun invoke(rocket: FavoriteRockets) {
        retrofitRepository.upsertRocket(rocket)
    }
}
