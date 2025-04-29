package com.jgomez.composegpt.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OpenAIService {
    @POST("chat/completions")
    suspend fun getChatCompletion(
        @Header("Authorization") apiKey: String = "Bearer ${OpenAIConfig.API_KEY}",
        @Body chatRequest: ChatRequest
    ): Response<ChatResponse>
} 