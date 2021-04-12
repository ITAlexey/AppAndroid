package com.example.simpleapp.models

import com.example.simpleapp.models.utils.PinParser
import com.example.simpleapp.models.utils.PinState


class PinModel(private val sharedPreferences: SharedPrefRepo) {
    var currentPinState: PinState = getPinState()
    private var confirmationPin: String = ""
    var temporaryPin: String = ""
        private set

    private fun getPinState(): PinState {
        return if (sharedPreferences.getPin()!!.isNotEmpty()) {
            PinState.LOGOUT
        } else {
            PinState.CREATE
        }
    }

    fun addNumber(number: Int) {
        temporaryPin += number.toString()
    }

    fun removeNumber() {
        temporaryPin = temporaryPin.substring(0, temporaryPin.length - 1)
    }

    fun processPinField() {
        when (currentPinState) {
            PinState.CREATE -> createPinIfNotSimple()
            PinState.CONFIRM -> savePinIfSuccess()
            PinState.LOGOUT -> loginIfSuccess()
            PinState.RESET -> deletePinIfSuccess()
        }
        resetTemporaryPin()
    }

    fun calculateSumPinNumbers(): String =
        sharedPreferences.getPin()!!.map { Integer.valueOf(it.toString()) }.sum().toString()

    fun resetTemporaryPin() {
        temporaryPin = ""
    }

    fun updatePinState(state: PinState) {
        currentPinState = state
    }

    private fun createPinIfNotSimple() {
        val isPinSimple: Boolean = PinParser.checkOnSimplicity(temporaryPin)
        if (!isPinSimple) {
            confirmationPin = temporaryPin
            updatePinState(PinState.CONFIRM)
        }
    }

    private fun deletePinIfSuccess() {
        if (sharedPreferences.isPinCorrect(temporaryPin)) {
            sharedPreferences.removePin()
            updatePinState(PinState.CREATE)
        }
    }

    private fun loginIfSuccess() {
        if (sharedPreferences.isPinCorrect(temporaryPin)) {
            updatePinState(PinState.LOGIN)
        }
    }

    private fun savePinIfSuccess() {
        if (confirmationPin == temporaryPin) {
            updatePinState(PinState.LOGOUT)
            sharedPreferences.savePin(confirmationPin)
        }
    }
}