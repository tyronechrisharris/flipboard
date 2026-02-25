package com.example.splitflap

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun FlapChar(
    targetChar: Char,
    audioEngine: AudioEngine,
    modifier: Modifier = Modifier
) {
    // Current displayed char (static background)
    var displayChar by remember { mutableStateOf(' ') }
    // Next char (flap back)
    var nextChar by remember { mutableStateOf(' ') }

    // Rotation value: 0f -> 180f
    val rotation = remember { Animatable(0f) }

    // Logic:
    LaunchedEffect(targetChar) {
        if (displayChar != targetChar) {
            while (displayChar != targetChar) {
                nextChar = CharacterUtils.getNextChar(displayChar)

                // Play sound at start of flip
                audioEngine.playClick()

                rotation.animateTo(
                    targetValue = 180f,
                    animationSpec = tween(durationMillis = 150, easing = LinearEasing)
                )

                displayChar = nextChar
                rotation.snapTo(0f)
            }
        }
    }

    Box(modifier = modifier.clipToBounds().background(Color(0xFF101010))) {
        // Visual layers
        val topStaticChar = nextChar
        val bottomStaticChar = displayChar
        val flapFrontChar = displayChar
        val flapBackChar = nextChar

        // 1. Static Top (Background): Shows NEXT char (revealed behind)
        CharHalf(topStaticChar, HalfType.TOP, Modifier.matchParentSize())

        // 2. Static Bottom (Background): Shows Current Char Bottom
        CharHalf(bottomStaticChar, HalfType.BOTTOM, Modifier.matchParentSize())

        // 3. Flap
        val r = rotation.value
        if (r < 90f) {
            // Front face (Top half of current)
            CharHalf(
                flapFrontChar,
                HalfType.TOP,
                Modifier
                    .matchParentSize()
                    .graphicsLayer {
                        rotationX = r
                        cameraDistance = 12f * density
                        transformOrigin = TransformOrigin(0.5f, 0.5f) // Pivot at center
                    }
            )
        } else {
            // Back face (Bottom half of next)
            CharHalf(
                flapBackChar,
                HalfType.BOTTOM,
                Modifier
                    .matchParentSize()
                    .graphicsLayer {
                        rotationX = r - 180f
                        cameraDistance = 12f * density
                        transformOrigin = TransformOrigin(0.5f, 0.5f) // Pivot at center
                    }
            )
        }

        // Hinge line / shadow
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color.Black.copy(alpha = 0.5f))
                .align(Alignment.Center)
        )
    }
}

enum class HalfType { TOP, BOTTOM }

@Composable
fun CharHalf(char: Char, type: HalfType, modifier: Modifier) {
    BoxWithConstraints(modifier) {
        val height = maxHeight
        val width = maxWidth

        // Background for the card
        val cardColor = Color(0xFF202020)

        if (type == HalfType.TOP) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height / 2) // Top half height
                    .align(Alignment.TopCenter)
                    .clipToBounds()
                    .background(cardColor)
            ) {
                 // Render full text box, align its TOP to this box's top
                 Box(
                     modifier = Modifier
                        .size(width, height)
                        .align(Alignment.TopCenter),
                     contentAlignment = Alignment.Center
                 ) {
                     Text(
                        text = char.toString(),
                        color = Color.White,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                 }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height / 2) // Bottom half height
                    .align(Alignment.BottomCenter)
                    .clipToBounds()
                    .background(cardColor)
            ) {
                 // Render full text box, align its BOTTOM to this box's bottom
                 Box(
                     modifier = Modifier
                        .size(width, height)
                        .align(Alignment.BottomCenter),
                     contentAlignment = Alignment.Center
                 ) {
                     Text(
                        text = char.toString(),
                        color = Color.White,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                 }
            }
        }
    }
}
