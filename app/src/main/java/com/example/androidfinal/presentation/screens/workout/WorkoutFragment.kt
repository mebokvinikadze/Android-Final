package com.example.androidfinal.presentation.screens.workout

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidfinal.R
import com.example.androidfinal.adapter.WorkoutAdapter
import com.example.androidfinal.common.Resource
import com.example.androidfinal.databinding.FragmentWorkoutBinding
import com.example.androidfinal.presentation.base_fragment.BaseFragment
import com.example.androidfinal.util.showErrorSnackBar
import com.example.androidfinal.util.showSuccessSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WorkoutFragment : BaseFragment<FragmentWorkoutBinding>(FragmentWorkoutBinding::inflate) {

    private val workoutViewModel: WorkoutViewModel by viewModels()
    private val args: WorkoutFragmentArgs by navArgs()

    private val workoutAdapter by lazy {
        WorkoutAdapter { workout -> workoutViewModel.toggleWorkoutFinished(workout) }
    }

    override fun setUp() {
        setUpMessagesRecyclerView()

    }

    override fun listeners() {
        loadWorkouts()
        navigateBackListener()
        setUpFinishButton()
    }

    private fun setUpMessagesRecyclerView() {
        binding.recyclerViewWorkouts.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = workoutAdapter
        }
    }

    private fun loadWorkouts() {
        workoutViewModel.fetchWorkouts(args.difficulty)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                workoutViewModel.workouts.collect { workouts ->
                    workoutAdapter.submitList(workouts)
                }
            }
        }
    }

    private fun navigateBackListener() {
        binding.ivBackIcon.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun editStateManagement() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                workoutViewModel.workoutCountState.collectLatest { state ->
                    when (state) {
                        is Resource.Loading -> {
                            loading()
                        }

                        is Resource.Success -> {
                            loaded()
                            binding.root.showSuccessSnackBar(getString(R.string.awesome_job_keep_crushing_it))
                            findNavController().navigateUp()
                        }

                        is Resource.Error -> {
                            loaded()
                            binding.root.showErrorSnackBar(state.errorMessage)
                        }
                    }
                }
            }
        }
    }

    private fun loading() {
        binding.loading.visibility = View.VISIBLE
        binding.ivBackIcon.isEnabled = true
        binding.btnFinish.isEnabled = false

    }

    private fun loaded() {
        binding.loading.visibility = View.GONE
        binding.ivBackIcon.isEnabled = true
        binding.btnFinish.isEnabled = true
    }

    private fun setUpFinishButton() {
        binding.btnFinish.setOnClickListener {
            if (workoutViewModel.areAllWorkoutsFinished()) {
                workoutViewModel.updateUserWorkoutCount()
                editStateManagement()
            } else {
                binding.root.showErrorSnackBar(getString(R.string.you_must_finish_all_workouts))

            }
        }
    }


}