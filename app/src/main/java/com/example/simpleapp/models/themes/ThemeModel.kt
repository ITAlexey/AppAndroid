package com.example.simpleapp.models.themes

import com.example.simpleapp.models.SharedPrefRepo

class ThemeModel(private val sharedPrefRepo: SharedPrefRepo) {

    fun getSavedTheme(key: String = KEY_APP_THEME, defaultValue: Int = ThemeApp.SYSTEM.themeMode): ThemeApp {
        val themeMode = sharedPrefRepo.getIntByKey(key, defaultValue)
        return ThemeApp.values().find {
            it.themeMode == themeMode
        } ?: ThemeApp.SYSTEM
    }

    fun saveTheme(themeType: ThemeApp, key: String = KEY_APP_THEME) =
        sharedPrefRepo.putIntByKey(key, themeType.themeMode)

    companion object {
        private const val KEY_APP_THEME = "KEY_APP_THEME"
    }
}