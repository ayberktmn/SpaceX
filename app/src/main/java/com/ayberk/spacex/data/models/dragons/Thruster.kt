package com.ayberk.spacex.data.models.dragons

data class Thruster(
    val amount: Int,
    val fuel_1: String,
    val fuel_2: String,
    val isp: Int,
    val pods: Int,
    val thrust: com.ayberk.spacex.data.models.dragons.Thrust,
    val type: String
)