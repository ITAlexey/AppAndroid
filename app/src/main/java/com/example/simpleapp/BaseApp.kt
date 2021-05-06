package com.example.simpleapp

import android.app.Application
import android.content.Context
import com.example.simpleapp.models.PinModel
import com.example.simpleapp.models.SharedPrefRepo
import com.example.simpleapp.utils.EncryptionUtils

class BaseApp : Application() {
    lateinit var pinModel: PinModel
        private set

    override fun onCreate() {
        super.onCreate()
        EncryptionUtils.createKeysIfNotExists()
        val sharedPreferences = getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
        SharedPrefRepo.initialized(sharedPreferences)
        val sharedPrefRepo = SharedPrefRepo.getInstance()
        pinModel = PinModel(sharedPrefRepo)
    }

    companion object {
        const val SHARED_PREF_FILE = "com.example.simpleapp.SHARED_PREF_FILE"
    }
}