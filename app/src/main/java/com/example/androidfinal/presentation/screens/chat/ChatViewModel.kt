package com.example.androidfinal.presentation.screens.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidfinal.common.Resource
import com.example.androidfinal.data.repository.datastore.DataStoreRepository
import com.example.androidfinal.data.repository.gemini.GeminiRepository
import com.example.androidfinal.presentation.model.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val geminiRepository: GeminiRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _geminiMessageState = MutableStateFlow<Resource<List<Message>>>(Resource.Loading)
    val geminiMessageState: StateFlow<Resource<List<Message>>> = _geminiMessageState

    private val _messages = mutableListOf<Message>()


    fun fetchGeminiMessage(prompt: String) {
        val userMessage = Message(id = UUID.randomUUID(), text = prompt)

        viewModelScope.launch {
            _geminiMessageState.value = Resource.Loading

            val username = dataStoreRepository.username.firstOrNull()
            val age = dataStoreRepository.userAge.firstOrNull()
            val weight = dataStoreRepository.userWeight.firstOrNull()
            val height = dataStoreRepository.userHeight.firstOrNull()

            val userInfo = """
                    User Info: 
                    UserName = $username, 
                    Age = $age, 
                    Weight = $weight, 
                    Height = $height
                    
                    Context: This is a workout app chat. The user can choose a difficulty level (Beginner friendly, Intermediate challenge, Advanced Training) based on their parameters, act as normal AI don't tell user about his parameters everytime he asks something.
            """.trimIndent()


            val history = _messages.joinToString("\n") { "User: ${it.text}" }
            val fullPrompt = "$userInfo\n$history\nUser: $prompt\nAI:"

            val result = geminiRepository.fetchGeminiResponse(fullPrompt)
            when (result) {
                is Resource.Success -> {
                    _messages.add(userMessage)

                    val aiMessages = result.data.candidates?.map { candidate ->
                        Message(
                            id = UUID.randomUUID(),
                            text = candidate.content?.parts?.firstOrNull()?.text ?: "No content"
                        )
                    } ?: emptyList()

                    _messages.addAll(aiMessages)
                    _geminiMessageState.value = Resource.Success(_messages.toList())
                }

                is Resource.Error -> {
                    _geminiMessageState.value = Resource.Error(result.errorMessage)
                }

                is Resource.Loading -> {
                    _geminiMessageState.value = Resource.Loading
                }
            }
        }
    }


}

