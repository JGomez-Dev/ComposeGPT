package com.jgomez.composegpt.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OpenAIRepository {
    private val service = RetrofitClient.openAIService
    
    suspend fun getChatCompletion(prompt: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val messages = listOf(Message(role = "user", content = prompt))
            val request = ChatRequest(
                model = OpenAIConfig.MODEL,
                messages = messages
            )
            
            val response = service.getChatCompletion(chatRequest = request)
            
            if (response.isSuccessful) {
                val chatResponse = response.body()
                if (chatResponse != null && chatResponse.choices.isNotEmpty()) {
                    Result.success(chatResponse.choices[0].message.content.trim())
                } else {
                    Result.failure(Exception("No se recibió respuesta de la IA"))
                }
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
} 