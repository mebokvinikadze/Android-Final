package com.example.androidfinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidfinal.R
import com.example.androidfinal.databinding.WorkoutRepetitionBinding
import com.example.androidfinal.databinding.WorkoutTimedBinding
import com.example.androidfinal.presentation.model.Workout
import com.example.androidfinal.presentation.model.WorkoutType

class WorkoutAdapter(
    private val onWorkoutFinished: (Workout) -> Unit
) : ListAdapter<Workout, RecyclerView.ViewHolder>(WorkoutDiffUtil()) {

    companion object {
        private const val WORKOUT_TIMED = 1
        private const val WORKOUT_REPETITION = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).workoutType) {
            WorkoutType.TIMED -> WORKOUT_TIMED
            WorkoutType.REPETITION -> WORKOUT_REPETITION
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == WORKOUT_TIMED) {
            return TimedWorkoutViewHolder(
                WorkoutTimedBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else
            return RepetitionWorkoutViewHolder(
                WorkoutRepetitionBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val workout = getItem(position)
        if (holder is TimedWorkoutViewHolder) {
            holder.onBind(workout)
        } else if (holder is RepetitionWorkoutViewHolder) {
            holder.onBind(workout)
        }
    }


    inner class TimedWorkoutViewHolder(private val binding: WorkoutTimedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(timeWorkout: Workout) {

            binding.tvWorkoutName.text = timeWorkout.name
            binding.tvWorkoutDescription.text = timeWorkout.description
            binding.ivWorkoutImage.setImageResource(timeWorkout.workoutImage)
            binding.tvWorkoutTime.text = timeWorkout.getFormattedTime(binding.root.context)
            binding.tvWorkoutSets.text = timeWorkout.getFormattedSets(binding.root.context)
            binding.tvMuscleGroups.text = timeWorkout.getFormattedMuscleGroups()


            binding.constraint.setOnClickListener {
                onWorkoutFinished(timeWorkout)
            }

            if (timeWorkout.isFinished) {
                binding.constraint.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.light_green
                    )
                )
            } else {
                binding.constraint.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.white
                    )
                )

            }
        }
    }

    inner class RepetitionWorkoutViewHolder(private val binding: WorkoutRepetitionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(repetitionWorkout: Workout) {

            binding.tvWorkoutName.text = repetitionWorkout.name
            binding.tvWorkoutDescription.text = repetitionWorkout.description
            binding.ivWorkoutImage.setImageResource(repetitionWorkout.workoutImage)
            binding.tvWorkoutReps.text = repetitionWorkout.getFormattedReps(binding.root.context)
            binding.tvWorkoutSets.text = repetitionWorkout.getFormattedSets(binding.root.context)
            binding.tvMuscleGroups.text = repetitionWorkout.getFormattedMuscleGroups()

            binding.constraint.setOnClickListener {
                onWorkoutFinished(repetitionWorkout)
            }

            if (repetitionWorkout.isFinished) {
                binding.constraint.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.light_green
                    )
                )
            } else {
                binding.constraint.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.white
                    )
                )

            }
        }
    }
}


class WorkoutDiffUtil : DiffUtil.ItemCallback<Workout>() {
    override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean {
        return oldItem == newItem
    }
}