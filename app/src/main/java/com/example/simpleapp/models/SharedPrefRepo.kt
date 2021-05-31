package com.example.simpleapp.models

import android.content.SharedPreferences
import java.lang.IllegalStateException

class SharedPrefRepo private constructor(private val sharedPreferences: SharedPreferences) {
    fun getIntByKey(key: String, defaultValue: Int): Int =
        sharedPreferences.getInt(key, defaultValue)

    fun getStringByKey(key: String, defaultValue: String): String =
        sharedPreferences.getString(key, defaultValue) ?: ""

    fun removeDataByKey(key: String) =
        sharedPreferences
            .edit()
            .remove(key)
            .apply()

    fun putStringByKey(key: String, data: String) =
        sharedPreferences
            .edit()
            .putString(key, data)
            .apply()

    fun putIntByKey(key: String, data: Int) =
        sharedPreferences
            .edit()
            .putInt(key, data)
            .apply()

    companion object {
        private var INITIALIZED: SharedPrefRepo? = null

        fun initialized(sharedPreferences: SharedPreferences) {
            INITIALIZED = SharedPrefRepo(sharedPreferences)
        }

        fun getInstance(): SharedPrefRepo =
            INITIALIZED
                ?: throw IllegalStateException("SharedPrefRepo class has not been initialized!")
    }
}