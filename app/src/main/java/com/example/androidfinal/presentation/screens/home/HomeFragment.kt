package com.example.androidfinal.presentation.screens.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.androidfinal.databinding.FragmentHomeBinding
import com.example.androidfinal.presentation.base_fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val homeViewModel: HomeViewModel by viewModels()

    override fun setUp() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.workoutCount.collectLatest { workout ->
                    binding.tvWorkoutCountContainer.text = workout.toString()
                }
            }
        }
    }


    override fun listeners() {
        navigateToProfileListener()
        navigateToChatListener()
        navigateToWorkoutListener()
    }


    private fun navigateToProfileListener() {
        binding.tvProfileContainer.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
        }
    }

    private fun navigateToChatListener() {
        binding.tvChatContainer.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToChatFragment())
        }
    }

    private fun navigateToWorkoutListener() {
        binding.tvWorkoutContainer.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDifficultyFragment())
        }
    }

}