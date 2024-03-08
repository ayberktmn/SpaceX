package com.ayberk.spacex.data.models.crew

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crew_items")
data class CrewFavorite(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "agency")
    val agency: String
)