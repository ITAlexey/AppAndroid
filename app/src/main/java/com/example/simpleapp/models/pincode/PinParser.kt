package com.example.simpleapp.models.pincode

object PinParser {
    fun checkOnSimplicity(pin: String): Boolean =
        isPinConsistsOfSameNumbers(pin)
                || isPinConsistsOfNumbersIncreasedByOne(pin)
                || isPinConsistsOfNumbersDecreasedByOne(pin)

    private fun isPinConsistsOfNumbersDecreasedByOne(pin: String): Boolean {
        for (i in pin.lastIndex downTo 1) {
            if (pin[i].toInt() - pin[i - 1].toInt() != 1) {
                return false
            }
        }
        return true
    }

    private fun isPinConsistsOfNumbersIncreasedByOne(pin: String): Boolean {
        for (i in 0 until pin.lastIndex) {
            if (pin[i].toInt() - pin[i + 1].toInt() != 1) {
                return false
            }
        }
        return true
    }

    private fun isPinConsistsOfSameNumbers(pin: String): Boolean =
        pin.toSet().size == 1
}