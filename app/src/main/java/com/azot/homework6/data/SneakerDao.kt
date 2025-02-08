package com.azot.homework6.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SneakerDao {
    @Query("SELECT * FROM sneakers")
    fun getAllSneakers(): Flow<List<Sneaker>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSneaker(sneaker: Sneaker): Long

    @Delete
    suspend fun deleteSneaker(sneaker: Sneaker): Int
}
