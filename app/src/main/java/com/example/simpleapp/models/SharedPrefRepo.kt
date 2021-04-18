package com.example.simpleapp.models

import android.content.SharedPreferences
import com.example.simpleapp.utils.EncryptionUtils

class SharedPrefRepo(private val sharedPreferences: SharedPreferences) {

    fun getPin(): String {
        val pin = sharedPreferences.getString(PIN_CODE_KEY, "")
        return if (pin!!.isNotEmpty()) EncryptionUtils.decryptData(pin) else ""
    }

    fun savePin(pinToBeSaved: String) =
        sharedPreferences.edit().putString(PIN_CODE_KEY, EncryptionUtils.encryptData(pinToBeSaved))
            .apply()

    fun removePin() = sharedPreferences.edit().remove(PIN_CODE_KEY).apply()

    fun isPinCorrect(pinToBeCompared: String): Boolean = pinToBeCompared == getPin()

    companion object {
        private const val PIN_CODE_KEY = "PIN_CODE"
    }
}