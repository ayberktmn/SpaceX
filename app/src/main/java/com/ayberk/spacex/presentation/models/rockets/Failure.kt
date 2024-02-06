package com.ayberk.spacex.presentation.models.rockets

data class Failure(
    val altitude: Int,
    val reason: String,
    val time: Int
)