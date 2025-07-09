package com.example.androidfinal.data.repository.difficulty

import android.content.res.Resources
import com.example.androidfinal.R
import com.example.androidfinal.presentation.model.DifficultyLevel
import com.example.androidfinal.presentation.model.WorkoutDifficulty
import javax.inject.Inject

class DifficultyRepositoryImpl @Inject constructor(
    private val resources: Resources
) : DifficultyRepository {
    override fun getDifficultyLevels(): List<WorkoutDifficulty> {
        return listOf(
            WorkoutDifficulty(
                difficulty = resources.getString(R.string.beginner_friendly),
                difficultyLevel = DifficultyLevel.EASY,
                difficultyColor = R.color.green,
                description = resources.getString(R.string.beginner_diff_desc),
                duration = resources.getString(R.string.beginner_duration),
                intensity = resources.getString(R.string.low_intensity),
                diffImage = R.drawable.easy_workout,
            ),
            WorkoutDifficulty(
                difficulty = resources.getString(R.string.intermediate_challenge),
                difficultyLevel = DifficultyLevel.MEDIUM,
                difficultyColor = R.color.orange,
                description = resources.getString(R.string.intermediate_diff_desc),
                duration = resources.getString(R.string.intermediate_duration),
                intensity = resources.getString(R.string.moderate_intensity),
                diffImage = R.drawable.intermediate_workout,
            ),
            WorkoutDifficulty(
                difficulty = resources.getString(R.string.advanced_training),
                difficultyLevel = DifficultyLevel.HARD,
                difficultyColor = R.color.red,
                description = resources.getString(R.string.advanced_difficulty_desc),
                duration = resources.getString(R.string.advanced_duration),
                intensity = resources.getString(R.string.high_intensity),
                diffImage = R.drawable.hard_workout,
            )
        )
    }
}
