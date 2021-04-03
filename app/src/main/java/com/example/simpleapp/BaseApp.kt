package com.example.simpleapp

import android.app.Application
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.simpleapp.Constants.SHARED_PREF_FILE
import com.example.simpleapp.models.PinModel

class BaseApp : Application() {
    lateinit var pinModel: PinModel
        private set


    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = getEncryptedSharedPref()
        pinModel = PinModel(sharedPreferences)

    }

    private fun getEncryptedSharedPref(): SharedPreferences {
        val masterKey = MasterKey.Builder(this, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        return EncryptedSharedPreferences.create(
            this,
            SHARED_PREF_FILE,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}