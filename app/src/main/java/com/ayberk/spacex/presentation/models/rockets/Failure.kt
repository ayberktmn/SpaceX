package com.ayberk.spacex.presentation.models.rockets

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Failure(
    val altitude: Int?,
    val reason: String?,
    val time: Int?
) : Parcelable