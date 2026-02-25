package com.example.splitflap

import org.junit.Test
import org.junit.Assert.*

class CharacterUtilsTest {
    @Test
    fun testGetNextChar() {
        assertEquals('A', CharacterUtils.getNextChar(' '))
        assertEquals('B', CharacterUtils.getNextChar('A'))
        // Last char
        val lastChar = CharacterUtils.charList.last()
        assertEquals(' ', CharacterUtils.getNextChar(lastChar))
    }

    @Test
    fun testGetDistance() {
        assertEquals(1, CharacterUtils.getDistance(' ', 'A'))
        assertEquals(0, CharacterUtils.getDistance('A', 'A'))
        // Wrap
        val last = CharacterUtils.charList.last()
        assertEquals(1, CharacterUtils.getDistance(last, ' '))
    }
}
