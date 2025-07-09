package com.example.androidfinal.data.repository.workout

import android.content.res.Resources
import com.example.androidfinal.R
import com.example.androidfinal.common.FirebaseHelper
import com.example.androidfinal.common.Resource
import com.example.androidfinal.data.repository.datastore.DataStoreRepository
import com.example.androidfinal.presentation.model.MuscleGroup
import com.example.androidfinal.presentation.model.Workout
import com.example.androidfinal.presentation.model.WorkoutType
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    private val resources: Resources,
    private val db: FirebaseFirestore,
    private val firebaseHelper: FirebaseHelper,
    private val dataStoreRepository: DataStoreRepository
) : WorkoutRepository {
    override fun getEasyWorkouts(): List<Workout> {
        return listOf(
            Workout(
                name = resources.getString(R.string.plank),
                description = resources.getString(R.string.plank_desc),
                workoutType = WorkoutType.TIMED,
                value = 45,
                sets = 3,
                muscleGroups = listOf(MuscleGroup.CORE, MuscleGroup.SHOULDERS),
                workoutImage = R.drawable.plank
            ),
            Workout(
                name = resources.getString(R.string.push_ups_on_knees),
                description = resources.getString(R.string.push_up_on_knees_desc),
                workoutType = WorkoutType.REPETITION,
                value = 10,
                sets = 3,
                muscleGroups = listOf(
                    MuscleGroup.CHEST,
                    MuscleGroup.TRICEPS,
                    MuscleGroup.SHOULDERS
                ),
                workoutImage = R.drawable.push_ups_on_knees
            ),
            Workout(
                name = resources.getString(R.string.mountain_climber),
                description = resources.getString(R.string.mountain_climber_desc),
                workoutType = WorkoutType.TIMED,
                value = 60,
                sets = 2,
                muscleGroups = listOf(MuscleGroup.CARDIO, MuscleGroup.LEGS, MuscleGroup.CORE),
                workoutImage = R.drawable.mountain_climber
            ),
            Workout(
                name = resources.getString(R.string.wall_sit),
                description = resources.getString(R.string.wall_sit_desc),
                workoutType = WorkoutType.TIMED,
                value = 30,
                sets = 3,
                muscleGroups = listOf(MuscleGroup.LEGS, MuscleGroup.CORE),
                workoutImage = R.drawable.wall_sit
            ),
            Workout(
                name = resources.getString(R.string.glute_bridges),
                description = resources.getString(R.string.glute_bridges_desc),
                workoutType = WorkoutType.REPETITION,
                value = 15,
                sets = 3,
                muscleGroups = listOf(MuscleGroup.LEGS, MuscleGroup.CORE),
                workoutImage = R.drawable.glute_bridges
            ),
            Workout(
                name = resources.getString(R.string.plank),
                description = resources.getString(R.string.plank_desc),
                workoutType = WorkoutType.TIMED,
                value = 45,
                sets = 3,
                muscleGroups = listOf(MuscleGroup.CORE, MuscleGroup.SHOULDERS),
                workoutImage = R.drawable.plank
            ),

            )
    }

    override fun getMediumWorkouts(): List<Workout> {
        return listOf(
            Workout(
                name = resources.getString(R.string.push_ups),
                description = resources.getString(R.string.push_ups_desc),
                workoutType = WorkoutType.REPETITION,
                value = 10,
                sets = 3,
                muscleGroups = listOf(
                    MuscleGroup.CHEST,
                    MuscleGroup.TRICEPS,
                    MuscleGroup.SHOULDERS
                ),
                workoutImage = R.drawable.push_ups
            ),
            Workout(
                name = resources.getString(R.string.bicycle_crunches),
                description = resources.getString(R.string.bicycle_crunches_desc),
                workoutType = WorkoutType.REPETITION,
                value = 20,
                sets = 3,
                muscleGroups = listOf(MuscleGroup.CORE),
                workoutImage = R.drawable.bicycle_crunches
            ),
            Workout(
                name = resources.getString(R.string.mountain_climbers),
                description = resources.getString(R.string.mountain_climbers_desc),
                workoutType = WorkoutType.TIMED,
                value = 90,
                sets = 3,
                muscleGroups = listOf(MuscleGroup.CORE, MuscleGroup.CARDIO, MuscleGroup.SHOULDERS),
                workoutImage = R.drawable.mountain_climber
            ),
            Workout(
                name = resources.getString(R.string.push_ups),
                description = resources.getString(R.string.push_ups_desc),
                workoutType = WorkoutType.REPETITION,
                value = 10,
                sets = 3,
                muscleGroups = listOf(
                    MuscleGroup.CHEST,
                    MuscleGroup.TRICEPS,
                    MuscleGroup.SHOULDERS
                ),
                workoutImage = R.drawable.push_ups
            ),
            Workout(
                name = resources.getString(R.string.lunges),
                description = resources.getString(R.string.lunges_desc),
                workoutType = WorkoutType.REPETITION,
                value = 24,
                sets = 3,
                muscleGroups = listOf(MuscleGroup.LEGS),
                workoutImage = R.drawable.lunges
            ),
            Workout(
                name = resources.getString(R.string.high_knees),
                description = resources.getString(R.string.high_knees_desc),
                workoutType = WorkoutType.TIMED,
                value = 30,
                sets = 4,
                muscleGroups = listOf(MuscleGroup.CARDIO, MuscleGroup.LEGS, MuscleGroup.CORE),
                workoutImage = R.drawable.high_knees
            ),
            Workout(
                name = resources.getString(R.string.push_ups),
                description = resources.getString(R.string.push_ups_desc),
                workoutType = WorkoutType.REPETITION,
                value = 10,
                sets = 3,
                muscleGroups = listOf(
                    MuscleGroup.CHEST,
                    MuscleGroup.TRICEPS,
                    MuscleGroup.SHOULDERS
                ),
                workoutImage = R.drawable.push_ups
            )

        )
    }

    override fun getHardWorkouts(): List<Workout> {
        return listOf(
            Workout(
                name = resources.getString(R.string.burpees),
                description = resources.getString(R.string.burpees_desc),
                workoutType = WorkoutType.REPETITION,
                value = 15,
                sets = 4,
                muscleGroups = listOf(MuscleGroup.FULL_BODY, MuscleGroup.CARDIO),
                workoutImage = R.drawable.burpee
            ),
            Workout(
                name = resources.getString(R.string.diamond_push_ups),
                description = resources.getString(R.string.diamond_push_ups_desc),
                workoutType = WorkoutType.REPETITION,
                value = 12,
                sets = 3,
                muscleGroups = listOf(MuscleGroup.TRICEPS, MuscleGroup.CHEST),
                workoutImage = R.drawable.diamond_push_up
            ),
            Workout(
                name = resources.getString(R.string.plank_to_push_up),
                description = resources.getString(R.string.plank_to_push_up_desc),
                workoutType = WorkoutType.TIMED,
                value = 45,
                sets = 3,
                muscleGroups = listOf(MuscleGroup.CORE, MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS),
                workoutImage = R.drawable.plank_push_up
            ),
            Workout(
                name = resources.getString(R.string.jump_squats),
                description = resources.getString(R.string.jump_squats_desc),
                workoutType = WorkoutType.REPETITION,
                value = 20,
                sets = 4,
                muscleGroups = listOf(MuscleGroup.LEGS, MuscleGroup.CARDIO),
                workoutImage = R.drawable.jump_squat
            ),
            Workout(
                name = resources.getString(R.string.mountain_climbers),
                description = resources.getString(R.string.mountain_climbers_desc),
                workoutType = WorkoutType.TIMED,
                value = 60,
                sets = 3,
                muscleGroups = listOf(MuscleGroup.CORE, MuscleGroup.CARDIO, MuscleGroup.SHOULDERS),
                workoutImage = R.drawable.mountain_climber
            ),
            Workout(
                name = resources.getString(R.string.pull_ups),
                description = resources.getString(R.string.pull_ups_desc),
                workoutType = WorkoutType.REPETITION,
                value = 10,
                sets = 3,
                muscleGroups = listOf(MuscleGroup.BACK, MuscleGroup.BICEPS, MuscleGroup.BICEPS),
                workoutImage = R.drawable.pull_up
            ),
            Workout(
                name = resources.getString(R.string.pistol_squats),
                description = resources.getString(R.string.pistol_squats_desc),
                workoutType = WorkoutType.REPETITION,
                value = 8,
                sets = 3,
                muscleGroups = listOf(MuscleGroup.LEGS, MuscleGroup.CORE),
                workoutImage = R.drawable.pistol_squat
            ),
            Workout(
                name = resources.getString(R.string.plank_to_push_up),
                description = resources.getString(R.string.plank_to_push_up_desc),
                workoutType = WorkoutType.TIMED,
                value = 60,
                sets = 2,
                muscleGroups = listOf(MuscleGroup.CORE, MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS),
                workoutImage = R.drawable.plank_push_up
            ),
        )
    }


    override suspend fun updateUserWorkoutCount(): Resource<Unit> {
        return firebaseHelper.handleFirebaseRequest {
            val uid = dataStoreRepository.userId.firstOrNull()

            if (!uid.isNullOrEmpty()){
                val userDoc = db.collection("users").document(uid).get().await()

                val workoutCount = userDoc.getLong("workout_count")?.toInt() ?: 0
                val userData = mapOf("workout_count" to workoutCount + 1)
                db.collection("users").document(uid).update(userData).await()

                dataStoreRepository.updateWorkoutCount(workoutCount + 1)
            }
        }
    }
}