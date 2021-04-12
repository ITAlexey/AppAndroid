package com.example.simpleapp.models

import android.content.SharedPreferences

class SharedPrefRepo(private val sharedPreferences: SharedPreferences) {

    fun getPin(): String? = sharedPreferences.getString(PIN_CODE_KEY, "")

    fun savePin(pinToBeSaved: String) =
        sharedPreferences.edit().putString(PIN_CODE_KEY, pinToBeSaved).apply()

    fun removePin() = sharedPreferences.edit().remove(PIN_CODE_KEY).apply()

    fun isPinCorrect(pinToBeCompared: String): Boolean = pinToBeCompared == getPin()

    companion object {
        private const val PIN_CODE_KEY = "PINCODE"
    }
}