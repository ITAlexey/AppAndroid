package com.example.simpleapp

import android.app.Application
import android.content.Context
import com.example.simpleapp.Constants.SHARED_PREF_FILE
import com.example.simpleapp.simpleapp.PinModel
import com.example.simpleapp.simpleapp.SharedPrefRepo
import com.example.simpleapp.utils.EncryptionUtils

class BaseApp : Application() {
    lateinit var pinModel: PinModel
        private set

    override fun onCreate() {
        super.onCreate()
        EncryptionUtils.createKeysIfNotExists()
        val sharedPreferences = getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
        val sharedPrefRepo = SharedPrefRepo(sharedPreferences)
        pinModel = PinModel(sharedPrefRepo)
    }
}