package com.example.androidfinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidfinal.databinding.DifficultyCardBinding
import com.example.androidfinal.presentation.model.DifficultyLevel
import com.example.androidfinal.presentation.model.WorkoutDifficulty

class DifficultyAdapter(private val onItemClick: (DifficultyLevel) -> Unit) :
    ListAdapter<WorkoutDifficulty, DifficultyAdapter.DifficultyViewHolder>(DifficultyDiffUtil()) {

    inner class DifficultyViewHolder(private val binding: DifficultyCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(workoutDifficulty: WorkoutDifficulty) {
            binding.ivDiffPic.setImageResource(workoutDifficulty.diffImage)
            binding.tvDiff.text = workoutDifficulty.difficulty
            val color =
                ContextCompat.getColor(binding.root.context, workoutDifficulty.difficultyColor)
            binding.tvDiff.setTextColor(color)
            binding.tvDiffDesc.text = workoutDifficulty.description
            binding.tvDiffDuration.text = workoutDifficulty.duration
            binding.tvDiffIntensity.text = workoutDifficulty.intensity

            binding.cardDiffContainer.setOnClickListener {
                onItemClick(workoutDifficulty.difficultyLevel)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DifficultyViewHolder {
        return DifficultyViewHolder(
            DifficultyCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DifficultyViewHolder, position: Int) {
        val workoutDifficulty = getItem(position)
        holder.onBind(workoutDifficulty)
    }
}


class DifficultyDiffUtil : DiffUtil.ItemCallback<WorkoutDifficulty>() {
    override fun areItemsTheSame(oldItem: WorkoutDifficulty, newItem: WorkoutDifficulty): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: WorkoutDifficulty,
        newItem: WorkoutDifficulty
    ): Boolean {
        return oldItem == newItem

    }
}