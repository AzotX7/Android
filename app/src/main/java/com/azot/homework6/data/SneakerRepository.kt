package com.azot.homework6.data

import kotlinx.coroutines.flow.Flow

class SneakerRepository(private val sneakerDao: SneakerDao) {
    val allSneakers: Flow<List<Sneaker>> = sneakerDao.getAllSneakers()

    suspend fun insert(sneaker: Sneaker) {
        sneakerDao.insertSneaker(sneaker)
    }

    suspend fun delete(sneaker: Sneaker) {
        sneakerDao.deleteSneaker(sneaker)
    }
}
