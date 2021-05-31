package com.example.simpleapp.models

import androidx.annotation.VisibleForTesting
import com.example.simpleapp.adapter.PinAdapter.Companion.PIN_SIZE
import com.example.simpleapp.models.utils.PinParser
import com.example.simpleapp.utils.EncryptionUtils


class PinModel(private val sharedPrefRepo: SharedPrefRepo) {
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
        get() = getPin().isNotEmpty()
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

    fun calculateSumPinNumbers(): Int =
        getPin()
            .map { Integer.valueOf(it.toString()) }
            .sum()

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
        if (isPinCorrect(temporaryPin)) {
            removePin()
            return true
        }
        return false
    }

    fun loginIfSuccess(): Boolean {
        return isPinCorrect(temporaryPin)
    }

    fun savePinIfSuccess(): Boolean {
        if (confirmationPin == temporaryPin) {
            savePin()
            return true
        }
        return false
    }

    private fun getPin(): String {
        val pin = sharedPrefRepo.getStringByKey(KEY_PIN_CODE, "")
        return EncryptionUtils.decryptData(pin)
    }

    private fun savePin() {
        val encryptedPin = EncryptionUtils.encryptData(confirmationPin)
        sharedPrefRepo.putStringByKey(KEY_PIN_CODE, encryptedPin)
    }

    private fun removePin() =
        sharedPrefRepo.removeDataByKey(KEY_PIN_CODE)


    private fun isPinCorrect(pinToBeCompared: String): Boolean =
        pinToBeCompared == getPin()

    companion object {
        private const val KEY_PIN_CODE = "KEY_PIN_CODE"
    }
}