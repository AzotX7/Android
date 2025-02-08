package com.azot.homework6.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Sneaker::class], version = 1)
abstract class SneakerDatabase : RoomDatabase() {
    abstract fun sneakerDao(): SneakerDao

    companion object {
        @Volatile
        private var INSTANCE: SneakerDatabase? = null

        fun getDatabase(context: Context): SneakerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SneakerDatabase::class.java,
                    "sneaker_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
