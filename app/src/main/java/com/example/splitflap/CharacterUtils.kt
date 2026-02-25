package com.example.splitflap

object CharacterUtils {
    // Define the sequence of characters on the flap
    // Ensure space is first or present
    private const val CHARS = " ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!?,.:"

    val charList = CHARS.toList()
    val charMap = charList.withIndex().associate { it.value to it.index }

    fun getNextChar(c: Char): Char {
        val index = charMap[c.uppercaseChar()] ?: 0 // Default to first char (space) if not found
        return charList[(index + 1) % charList.size]
    }

    // Calculate how many steps to get from 'from' to 'to'
    fun getDistance(from: Char, to: Char): Int {
        val fromIndex = charMap[from.uppercaseChar()] ?: 0
        val toIndex = charMap[to.uppercaseChar()] ?: 0
        if (fromIndex <= toIndex) {
            return toIndex - fromIndex
        }
        return (charList.size - fromIndex) + toIndex
    }
}
