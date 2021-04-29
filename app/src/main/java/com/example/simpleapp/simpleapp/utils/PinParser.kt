package com.example.simpleapp.simpleapp.utils

object PinParser {
    private lateinit var pin: String
    fun checkOnSimplicity(pin: String): Boolean {
        this.pin = pin
        return isPinConsistsOfSameNumbers() || isPinConsistsOfNumbersIncreasedByOne() || isPinConsistsOfNumbersDecreasedByOne()
    }

    private fun isPinConsistsOfNumbersDecreasedByOne(): Boolean {
        for (i in pin.lastIndex downTo 1) {
            if (pin[i].toInt() - pin[i - 1].toInt() != 1) {
                return false
            }
        }
        return true
    }

    private fun isPinConsistsOfNumbersIncreasedByOne(): Boolean {
        for (i in 0 until pin.lastIndex) {
            if (pin[i].toInt() - pin[i + 1].toInt() != 1) {
                return false
            }
        }
        return true
    }

    private fun isPinConsistsOfSameNumbers(): Boolean =
        pin.toSet().size == 1
}