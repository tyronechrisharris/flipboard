package com.example.splitflap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    private lateinit var audioEngine: AudioEngine
    private lateinit var server: LocalServer
    private val messageState = mutableStateOf("WELCOME TO VISTA BOARD")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        audioEngine = AudioEngine(this)

        server = LocalServer { newText ->
            // Callback from server (background thread)
            runOnUiThread {
                messageState.value = newText.uppercase()
            }
        }

        // Start server
        try {
            server.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        setContent {
            val message by messageState
            // Get IP once
            val ipAddress = remember { server.getIpAddress() }

            Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
                SplitFlapBoard(
                    message = message,
                    audioEngine = audioEngine,
                    modifier = Modifier.align(Alignment.Center)
                )

                // IP Overlay
                Text(
                    text = "Server IP: $ipAddress:8080",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(32.dp)
                        .background(Color.Black.copy(alpha = 0.7f))
                        .padding(8.dp)
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        audioEngine.release()
        server.stop()
    }
}
