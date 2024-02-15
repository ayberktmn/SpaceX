package com.ayberk.spacex.presentation.ui

import com.ayberk.spacex.data.models.rockets.FavoriteRockets

sealed class RocketEvent {
    data class UpsertDeleteArticle(val rocket: FavoriteRockets) : RocketEvent()
}