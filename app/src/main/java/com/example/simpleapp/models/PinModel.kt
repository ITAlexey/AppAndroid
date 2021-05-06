package com.example.simpleapp.models

import androidx.annotation.VisibleForTesting
import com.example.simpleapp.adapter.PinAdapter.Companion.PIN_SIZE
import com.example.simpleapp.models.utils.PinParser


class PinModel(private val sharedPreferences: SharedPrefRepo) {
    private var confirmationPin: String = ""

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var temporaryPin: String = ""

    var isPinEmpty: Boolean = true
        get() = temporaryPin.isEmpty()
        private set

    var isPinNotEmpty: Boolean = true
        get() = temporaryPin.isNotEmpty()
        private set

    var isPinFull: Boolean = true
        get() = temporaryPin.length == PIN_SIZE
        private set

    var isPinSaved: Boolean = false
        get() = sharedPreferences.getPin().isNotEmpty()
        private set

    var pinLength: Int = 0
        get() = temporaryPin.length
        private set

    fun addNumber(number: Int) {
        temporaryPin += number.toString()
    }

    fun removeNumber() {
        temporaryPin = temporaryPin.substring(0, temporaryPin.lastIndex)
    }

    fun calculateSumPinNumbers(): String =
        sharedPreferences
            .getPin()
            .map { Integer.valueOf(it.toString()) }
            .sum()
            .toString()

    fun resetPin() {
        temporaryPin = ""
    }

    fun createPinIfSuccess(): Boolean {
        val isPinSimple: Boolean = PinParser.checkOnSimplicity(temporaryPin)
        if (!isPinSimple) {
            confirmationPin = temporaryPin
            return true
        }
        return false
    }

    fun deletePinIfSuccess(): Boolean {
        val isPinCorrect = sharedPreferences.isPinCorrect(temporaryPin)
        if (isPinCorrect) {
            sharedPreferences.removePin()
            return true
        }
        return false
    }

    fun loginIfSuccess(): Boolean {
        return sharedPreferences.isPinCorrect(temporaryPin)
    }

    fun savePinIfSuccess(): Boolean {
        if (confirmationPin == temporaryPin) {
            sharedPreferences.savePin(confirmationPin)
            return true
        }
        return false
    }
}