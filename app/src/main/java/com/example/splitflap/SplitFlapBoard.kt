package com.example.splitflap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SplitFlapBoard(
    message: String,
    audioEngine: AudioEngine,
    modifier: Modifier = Modifier
) {
    val columns = 22
    val rows = 6
    val totalChars = columns * rows

    // Ensure message fits the grid exactly
    // Upper case for better matching with our char set
    val sanitizedMessage = message.uppercase()
    val paddedMessage = sanitizedMessage.padEnd(totalChars, ' ').take(totalChars)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF050505)) // Very dark background
            .padding(32.dp), // Margins for TV
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (row in 0 until rows) {
            Row(
                modifier = Modifier.wrapContentSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                for (col in 0 until columns) {
                    val index = row * columns + col
                    val char = paddedMessage.getOrElse(index) { ' ' }

                    // Filter char to be in our supported set, otherwise space or '?'
                    val supportedChar = if (CharacterUtils.charMap.containsKey(char)) char else ' '

                    FlapChar(
                        targetChar = supportedChar,
                        audioEngine = audioEngine,
                        modifier = Modifier
                            .padding(2.dp) // Gap between modules
                            .width(40.dp)  // Safe for 1080p screens (22 cols * 80px = 1760px < 1920px)
                            .height(60.dp) // 2:3 aspect ratio roughly
                    )
                }
            }
        }
    }
}
