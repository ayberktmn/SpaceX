package com.ayberk.spacex.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ayberk.spacex.data.models.rockets.Converters
import com.ayberk.spacex.data.models.rockets.FavoriteRockets
import com.ayberk.spacex.data.models.rockets.RocketsItem

@Database(entities = [FavoriteRockets::class], version = 1)
@TypeConverters(Converters::class) // TypeConverter'ı burada belirtin
abstract class SpaceRoomDB : RoomDatabase() {
    abstract fun spaceRoomDAOInterface(): SpaceRoomDAO
}