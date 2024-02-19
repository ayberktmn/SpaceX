package com.ayberk.spacex.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ayberk.spacex.data.models.rockets.FavoriteRockets

@Dao
interface SpaceRoomDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRocket(rocket: FavoriteRockets)

    @Delete
    fun deleteRocket(rocket: FavoriteRockets)

    @Query("DELETE FROM rocket_items")
    fun clearRockets()

    @Query("SELECT * FROM rocket_items")
    fun getAllRockets(): List<FavoriteRockets>

    @Query("SELECT COUNT(*) FROM rocket_items WHERE id = :id")
    fun checkIfDataExists(id: String): Int

}
