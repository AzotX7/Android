package com.azot.homework6.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sneakers")
data class Sneaker(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val brand: String,
    val description: String,
    val year: Int,
    val rating: Float
)
