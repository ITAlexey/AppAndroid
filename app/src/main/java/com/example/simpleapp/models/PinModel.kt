package com.example.simpleapp.models

import com.example.simpleapp.Constants.PIN_SIZE
import com.example.simpleapp.models.utils.PinParser


class PinModel(private val sharedPreferences: SharedPrefRepo) {
    private var confirmationPin: String = ""
    var temporaryPin: String = ""
        private set

    fun addNumber(number: Int) {
        temporaryPin += number.toString()
    }

    fun removeNumber() {
        temporaryPin = temporaryPin.substring(0, temporaryPin.length - 1)
    }


    fun calculateSumPinNumbers(): String =
        sharedPreferences.getPin()!!.map { Integer.valueOf(it.toString()) }.sum().toString()

    fun resetTemporaryPin() {
        temporaryPin = ""
    }

    fun createPinIfSuccess(processResult: (Boolean) -> Unit)  {
        val isPinSimple: Boolean = PinParser.checkOnSimplicity(temporaryPin)
        if (!isPinSimple) {
            confirmationPin = temporaryPin
        }
        processResult(!isPinSimple)
    }

    fun deletePinIfSuccess(processResult: (Boolean) -> Unit) {
        val isPinCorrect = sharedPreferences.isPinCorrect(temporaryPin)
        if (isPinCorrect) {
            sharedPreferences.removePin()
        }
        processResult(isPinCorrect)
    }

    fun loginIfSuccess(processResult: (Boolean) -> Unit) {
        val isPinCorrect = sharedPreferences.isPinCorrect(temporaryPin)
        processResult(isPinCorrect)
    }

    fun savePinIfSuccess(processResult: (Boolean) -> Unit) {
        val isPinsEqual = confirmationPin == temporaryPin
        if (isPinsEqual) {
            sharedPreferences.savePin(confirmationPin)
        }
        processResult(isPinsEqual)
    }

    fun isTemporaryPinEmpty(): Boolean = temporaryPin.isEmpty()

    fun isTemporaryPinFull(): Boolean = temporaryPin.length == PIN_SIZE

    fun isPinSaved(): Boolean = sharedPreferences.getPin()!!.isNotEmpty()
}