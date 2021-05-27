package com.example.simpleapp.models

import android.content.SharedPreferences
import com.example.simpleapp.utils.EncryptionUtils
import java.lang.IllegalStateException

class SharedPrefRepo private constructor(private val sharedPreferences: SharedPreferences) {

    fun getPin(): String {
        val pin = sharedPreferences.getString(KEY_PIN_CODE, "") ?: ""
        return if (pin.isNotEmpty()) EncryptionUtils.decryptData(pin) else ""
    }

    fun getApplicationTheme(): Int =
        sharedPreferences.getInt(KEY_APP_THEME, 0)

    fun savePin(pinToBeSaved: String) =
        sharedPreferences
            .edit()
            .putString(KEY_PIN_CODE, EncryptionUtils.encryptData(pinToBeSaved))
            .apply()

    fun removePin() =
        sharedPreferences
            .edit()
            .remove(KEY_PIN_CODE)
            .apply()

    fun isPinCorrect(pinToBeCompared: String): Boolean =
        pinToBeCompared == getPin()

    companion object {
        private const val KEY_PIN_CODE = "PIN_CODE"
        private const val KEY_APP_THEME = "APP_THEME"
        private var INITIALIZED: SharedPrefRepo? = null

        fun initialized(sharedPreferences: SharedPreferences) {
            INITIALIZED = SharedPrefRepo(sharedPreferences)
        }

        fun getInstance(): SharedPrefRepo =
            INITIALIZED ?: throw IllegalStateException("SharedPrefRepo class has not been initialized!")
    }
}