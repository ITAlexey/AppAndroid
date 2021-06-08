package com.example.simpleapp.models.pincode

import com.example.simpleapp.adapter.PinAdapter.Companion.PIN_SIZE
import com.example.simpleapp.models.SharedPrefRepo


class PinModel(private val sharedPrefRepo: SharedPrefRepo) {
    private var confirmationPin: String = ""
    private var temporaryPin: String = ""
    var processedPinStatus = false to PinState.CREATE
        private set

    var pinState: PinState
        private set
    var isPinSimple: Boolean = true
        get() = PinParser.checkOnSimplicity(temporaryPin)
        private set

    init {
        pinState = if (getPin().isNotEmpty()) {
            PinState.LOGOUT
        } else {
            PinState.CREATE
        }
    }

    var isPinEmpty: Boolean = true
        get() = temporaryPin.isEmpty()
        private set

    var isPinNotEmpty: Boolean = true
        get() = temporaryPin.isNotEmpty()
        private set

    var isPinFull: Boolean = true
        get() = temporaryPin.length == PIN_SIZE
        private set

    var pinLength: Int = 0
        get() = temporaryPin.length
        private set

    fun updatePinState(newState: PinState) {
        pinState = newState
    }

    fun addNumber(number: Int) {
        temporaryPin += number.toString()
    }

    fun removeNumber() {
        temporaryPin = temporaryPin.substring(0, temporaryPin.lastIndex)
    }

    fun calculateSumPinNumbers(): Int =
        getPin()
            .map { Integer.valueOf(it.toString()) }
            .sum()

    fun resetPin() {
        temporaryPin = ""
    }

    fun updateProcessedPinStatus(isSuccess: Boolean, newPinState: PinState) {
        processedPinStatus = isSuccess to newPinState
    }

    fun checkPinsEquality(): Boolean =
        confirmationPin == temporaryPin

    fun saveAsConfirmationPin() {
        confirmationPin = temporaryPin
    }

    fun isPinCorrect(savedPin: String) : Boolean =
        temporaryPin == savedPin

    fun getPin(): String {
        val pin = sharedPrefRepo.getStringByKey(KEY_PIN_CODE, "")
        return EncryptionUtils.decryptData(pin)
    }

    fun savePin() {
        val encryptedPin = EncryptionUtils.encryptData(confirmationPin)
        sharedPrefRepo.putStringByKey(KEY_PIN_CODE, encryptedPin)
    }

    fun removePin() =
        sharedPrefRepo.removeDataByKey(KEY_PIN_CODE)

    companion object {
        private const val KEY_PIN_CODE = "KEY_PIN_CODE"
    }
}