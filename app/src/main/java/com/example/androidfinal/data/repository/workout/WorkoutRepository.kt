package com.example.androidfinal.data.repository.workout

import com.example.androidfinal.common.Resource
import com.example.androidfinal.presentation.model.Workout

interface WorkoutRepository {
    fun getEasyWorkouts(): List<Workout>
    fun getMediumWorkouts(): List<Workout>
    fun getHardWorkouts(): List<Workout>
    suspend fun updateUserWorkoutCount(): Resource<Unit>
}