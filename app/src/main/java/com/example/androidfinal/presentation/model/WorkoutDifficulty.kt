package com.example.androidfinal.presentation.model

import java.util.UUID

data class WorkoutDifficulty(
    val id: UUID = UUID.randomUUID(),
    val difficultyLevel: DifficultyLevel,
    val difficulty: String,
    val difficultyColor: Int,
    val description: String,
    val duration: String,
    val intensity: String,
    val diffImage: Int
)

enum class DifficultyLevel {
    EASY,
    MEDIUM,
    HARD
}
