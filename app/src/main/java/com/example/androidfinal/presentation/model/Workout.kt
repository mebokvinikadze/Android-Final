package com.example.androidfinal.presentation.model

import android.content.Context
import com.example.androidfinal.R
import java.util.UUID

data class Workout(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val description: String,
    val workoutType: WorkoutType,
    val value: Int,
    val sets: Int,
    var isFinished: Boolean = false,
    val muscleGroups: List<MuscleGroup>,
    val workoutImage: Int
) {
    fun getFormattedTime(context: Context): String {
        val minutes = value / 60
        val seconds = value % 60
        return context.getString(R.string.time_per_set, minutes, seconds)
    }

    fun getFormattedReps(context: Context): String {
        return context.getString(R.string.reps_per_set, value)
    }

    fun getFormattedSets(context: Context): String {
        return context.resources.getQuantityString(R.plurals.sets, sets, sets)
    }

    fun getFormattedMuscleGroups(): String {
        return muscleGroups.joinToString(", ") {
            it.name.replace("_", " ").lowercase().replaceFirstChar { ch -> ch.uppercaseChar() }
        }
    }
}

enum class WorkoutType {
    TIMED,
    REPETITION
}

enum class MuscleGroup {
    CHEST,
    BACK,
    SHOULDERS,
    BICEPS,
    TRICEPS,
    LEGS,
    CORE,
    FULL_BODY,
    CARDIO
}