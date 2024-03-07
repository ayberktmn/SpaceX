package com.ayberk.spacex.usecase

import com.ayberk.spacex.data.models.crew.CrewFavorite
import com.ayberk.spacex.data.models.rockets.FavoriteRockets
import com.ayberk.spacex.data.retrofit.RetrofitRepository

class UpsertCrew(
    private val retrofitRepository: RetrofitRepository
) {
    suspend operator fun invoke(crewFavorite: CrewFavorite) {
        retrofitRepository.upsertCrew(crewFavorite)
    }
}