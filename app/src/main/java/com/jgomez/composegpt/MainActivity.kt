package com.jgomez.composegpt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgomez.composegpt.api.OpenAIRepository
import com.jgomez.composegpt.model.ChatMessage
import com.jgomez.composegpt.ui.components.ChatBubble
import com.jgomez.composegpt.ui.components.ChatInput
import com.jgomez.composegpt.ui.components.TypingIndicator
import com.jgomez.composegpt.ui.theme.ComposeGPTTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeGPTTheme {
                ChatScreen()
            }
        }
    }
}

suspend fun fetchLLMResponse(prompt: String): String? {
    val repository = OpenAIRepository()
    return repository.getChatCompletion(prompt).fold(
        onSuccess = { it },
        onFailure = { 
            println("Error al obtener respuesta: ${it.message}")
            "Error: ${it.message ?: "Desconocido"}"
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val messages = remember { mutableStateListOf<ChatMessage>() }
    var userInput by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    LaunchedEffect(key1 = true) {
        if (messages.isEmpty()) {
            messages.add(
                ChatMessage(
                    text = "¡Hola! Soy tu asistente IA. ¿En qué puedo ayudarte hoy?",
                    isFromUser = false
                )
            )
        }
    }

    LaunchedEffect(messages.size, isLoading) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .statusBarsPadding(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "ComposeGPT",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = { /* Implementar menú */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Menú"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .weight(1f)
                        .fillMaxWidth(),
                    state = listState,
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
                ) {
                    items(messages, key = { it.id }) { message ->
                        ChatBubble(message = message)
                    }
                    
                    if (isLoading) {
                        item {
                            TypingIndicator()
                        }
                    }
                }
                
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .imePadding(),
                    color = MaterialTheme.colorScheme.surface,
                    shadowElevation = 8.dp
                ) {
                    ChatInput(
                        value = userInput,
                        onValueChange = { userInput = it },
                        onSendClick = {
                            val userMessage = ChatMessage(
                                text = userInput,
                                isFromUser = true
                            )
                            messages.add(userMessage)
                            val currentInput = userInput
                            userInput = ""
                            isLoading = true
                            
                            coroutineScope.launch {
                                // Pequeño retraso para la animación
                                delay(300)
                                val response = fetchLLMResponse(currentInput)
                                isLoading = false
                                
                                if (response != null) {
                                    val aiMessage = ChatMessage(
                                        text = response,
                                        isFromUser = false,
                                        isError = response.startsWith("Error:")
                                    )
                                    messages.add(aiMessage)
                                } else {
                                    messages.add(
                                        ChatMessage(
                                            text = "Lo siento, no pude procesar tu solicitud. Por favor, inténtalo de nuevo.",
                                            isFromUser = false,
                                            isError = true
                                        )
                                    )
                                }
                            }
                        },
                        enabled = !isLoading,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    ComposeGPTTheme {
        ChatScreen()
    }
}