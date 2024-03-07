package com.ayberk.spacex.data.room.crewRoom

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ayberk.spacex.data.models.crew.CrewFavorite

@Database(entities = [CrewFavorite::class], version = 1)
abstract class CrewRoomDB : RoomDatabase() {

    abstract fun crewRoomDAOInterface(): CrewRoomDAO
}