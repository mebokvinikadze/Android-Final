package com.example.androidfinal.data.remote.retrofit

import com.example.androidfinal.data.remote.gemini.GeminiRequest
import com.example.androidfinal.data.remote.gemini.GeminiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitGeminiService {
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer YOUR_API_KEY"
    )
    @POST("models/gemini-1.5-flash:generateContent")
    suspend fun getGeminiResponse(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): Response<GeminiResponse>
}