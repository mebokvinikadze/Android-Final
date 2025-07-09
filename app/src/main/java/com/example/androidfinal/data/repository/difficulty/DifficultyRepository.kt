package com.example.androidfinal.data.repository.difficulty

import com.example.androidfinal.presentation.model.WorkoutDifficulty

interface DifficultyRepository {
    fun getDifficultyLevels(): List<WorkoutDifficulty>
}