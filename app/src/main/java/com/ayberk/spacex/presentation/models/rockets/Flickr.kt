package com.ayberk.spacex.presentation.models.rockets

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Flickr(
    val original: List<String>?,
) : Parcelable