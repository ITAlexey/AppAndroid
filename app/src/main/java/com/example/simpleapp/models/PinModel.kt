package com.example.simpleapp.models

import com.example.simpleapp.R
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

    fun createPinIfSuccess(success: () -> Unit, fail: (Int) -> Unit?)  {
        val isPinSimple: Boolean = PinParser.checkOnSimplicity(temporaryPin)
        if (!isPinSimple) {
            confirmationPin = temporaryPin
            success()
        } else {
            fail(R.string.popup_simple)
        }
    }

    fun deletePinIfSuccess(success: () -> Unit, fail: (Int) -> Unit?) {
        if (sharedPreferences.isPinCorrect(temporaryPin)) {
            sharedPreferences.removePin()
            success()
        }
        fail(R.string.popup_different)
    }

    fun loginIfSuccess(success: () -> Unit, fail: (Int) -> Unit?) {
        if (sharedPreferences.isPinCorrect(temporaryPin)) {
            success()
        }
        fail(R.string.popup_fail)
    }

    fun savePinIfSuccess(success: () -> Unit, fail: (Int) -> Unit?) {
        if (confirmationPin == temporaryPin) {
            sharedPreferences.savePin(confirmationPin)
            success()
        }
        fail(R.string.popup_different)
    }

    fun isPinSaved(): Boolean = sharedPreferences.getPin()!!.isNotEmpty()
}