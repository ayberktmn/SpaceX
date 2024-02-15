package com.ayberk.spacex.data.models.rockets

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class RocketsItem(
    val id: String,
    val auto_update: Boolean?,
    val cores: List<com.ayberk.spacex.data.models.rockets.Core>?,
    val crew: List<String>?,
    val date_local: String?,
    val date_precision: String?,
    val date_unix: Int?,
    val date_utc: String?,
    val details: String?,
    val failures: List<com.ayberk.spacex.data.models.rockets.Failure>?,
    val fairings: com.ayberk.spacex.data.models.rockets.Fairings?,
    val flight_number: Int?,
    val launch_library_id: String?,
    val launchpad: String?,
    val links: com.ayberk.spacex.data.models.rockets.Links?,
    val name: String?,
    val net: Boolean?,
    val payloads: List<String>?,
    val rocket: String?,
    val ships: List<String>?,
    val static_fire_date_unix: Int?,
    val static_fire_date_utc: String?,
    val success: Boolean?,
    val tbd: Boolean?,
    val upcoming: Boolean?,
    val window: Int?
) : Parcelable