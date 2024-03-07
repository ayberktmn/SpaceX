package com.ayberk.spacex.usecase.event

import com.ayberk.spacex.data.models.crew.CrewFavorite

sealed class CrewEvent {
    data class UpsertDeleteCrew(val crew: CrewFavorite) : CrewEvent()
}