package com.example.splitflap

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import kotlin.random.Random

class AudioEngine(context: Context) {
    private val soundPool: SoundPool
    private val clickSoundId: Int

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(50) // Allow many overlapping sounds
            .setAudioAttributes(audioAttributes)
            .build()

        // Load the click sound. Ensure R.raw.click exists.
        clickSoundId = soundPool.load(context, R.raw.click, 1)
    }

    fun playClick() {
        // slight pitch variation for realism
        val pitch = 0.9f + Random.nextFloat() * 0.2f
        // Volume 0.5, loop 0
        soundPool.play(clickSoundId, 0.5f, 0.5f, 1, 0, pitch)
    }

    fun release() {
        soundPool.release()
    }
}
