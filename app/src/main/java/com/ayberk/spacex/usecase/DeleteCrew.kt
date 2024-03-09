package com.ayberk.spacex.usecase

import com.ayberk.spacex.data.models.crew.CrewFavorite
import com.ayberk.spacex.data.models.rockets.FavoriteRockets
import com.ayberk.spacex.data.retrofit.RetrofitRepository
import javax.inject.Inject

class DeleteCrew  @Inject constructor(
    private val retrofitRepository: RetrofitRepository
) {
    suspend operator fun invoke(deleteCrew: CrewFavorite) {
        retrofitRepository.deleteCrew(deleteCrew)
    }
}