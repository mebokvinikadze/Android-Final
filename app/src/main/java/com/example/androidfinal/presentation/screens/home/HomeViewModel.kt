package com.example.androidfinal.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidfinal.data.repository.home.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
) : ViewModel() {
    private val _workoutCount = MutableStateFlow<Int?>(null)
    val workoutCount: StateFlow<Int?> get() = _workoutCount

    init {
        getWorkoutCountFromDataStore()
    }

    private fun getWorkoutCountFromDataStore() {
        viewModelScope.launch {
            homeRepository.getUserWorkerCountFromDataStore().collect { count ->
                _workoutCount.value = count
            }
        }
    }


}