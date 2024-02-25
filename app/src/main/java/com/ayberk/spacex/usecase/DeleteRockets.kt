package com.ayberk.spacex.usecase

import com.ayberk.spacex.data.models.rockets.FavoriteRockets
import com.ayberk.spacex.data.retrofit.RetrofitRepository
import javax.inject.Inject

class DeleteRockets @Inject constructor(
    private val retrofitRepository: RetrofitRepository
) {
    suspend operator fun invoke(deleteRocket: FavoriteRockets) {
        retrofitRepository.deleteRocket(deleteRocket)
    }
}