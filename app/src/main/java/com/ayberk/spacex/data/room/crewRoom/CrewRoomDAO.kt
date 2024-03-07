package com.ayberk.spacex.data.room.crewRoom

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ayberk.spacex.data.models.crew.CrewFavorite

@Dao
interface CrewRoomDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCrew(crew: CrewFavorite)

    @Delete
    fun deleteCrew(crew: CrewFavorite)

    @Query("DELETE FROM crew_items")
    fun clearCrew()

    @Query("SELECT * FROM crew_items")
    fun getAllCrew(): List<CrewFavorite>

    @Query("SELECT COUNT(*) FROM crew_items WHERE id = :id")
    fun checkIfDataExists(id: String): Int

}