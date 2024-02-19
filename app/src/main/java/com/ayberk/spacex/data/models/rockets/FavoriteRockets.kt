package com.ayberk.spacex.data.models.rockets

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rocket_items")
data class FavoriteRockets(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "image")
    val image: String?,

    @ColumnInfo(name = "name")
    val name: String?,
)