package com.example.simpleapp

import android.app.Application
import android.content.Context
import com.example.simpleapp.models.pincode.PinModel
import com.example.simpleapp.models.SharedPrefRepo
import com.example.simpleapp.models.themes.ThemeModel
import com.example.simpleapp.models.pincode.EncryptionUtils

class BaseApp : Application() {
    lateinit var pinModel: PinModel
        private set
    lateinit var themeModel: ThemeModel
        private set

    override fun onCreate() {
        super.onCreate()

        EncryptionUtils.createKeysIfNotExists()
        val sharedPreferences = getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
        SharedPrefRepo.initialized(sharedPreferences)
        val sharedPrefRepo = SharedPrefRepo.getInstance()

        pinModel = PinModel(sharedPrefRepo)
        themeModel = ThemeModel(sharedPrefRepo)
    }

    companion object {
        const val SHARED_PREF_FILE = "com.example.simpleapp.SHARED_PREF_FILE"
    }
}