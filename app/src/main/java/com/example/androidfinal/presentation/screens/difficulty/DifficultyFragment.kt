package com.example.androidfinal.presentation.screens.difficulty

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidfinal.adapter.DifficultyAdapter
import com.example.androidfinal.databinding.FragmentDifficultyBinding
import com.example.androidfinal.presentation.base_fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DifficultyFragment :
    BaseFragment<FragmentDifficultyBinding>(FragmentDifficultyBinding::inflate) {

    private val difficultyViewModel: DifficultyViewModel by viewModels()

    private val difficultyAdapter by lazy {
        DifficultyAdapter { difficulty ->
            val action = DifficultyFragmentDirections
                .actionDifficultyFragmentToWorkoutFragment(difficulty)
            findNavController().navigate(action)
        }
    }

    override fun setUp() {
        difficultyViewModel.loadDifficultyLevels()
        setUpMessagesRecyclerView()
        difficultyLevels()
    }

    override fun listeners() {
        navigateBackListener()
    }

    private fun setUpMessagesRecyclerView() {
        binding.recyclerViewDifficulties.itemAnimator = null
        binding.recyclerViewDifficulties.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = difficultyAdapter
        }
    }

    private fun difficultyLevels() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                difficultyViewModel.difficultyLevels.collect { difficulties ->
                    difficultyAdapter.submitList(difficulties)
                }
            }
        }
    }

    private fun navigateBackListener() {
        binding.ivBackIcon.setOnClickListener {
            findNavController().navigateUp()
        }
    }


}