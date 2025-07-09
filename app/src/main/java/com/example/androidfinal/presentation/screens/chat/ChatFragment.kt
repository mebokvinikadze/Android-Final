package com.example.androidfinal.presentation.screens.chat

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidfinal.R
import com.example.androidfinal.adapter.MessageAdapter
import com.example.androidfinal.common.Resource
import com.example.androidfinal.databinding.FragmentChatBinding
import com.example.androidfinal.presentation.base_fragment.BaseFragment
import com.example.androidfinal.util.showErrorSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ChatFragment : BaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate) {
    private val chatViewModel: ChatViewModel by viewModels()

    private val messageAdapter by lazy {
        MessageAdapter()
    }

    override fun setUp() {
        setUpMessagesRecyclerView()
    }

    override fun listeners() {
        askAI()
        navigateBackListener()
    }


    private fun messageState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                chatViewModel.geminiMessageState.collectLatest { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            loading()
                        }

                        is Resource.Success -> {
                            loaded()
                            val newMessages = resource.data
                            messageAdapter.submitList(newMessages) {
                                binding.RecyclerViewContainer.scrollToPosition(messageAdapter.itemCount - 1)
                            }

                        }

                        is Resource.Error -> {
                            loaded()
                            binding.root.showErrorSnackBar(resource.errorMessage)
                        }
                    }
                }
            }
        }
    }


    private fun askAI() {
        binding.ivSend.setOnClickListener {
            val inputText = binding.etInputMessage.text.toString().trim()

            if (inputText.isEmpty()) {
                binding.etInputMessage.error = getString(R.string.message_input_should_not_be_empty)
                return@setOnClickListener
            }


            binding.etInputMessage.text?.clear()

            chatViewModel.fetchGeminiMessage(inputText)
            messageState()

        }
    }


    private fun setUpMessagesRecyclerView() {
        binding.RecyclerViewContainer.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = messageAdapter
        }
    }

    private fun navigateBackListener() {
        binding.ivBackIcon.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun loading() {
        binding.loading.visibility = View.VISIBLE
    }

    private fun loaded() {
        binding.loading.visibility = View.GONE
    }
}