package com.example.androidfinal.data.repository.gemini

import com.example.androidfinal.common.Resource
import com.example.androidfinal.data.remote.gemini.GeminiResponse

interface GeminiRepository {
    suspend fun fetchGeminiResponse(prompt: String): Resource<GeminiResponse>
}