package com.ayberk.spacex.usecase.event

import com.ayberk.spacex.data.models.rockets.FavoriteRockets

sealed class RocketEvent {
    data class UpsertDeleteRocket(val rocket: FavoriteRockets) : RocketEvent()
}