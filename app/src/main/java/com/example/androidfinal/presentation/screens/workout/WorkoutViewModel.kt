package com.example.androidfinal.presentation.screens.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidfinal.common.Resource
import com.example.androidfinal.data.repository.workout.WorkoutRepository
import com.example.androidfinal.presentation.model.DifficultyLevel
import com.example.androidfinal.presentation.model.Workout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(private val workoutRepository: WorkoutRepository) :
    ViewModel() {

    private val _workouts = MutableStateFlow<List<Workout>>(emptyList())
    val workouts: StateFlow<List<Workout>> get() = _workouts

    private val _workoutCountState = MutableStateFlow<Resource<Unit>>(Resource.Loading)
    val workoutCountState: StateFlow<Resource<Unit>> = _workoutCountState


    fun fetchWorkouts(difficulty: DifficultyLevel) {
        val fetchedWorkouts = when (difficulty) {
            DifficultyLevel.EASY -> workoutRepository.getEasyWorkouts()
            DifficultyLevel.MEDIUM -> workoutRepository.getMediumWorkouts()
            DifficultyLevel.HARD -> workoutRepository.getHardWorkouts()
        }
        _workouts.value = fetchedWorkouts
    }

    fun toggleWorkoutFinished(workout: Workout) {
        _workouts.value = _workouts.value.map {
            if (it == workout) it.copy(isFinished = !it.isFinished) else it
        }
    }

    fun updateUserWorkoutCount() {
        viewModelScope.launch {
            _workoutCountState.value = Resource.Loading

            val result = workoutRepository.updateUserWorkoutCount()

            _workoutCountState.value = result
        }
    }

    fun areAllWorkoutsFinished(): Boolean {
        return _workouts.value.all { it.isFinished }
    }


}