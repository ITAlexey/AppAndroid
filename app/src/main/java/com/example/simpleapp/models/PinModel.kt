package com.example.simpleapp.models

import android.content.SharedPreferences
import com.example.simpleapp.Constants.PIN_CODE_KEY

class PinModel(
    private val sharedPreferences: SharedPreferences,
    var permanentPin: String? = sharedPreferences.getString(PIN_CODE_KEY, ""),
    var currentPinState: PinState = if (permanentPin!!.isNotEmpty()) PinState.LOGOUT else PinState.CREATE,
    private var confirmationPin: String = "",
    var temporaryPin: String = ""
) {
    fun addNumber(number: Int) {
        temporaryPin += number.toString()
    }

    fun removeNumber() {
        temporaryPin = temporaryPin.substring(0, temporaryPin.length - 1)
    }

    fun processPinField() {
        when (currentPinState) {
            PinState.CREATE -> createPin()
            PinState.CONFIRM -> savePinIfSuccess()
            PinState.LOGOUT -> loginIfSuccess()
            PinState.RESET -> deletePinIfSuccess()
        }
        resetTemporaryPin()
    }

    fun resetTemporaryPin() {
        temporaryPin = ""
    }

    fun updatePinState(state: PinState) {
        currentPinState = state
    }

    private fun createPin() {
        confirmationPin = temporaryPin
        updatePinState(PinState.CONFIRM)
    }

    private fun deletePinIfSuccess() {
        if (temporaryPin == permanentPin) {
            removeSavedPin()
        }
    }

    private fun loginIfSuccess() {
        if (temporaryPin == permanentPin) {
            updatePinState(PinState.LOGIN)
        }
    }

    private fun removeSavedPin() {
        sharedPreferences.edit().remove(PIN_CODE_KEY).apply()
        updatePinState(PinState.CREATE)
    }

    private fun savePinIfSuccess() {
        if (confirmationPin == temporaryPin) {
            updatePinState(PinState.LOGOUT)
            saveToSharedPref()
            permanentPin = confirmationPin
        }
    }

    private fun saveToSharedPref() =
        sharedPreferences
            .edit()
            .putString(PIN_CODE_KEY, confirmationPin)
            .apply()
}