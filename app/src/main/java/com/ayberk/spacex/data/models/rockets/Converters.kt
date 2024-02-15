package com.ayberk.spacex.data.models.rockets

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromString(value: String?): Int? {
        return value?.toInt()
    }

    @TypeConverter
    fun toString(value: Int?): String? {
        return value?.toString()
    }
}