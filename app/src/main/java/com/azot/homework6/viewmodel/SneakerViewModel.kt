package com.azot.homework6.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.azot.homework6.data.Sneaker
import com.azot.homework6.data.SneakerDatabase
import com.azot.homework6.data.SneakerRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SneakerViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SneakerRepository
    val allSneakers: StateFlow<List<Sneaker>>

    init {
        val sneakerDao = SneakerDatabase.getDatabase(application).sneakerDao()
        repository = SneakerRepository(sneakerDao)
        allSneakers = repository.allSneakers.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    fun insert(sneaker: Sneaker) = viewModelScope.launch {
        repository.insert(sneaker)
    }

    fun delete(sneaker: Sneaker) = viewModelScope.launch {
        repository.delete(sneaker)
    }
}
