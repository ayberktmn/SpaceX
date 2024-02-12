package com.ayberk.spacex.data.models.rockets

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Links(
    val article: String?,
    val flickr: com.ayberk.spacex.data.models.rockets.Flickr?,
    val patch: com.ayberk.spacex.data.models.rockets.Patch?,
    val presskit: String?,
    val reddit: com.ayberk.spacex.data.models.rockets.Reddit?,
    val webcast: String?,
    val wikipedia: String?,
    val youtube_id: String?
) : Parcelable