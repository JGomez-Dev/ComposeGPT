package com.jgomez.composegpt.model

import java.util.UUID
import java.util.Date

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val timestamp: Date = Date(),
    val isFromUser: Boolean,
    val isError: Boolean = false
) 