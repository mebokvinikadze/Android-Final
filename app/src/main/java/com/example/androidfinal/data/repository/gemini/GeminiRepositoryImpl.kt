package com.example.androidfinal.data.repository.gemini

import com.example.androidfinal.BuildConfig
import com.example.androidfinal.common.ApiHelper
import com.example.androidfinal.common.Resource
import com.example.androidfinal.data.remote.gemini.Content
import com.example.androidfinal.data.remote.gemini.GeminiRequest
import com.example.androidfinal.data.remote.gemini.GeminiResponse
import com.example.androidfinal.data.remote.gemini.Part
import com.example.androidfinal.data.remote.retrofit.RetrofitGeminiService
import javax.inject.Inject

class GeminiRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val geminiService: RetrofitGeminiService
) : GeminiRepository {

    override suspend fun fetchGeminiResponse(prompt: String): Resource<GeminiResponse> {
        val request = GeminiRequest(listOf(Content(listOf(Part(prompt)))))
        return apiHelper.handleHttpRequest(apiCall = {
            geminiService.getGeminiResponse(BuildConfig.API_KEY, request)
        })
    }
}
