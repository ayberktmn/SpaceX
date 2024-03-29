package com.ayberk.spacex.data.models.crew

data class CrewItem(
    val agency: String,
    val id: String,
    val image: String,
    val launches: List<String>,
    val name: String,
    val status: String,
    val wikipedia: String
)