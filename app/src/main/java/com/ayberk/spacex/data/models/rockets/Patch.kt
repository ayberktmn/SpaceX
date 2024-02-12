package com.ayberk.spacex.data.models.rockets

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Patch(
    val large: String?,
    val small: String?
) : Parcelable