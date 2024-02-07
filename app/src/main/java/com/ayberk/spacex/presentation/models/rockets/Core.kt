package com.ayberk.spacex.presentation.models.rockets

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Core(
    val core: String?,
    val flight: Int?,
    val gridfins: Boolean?,
    val landing_attempt: Boolean?,
    val landing_success: Boolean?,
    val landing_type: String?,
    val landpad: String?,
    val legs: Boolean?,
    val reused: Boolean?
) : Parcelable